package com.opencryptotrade.common.cryptocurrency.daemon.rpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.common.cryptocurrency.daemon.model.http.HttpClientException;
import com.opencryptotrade.common.cryptocurrency.daemon.model.http.HttpServerException;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public abstract class DaemonRPC {

    private static final Consumer<HttpHeaders> HEADERS = headers -> headers.set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
    private final Consumer<HttpClientResponse> responseCallback;
    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private final Mono<String> token;
    private final CryptoCurrencyDaemonSettings settings;

    DaemonRPC(CryptoCurrencyDaemonSettings settings, ObjectMapper objectMapper) {
        responseCallback = response -> {
            if (response.status().code() >= 500) {
                throw new HttpServerException(response.status().code(), response.status().reasonPhrase());
            } else if (response.status().code() >= 400) {
                throw new HttpClientException(response.status().code(), response.status().reasonPhrase());
            }
        };
        this.objectMapper = objectMapper;
        this.token = createBasicAuthenticationToken(settings.userName(), settings.password());
        this.settings = settings;
        this.client = HttpClient.create();
//        this.client = HttpClient.create().wiretap(true);
    }

    protected Mono<RpcResponse> execute(String methodName, Object[] arguments, Class tClass) {
        return Mono.from(client
                .headersWhen(authorizedHeader())
                .headers(HEADERS)
                .post()
                .uri(buildUri(settings))
                .send(ByteBufFlux.fromString(encodeBody(methodName, arguments)))
                .response(decode(tClass)))
                .retry(3L);
    }

    private static URI buildUri(CryptoCurrencyDaemonSettings daemonSettings) {
        URI uri = null;
        try {
            uri = new URI(
                    "http",  // scheme
                    null,   // user info
                    daemonSettings.host(), // host
                    daemonSettings.port(), // port
                    "",        // path
                    null,     // query
                    null); // fragment
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
        }

        return uri;
    }

    private BiFunction<? super HttpClientResponse, ? super ByteBufFlux, Mono<RpcResponse>> decode(Class tClass) {
        return (response, byteBufFlux) -> {
            this.responseCallback.accept(response);
//            if (response.status().equals(HttpResponseStatus.NOT_FOUND)) {
//                return Mono.empty();
//            } else {
//                return byteBufFlux.aggregate().asInputStream().map(inputStream -> deserialize(inputStream, tClass));
//            }
            return byteBufFlux.aggregate().asInputStream().map(inputStream -> deserialize(inputStream, tClass));
        };
    }

    private Mono<String> encodeBody(String method, Object[] params) {
        return Mono.fromCallable(() -> {
            String result = "";
            try {
                RpcCall rpcCall = new RpcCall("1.0", method, params, UUID.randomUUID().toString());
                result = objectMapper.writeValueAsString(rpcCall);
            } catch (JsonProcessingException e) {
                LOGGER.error(e.getMessage());
            }

            return result;
        });
    }

    private RpcResponse deserialize(InputStream inputStream, Class tClass) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(RpcResponse.class, tClass);
            RpcResponse data = objectMapper.readValue(inputStream, type);
            inputStream.close();
            return data;
        } catch (IOException e) {
            throw Exceptions.propagate(e);
        }
    }

    private Function<? super HttpHeaders, Mono<? extends HttpHeaders>> authorizedHeader() {
        return headers -> token.map(t -> headers.set(HttpHeaderNames.AUTHORIZATION, t));
    }

    private static Mono<String> createBasicAuthenticationToken(String username, String password) {
        return Mono.fromSupplier(() -> basicAuthentication(username, password)).cache();
    }

    private static String basicAuthentication(String username, String password) {
        String credentials = username + ":" + password;
        byte[] credentialsAsBytes = credentials.getBytes(StandardCharsets.ISO_8859_1);
        byte[] encodedBytes = Base64.getEncoder().encode(credentialsAsBytes);
        String encodedCredentials = new String(encodedBytes, StandardCharsets.ISO_8859_1);
        return "Basic " + encodedCredentials;
    }
}

package com.opencryptotrade.cryptocurrencyservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencryptotrade.common.config.JacksonConfiguration;
import com.opencryptotrade.common.cryptocurrency.daemon.*;
import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.common.cryptocurrency.daemon.model.http.HttpServerException;
import com.opencryptotrade.common.cryptocurrency.daemon.rpc.RpcDaemonZEC;
import com.opencryptotrade.common.model.blockchain.ZCash.Block;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import reactor.test.StepVerifier;

import java.util.Objects;

@SpringJUnitConfig
@ContextConfiguration(classes= JacksonConfiguration.class, loader= AnnotationConfigContextLoader.class)
class RemoteDaemonRpcTest {

    private ZecDaemon remoteDaemon;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        CryptoCurrencyDaemonSettings currencyDaemonSettings = new CryptoCurrencyDaemonSettings("127.0.0.1", 8232, "user", "root");
        this.remoteDaemon = new ZecDaemon(new RpcDaemonZEC(currencyDaemonSettings, objectMapper));
    }

    @Test
    void ZCashGetBlock() {
        StepVerifier.create(remoteDaemon.getBlock(590))
                .assertNext(r -> Assertions.assertTrue(
                        r.result() instanceof Block && Objects.equals(((Block) r.result()).getHash(), "00000004e4735cd2e3982a0b46b1f82c32ae7bddc221569cad72809b923d4a6f")
                ))
                .verifyComplete();
    }

    @Test
    void ZCashGetBlockError() {
        StepVerifier.create(remoteDaemon.getBlock(999999999))
                .expectErrorMatches(e -> e instanceof HttpServerException)
                .verify();
    }

    @Test
    void ZCashGetBlockHeight() {
        StepVerifier.create(remoteDaemon.getBlockHeight())
                .assertNext(r -> Assertions.assertTrue(r > 0))
                .verifyComplete();
    }

//    @Test
//    void ZCashMethodNotFoundError() {
//        StepVerifier.create(remoteDaemon.execute("", new Object[]{}, Object.class))
//                .expectErrorMatches(e -> e instanceof HttpClientException)
//                .verify();
//    }

}

package com.opencryptotrade.common.cryptocurrency.daemon.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencryptotrade.common.cryptocurrency.daemon.RemoteDaemon;
import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonSettings;
import reactor.core.publisher.Mono;

public class RpcDaemonZEC extends DaemonRPC implements RemoteDaemon {

    public RpcDaemonZEC(CryptoCurrencyDaemonSettings settings, ObjectMapper objectMapper) {
        super(settings, objectMapper);
    }

    @Override
    public Mono<RpcResponse> execute(String methodName, Object[] arguments, Class tClass) {
        return super.execute(methodName, arguments, tClass);
    }
}

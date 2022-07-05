package com.opencryptotrade.common.cryptocurrency.daemon;

import com.opencryptotrade.common.cryptocurrency.daemon.rpc.RpcResponse;
import reactor.core.publisher.Mono;

public interface RemoteDaemon {

   Mono<RpcResponse> execute(String methodName, Object[] arguments, Class tClass);

}

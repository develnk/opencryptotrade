package com.opencryptotrade.common.cryptocurrency.daemon;

import com.opencryptotrade.common.cryptocurrency.daemon.rpc.RpcResponse;
import reactor.core.publisher.Mono;

public interface BtcDaemonCommands extends DaemonCommands {

    Object getRawTransaction(String txId);
    Object getRawMemPool();
    Object getMemPoolInfo();
    Object getTxOut(String txId);
    Object getTxOutSetInfo();
    Object verifychain();

    @Override
    Mono<RpcResponse> getBlock(Integer height);

}

package com.opencryptotrade.common.cryptocurrency.daemon;

import reactor.core.publisher.Mono;

public interface DaemonCommands {

    Mono<String> generateNewAddress();
    String getBalance(String address);
    Object getStatus();
    Object getInfo();

    // blockchain category
    Object getBestBlockHash();
    Object getBlockCount();
    Mono<?> getBlock(Integer height);
    Object getBlockHash();
    Mono<Integer> getBlockHeight();
    Object getBlockHeader();
    Object getBlockChainInfo();
    Object getChainTips();

}

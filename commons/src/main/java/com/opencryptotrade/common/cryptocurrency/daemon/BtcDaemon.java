package com.opencryptotrade.common.cryptocurrency.daemon;

import com.opencryptotrade.common.cryptocurrency.daemon.rpc.RpcResponse;
import reactor.core.publisher.Mono;

public class BtcDaemon implements BtcDaemonCommands {

    @Override
    public Object getRawTransaction(String txId) {
        return null;
    }

    @Override
    public Object getRawMemPool() {
        return null;
    }

    @Override
    public Object getMemPoolInfo() {
        return null;
    }

    @Override
    public Object getTxOut(String txId) {
        return null;
    }

    @Override
    public Object getTxOutSetInfo() {
        return null;
    }

    @Override
    public Object verifychain() {
        return null;
    }

    @Override
    public Mono<String> generateNewAddress() {
        return null;
    }

    @Override
    public String getBalance(String address) {
        return null;
    }

    @Override
    public Object getStatus() {
        return null;
    }

    @Override
    public Object getInfo() {
        return null;
    }

    @Override
    public Object getBestBlockHash() {
        return null;
    }

    @Override
    public Object getBlockCount() {
        return null;
    }

    @Override
    public Mono<RpcResponse> getBlock(Integer height) {
        return null;
    }

    @Override
    public Object getBlockHash() {
        return null;
    }

    @Override
    public Mono<Integer> getBlockHeight() {
        return null;
    }

    @Override
    public Object getBlockHeader() {
        return null;
    }

    @Override
    public Object getBlockChainInfo() {
        return null;
    }

    @Override
    public Object getChainTips() {
        return null;
    }

}

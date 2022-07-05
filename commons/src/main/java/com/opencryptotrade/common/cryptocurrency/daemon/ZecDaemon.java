package com.opencryptotrade.common.cryptocurrency.daemon;

import com.opencryptotrade.common.cryptocurrency.daemon.rpc.RpcDaemonZEC;
import com.opencryptotrade.common.cryptocurrency.daemon.rpc.RpcResponse;
import com.opencryptotrade.common.model.blockchain.ZCash.Block;
import com.opencryptotrade.common.model.blockchain.ZCash.Info;
import reactor.core.publisher.Mono;

public class ZecDaemon implements BtcDaemonCommands {

    private final RpcDaemonZEC rpcDaemon;

    public ZecDaemon(RpcDaemonZEC rpcDaemon) {
        this.rpcDaemon = rpcDaemon;
    }

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
        return rpcDaemon.execute("getnewaddress", new Object[]{}, String.class)
                .map(rpcResponse -> (String) rpcResponse.result());
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
        return rpcDaemon.execute("getblock", new Object[]{String.valueOf(height)}, Block.class);
    }

    @Override
    public Object getBlockHash() {
        return null;
    }

    @Override
    public Mono<Integer> getBlockHeight() {
        return rpcDaemon.execute("getinfo", new Object[]{}, Info.class)
                .map(rpcResponse -> ((Info) rpcResponse.result()).getBlocks());
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

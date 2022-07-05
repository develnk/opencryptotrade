package com.opencryptotrade.common.cryptocurrency.daemon.rpc;

public record RpcResponse<T>(String jsonrpc, T result, String id, RpcError error) {
}

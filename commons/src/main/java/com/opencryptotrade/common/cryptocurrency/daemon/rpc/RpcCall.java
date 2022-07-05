package com.opencryptotrade.common.cryptocurrency.daemon.rpc;

public record RpcCall(String jsonrpc, String method, Object[] params, String id) {
}

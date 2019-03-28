package com.core.network.api;

/**
 * Api 状态 - 进度状态
 *
 * @author a_liYa
 * @date 16/8/6 19:32.
 */
public enum ApiState {

    REQUEST_START(Type.REQUEST, State.START),
    REQUEST_PROCESS(Type.REQUEST, State.PROCESS),
    REQUEST_END(Type.REQUEST, State.FINISH),

    RESPONSE_START(Type.RESPONSE, State.START),
    RESPONSE_PROCESS(Type.RESPONSE, State.PROCESS),
    RESPONSE_END(Type.RESPONSE, State.FINISH);

    /**
     * 类型 : request或response
     */
    private Type type;
    /**
     * 状态 : 开始、进行中或结束
     */
    private State state;


    ApiState(Type type, State state) {
        this.type = type;
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public Type getType() {
        return type;
    }

    public boolean isRequest() {
        return type == Type.REQUEST;
    }

    public boolean isResponse() {
        return type == Type.RESPONSE;
    }

    /**
     * Api类型 : 请求、响应
     */
    public enum Type {
        REQUEST,    // 请求
        RESPONSE    // 响应
    }

    /**
     * 状态 : 开始、进行中、结束
     */
    public enum State {
        START,      // 开始
        PROCESS,    // 进行中
        FINISH      // 结束
    }
}

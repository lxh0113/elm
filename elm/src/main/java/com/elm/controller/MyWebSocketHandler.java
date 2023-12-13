package com.elm.controller;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 处理收到的文本消息
        System.out.println("收到了"+message+"的消息");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 当WebSocket连接建立时调用
        System.out.println("已经建立连接了");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 当WebSocket连接关闭时调用
        System.out.println("关闭连接了");
    }
}

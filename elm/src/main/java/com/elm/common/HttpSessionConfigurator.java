package com.elm.common;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.HashMap;

//public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {
//
//    @Override
//    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
//        System.out.println(config.getUserProperties());
//        if(config.getUserProperties()!=null) config.getUserProperties().put(HttpSession.class.getName(), request.getHttpSession());
////        else config.getUserProperties() = new HashMap<String, Object>();
//    }
//
//}

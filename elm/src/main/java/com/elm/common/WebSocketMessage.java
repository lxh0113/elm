package com.elm.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMessage<T> implements Serializable {

    //消息类型
    private String messageType;

    //消息接收人
    private String receiverId;

    //消息
    private T t;
}

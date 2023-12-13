package com.elm.dao.WebSocketData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatData implements Serializable {
    private String senderId;
    private String receiverId;
    private String content;

    @Override
    public String toString() {
        return "ChatData{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

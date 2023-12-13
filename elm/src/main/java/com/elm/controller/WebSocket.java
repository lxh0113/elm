package com.elm.controller;

//import com.elm.common.HttpSessionConfigurator;
import com.alibaba.fastjson.JSON;
import com.elm.common.WebSocketMessage;
import com.elm.common.WebSocketType;
import com.elm.dao.CommentsDao;
import com.elm.dao.MessageDao;
import com.elm.dao.OrdersDao;
import com.elm.dao.requestData.OrderItemsData;
import com.elm.domain.Comments;
import com.elm.domain.Message;
import com.elm.domain.Orders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


@Slf4j
@RestController
@Component
@ServerEndpoint(value = "/api/websocket/{userId}")
public class WebSocket {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 用户ID
     */
    private String userId;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<String,Session>();

    private static ApplicationContext applicationContext;


    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocket.applicationContext = applicationContext;
    }


    //这个默认是Get请求
    @GetMapping
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId)
    {
        try {
            this.session = session;
//            HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
            this.userId = userId;
            System.out.println("连接到了"+userId);
            webSockets.add(this);
            sessionPool.put(userId, session);
//            session.getBasicRemote().sendText("连接成功啦");
            log.info("【websocket消息】有新的连接，总数为:"+webSockets.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("【websocket消息】收到客户端消息:"+message);

        ObjectMapper objectMapper=new ObjectMapper();

        try {
            //得到消息并且去处理
            WebSocketMessage webSocketMessage=objectMapper.readValue(message,WebSocketMessage.class);
            String type=webSocketMessage.getMessageType();
            String receiveId=webSocketMessage.getReceiverId();

            boolean res=false;

            switch (type)
            {
                case WebSocketType.Private_Chat: res=privateChat(webSocketMessage);break;
                case WebSocketType.Receive_Orders:res=receiveOrders(webSocketMessage);break;
                case WebSocketType.Cancel_Orders:res=cancelOrders(webSocketMessage);break;
                case WebSocketType.Complete_Orders:res=completeOrders(webSocketMessage);break;
                case WebSocketType.Refund_Orders:res=refundOrders(webSocketMessage);break;
                case WebSocketType.Store_Remark:res=storeRemark(webSocketMessage);break;
                case WebSocketType.People_Remark:res=peopleRemark(webSocketMessage);break;

                default:
                    throw new IllegalStateException("Unexpected value: " + webSocketMessage.getMessageType());
            }

            if(!res)
            {
                log.info("调用方法出错");
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private boolean peopleRemark(WebSocketMessage webSocketMessage) {
        System.out.println("发布评论这里。。。。。。进来了");
        //发布评论
        String receiveId=webSocketMessage.getReceiverId();
        Map<String,Object> map= (Map<String, Object>) webSocketMessage.getT();
        System.out.println(webSocketMessage.getT());

        Comments comments=new Comments(null,(String) map.get("storeId"),(Integer) map.get("ordersId"),
                (String) map.get("content"),(String) map.get("senderId"),(String)map.get("receiverId"),
                new Date(System.currentTimeMillis()),(Integer)map.get("star"),(String) map.get("images"));

        CommentsDao commentsDao=applicationContext.getBean(CommentsDao.class);
        int insert = commentsDao.insert(comments);
        if(insert<=0) return false;

        webSocketMessage.setT(comments);
//        System.out.println("receiverId"+receiveId+"userId"+this.userId);
        if(sessionPool.get(receiveId)!=null)
        {
            try {
                sessionPool.get(receiveId).getBasicRemote().sendText(JSON.toJSONString(webSocketMessage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(sessionPool.get(this.userId)!=null)
        {
            webSocketMessage.setReceiverId(this.userId);
            try {
                sessionPool.get(this.userId).getBasicRemote().sendText(JSON.toJSONString(webSocketMessage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    private boolean storeRemark(WebSocketMessage webSocketMessage) {

        return true;
    }

    private boolean refundOrders(WebSocketMessage webSocketMessage) {
        //
        return true;
    }

    private boolean completeOrders(WebSocketMessage webSocketMessage) {
        //完成订单

        String receiveId=webSocketMessage.getReceiverId();
        Map<String,Object> map= (Map<String, Object>) webSocketMessage.getT();

        boolean b = changOrdersInfo(map);
        if(b)
        {
            if(sessionPool.get(receiveId)!=null)
            {
                try {
                    sessionPool.get(receiveId).getBasicRemote().sendText(JSON.toJSONString(webSocketMessage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            webSocketMessage.setReceiverId(this.userId);

            if(sessionPool.get(this.userId)!=null)
            {
                webSocketMessage.setMessageType(WebSocketType.Operation_Success);
                //发送给自己  也就是商家
                try {
                    sessionPool.get(this.userId).getBasicRemote().sendText(JSON.toJSONString(webSocketMessage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else
        {
            //
            return false;
        }
        return true;
    }

    private boolean cancelOrders(WebSocketMessage webSocketMessage)
    {
        //取消订单
        //数据库修改状态


        return true;
    }

    private boolean receiveOrders(WebSocketMessage webSocketMessage) {
        //接单
        String receiveId=webSocketMessage.getReceiverId();
        Map<String,Object> map= (Map<String, Object>) webSocketMessage.getT();

        boolean b = changOrdersInfo(map);
        if(b)
        {
            //修改成功了
            if(sessionPool.get(receiveId)!=null)
            {
                try {
                    sessionPool.get(receiveId).getBasicRemote().sendText(JSON.toJSONString(webSocketMessage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if(sessionPool.get(this.userId)!=null)
            {
                webSocketMessage.setReceiverId(this.userId);
                webSocketMessage.setMessageType(WebSocketType.Operation_Success);
                try {
                    sessionPool.get(this.userId).getBasicRemote().sendText(JSON.toJSONString(webSocketMessage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else
        {
            return false;
        }

        return true;
    }

    public boolean privateChat(WebSocketMessage webSocketMessage)
    {

        String receiveId=webSocketMessage.getReceiverId();
        Map<String,Object> map= (Map<String, Object>) webSocketMessage.getT();
        //对数据库进行操作
        Message message=new Message(null, (String) map.get("senderId"), (String) map.get("receiverId"), map.get("content").toString(),map.get("flag")==null?0:1,map.get("flag")==null?null: (String) map.get("url"),new Date(System.currentTimeMillis()),null);

        MessageDao messageDao=applicationContext.getBean(MessageDao.class);

        int insert = messageDao.insert(message);

        if(insert<=0) return false;

        webSocketMessage.setT(message);

        //给发送者和接收者发送消息
        if(sessionPool.get(receiveId)!=null)
        {

            try {
                sessionPool.get(receiveId).getBasicRemote().sendText(JSON.toJSONString(webSocketMessage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(sessionPool.get(this.userId)!=null)
        {
            webSocketMessage.setReceiverId(this.userId);
            try {
                sessionPool.get(this.userId).getBasicRemote().sendText(JSON.toJSONString(webSocketMessage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误,原因:"+error.getMessage());
        error.printStackTrace();
    }


    @OnClose
    public void onClose()
    {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:"+message);
        for(WebSocket webSocket : webSockets) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null&&session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:"+message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for(String userId:userIds) {
            Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:"+message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean changOrdersInfo(Map<String,Object> map)
    {
        Orders orders=new Orders((Integer) map.get("id"),
                (String) map.get("buyerId"), (String) map.get("storeId"), (Double) map.get("price"), (String) map.get("addressText"),
                (Double) map.get("packageFee"), (Integer) map.get("status"), (Date) map.get("time"), (String) map.get("remark"),
                (Integer) map.get("isCancel"), (String) map.get("cancelReason"), (String) map.get("orderInfo"), (Integer) map.get("commentId"),
                (List<OrderItemsData>) map.get("itemsList"), (Integer) map.get("number"), (String) map.get("buyerName"), (String) map.get("buyerTel"),
                (String) map.get("pcd"));

        OrdersDao ordersDao = applicationContext.getBean(OrdersDao.class);
        int i = ordersDao.updateById(orders);

        if(i<=0) return false;
        return true;
    }
}

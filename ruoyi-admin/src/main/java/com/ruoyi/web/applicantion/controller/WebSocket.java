package com.ruoyi.web.applicantion.controller;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.WebSocketConfig;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.web.applicantion.mapper.WebSocketMapper;
import com.ruoyi.web.applicantion.service.dto.WebSocketDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint(value = "/chat/{username}")
@Component

public class WebSocket {
    private static final ConcurrentHashMap<String,Session> ws = new ConcurrentHashMap<>();
    private String name;
    private Session session;

    private static WebSocketMapper webSocketMapper;

    // 注入的时候，给类的 service 注入
    @Autowired
    public void setChatService(WebSocketMapper webSocketMapper) {
        WebSocket.webSocketMapper=webSocketMapper;
    }

    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "username") String username){
        log.info("username:"+username);
        this.name = username;
        this.session = session;
        ws.put(name,session);
    }

    @OnMessage
    public void OnMessage(String message){
        log.info(message);
        JSONObject jsonObject = JSON.parseObject(message);
        String text = jsonObject.getString("text");
        String from = jsonObject.getString("from");
        String to = jsonObject.getString("to");

        sendMessageTo(from,to,text);
    }

    @OnClose
    public void OnClose(){
        ws.remove(this.name);
    }

    public void sendMessageTo(String from,String to,String message){
        log.info("当前在线:"+ws.size());
        Session sessionTo = ws.get(to);
        String readStatus = "1";
        System.out.println(sessionTo);
        if(sessionTo!=null){
            sessionTo.getAsyncRemote().sendText(message);
            readStatus = "0";
        }

        WebSocketDto wsDto = new WebSocketDto();
        LocalDateTime currentTime  = LocalDateTime.now();
        wsDto.setSenderId(from);
        wsDto.setReceiverId(to);
        wsDto.setContent(message);
        wsDto.setSendTime(currentTime);
        wsDto.setRead_status(readStatus);
        wsDto.setIsLatest("1");

        webSocketMapper.updateLatestMessage(from,to);
        webSocketMapper.saveMessage(wsDto);
    }
}

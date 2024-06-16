package com.ruoyi.web.applicantion.service.impl;

import com.ruoyi.web.applicantion.mapper.WebSocketMapper;
import com.ruoyi.web.applicantion.service.ChatService;
import com.ruoyi.web.applicantion.service.dto.MessageDto;
import com.ruoyi.web.applicantion.service.dto.WebSocketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private WebSocketMapper webSocketMapper;

    @Override
    public List<MessageDto> getAllMsg(String fromName, String toName) {
        List<MessageDto> list = webSocketMapper.getAllMsg(fromName,toName);
        return list;
    }

    @Override
    public List<WebSocketDto> getLatest() {
        List<WebSocketDto> list = webSocketMapper.getLatest();
        Integer sumRead;
        for (WebSocketDto ws:list){
            sumRead = webSocketMapper.getSumRead(ws.getSenderId(),ws.getReceiverId());
            ws.setRead_status(String.valueOf(sumRead));
        }
        return list;
    }

    @Override
    public List<WebSocketDto> getUserLatest(String fromName, String toName) {
        List<WebSocketDto> list = webSocketMapper.getUserLatest(fromName,toName);
        Integer count = webSocketMapper.getCount(fromName,toName);
        for (WebSocketDto ws:list){
            ws.setRead_status(String.valueOf(count));
        }
        return list;
    }

    @Override
    public void updateStatus(String fromName,String toName) {
        webSocketMapper.updateStatus(fromName,toName);
    }
}

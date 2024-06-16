package com.ruoyi.web.applicantion.service;

import com.ruoyi.web.applicantion.service.dto.MessageDto;
import com.ruoyi.web.applicantion.service.dto.WebSocketDto;

import java.util.List;

public interface ChatService {
    List<MessageDto> getAllMsg(String fromName, String toName);

    List<WebSocketDto> getLatest();

    List<WebSocketDto> getUserLatest(String fromName, String toName);

    void updateStatus(String fromName,String toName);
}

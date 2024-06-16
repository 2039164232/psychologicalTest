package com.ruoyi.web.applicantion.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WebSocketDto {
    private String id;
    private String senderId;
    private String receiverId;
    private String content;
    private LocalDateTime sendTime;
    private String read_status;
    private String isLatest;

}

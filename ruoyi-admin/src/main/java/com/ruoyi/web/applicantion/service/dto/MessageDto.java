package com.ruoyi.web.applicantion.service.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MessageDto {
    private String senderId;
    private String receiverId;
    private String content;
    private LocalDateTime sendTime;
}

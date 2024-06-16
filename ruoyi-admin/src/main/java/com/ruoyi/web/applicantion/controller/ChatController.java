package com.ruoyi.web.applicantion.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.applicantion.service.ChatService;
import com.ruoyi.web.applicantion.service.dto.MessageDto;
import com.ruoyi.web.applicantion.service.dto.WebSocketDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    //根据收发人查询所有聊天记录
    @GetMapping("/getAll")
    public AjaxResult getAllMsg(@RequestParam String fromName, @RequestParam String toName){
        log.info("发送人:"+fromName+",收件人:"+toName);
        List<MessageDto> list = chatService.getAllMsg(fromName,toName);
        return AjaxResult.success(list);
    }

    @GetMapping("/getLast")
    public AjaxResult getLatest(){
        List<WebSocketDto> list = chatService.getLatest();
        return AjaxResult.success(list);
    }

    @GetMapping("/getUserLast")
    public AjaxResult getUserLatest(@RequestParam String fromName, @RequestParam String toName){
        log.info("发送人:"+fromName+",收件人:"+toName);
        List<WebSocketDto> list = chatService.getUserLatest(fromName,toName);
        return AjaxResult.success(list);
    }

    //更改信息状态，把未读消息改为已读
    @PutMapping("/updateReadStatus")
    public AjaxResult updateStatus(@RequestParam String fromName, @RequestParam String toName){
        log.info("修改已读状态:"+fromName+","+toName);
        chatService.updateStatus(fromName,toName);
        return AjaxResult.success();
    }
}

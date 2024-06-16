package com.ruoyi.web.applicantion.mapper;

import com.ruoyi.web.applicantion.service.dto.MessageDto;
import com.ruoyi.web.applicantion.service.dto.WebSocketDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WebSocketMapper {
    void saveMessage(WebSocketDto wsDto);

    void updateLatestMessage(@Param(value = "from") String from, @Param(value = "to") String to);

    List<MessageDto> getAllMsg(@Param(value = "fromName") String fromName, @Param(value = "toName") String toName);

    List<WebSocketDto> getLatest();

    Integer getSumRead(@Param(value = "sendId") String sendId,@Param(value = "receiverId") String receiverId);

    List<WebSocketDto> getUserLatest(@Param(value = "fromName") String fromName, @Param(value = "toName") String toName);

    Integer getCount(@Param(value = "fromName") String fromName, @Param(value = "toName") String toName);

    void updateStatus(@Param(value = "fromName") String fromName, @Param(value = "toName") String toName);
}

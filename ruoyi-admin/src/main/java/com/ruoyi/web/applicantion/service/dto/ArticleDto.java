package com.ruoyi.web.applicantion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private String id;
    private String Title;
    private String Author;
    private String Content;
    private LocalDateTime PublishDate;
    private String url;
}

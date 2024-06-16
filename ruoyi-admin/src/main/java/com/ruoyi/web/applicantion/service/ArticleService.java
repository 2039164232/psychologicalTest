package com.ruoyi.web.applicantion.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.applicantion.service.dto.ArticleDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {
    AjaxResult upload(MultipartFile file,MultipartFile content,String Title,String Author);

    List<ArticleDto> getById(String id);

    List<ArticleDto> getAll();

    int deleteById(String id,String url);

    void update(String id,MultipartFile file, MultipartFile content, String Title, String Author,String src) throws IOException;
}

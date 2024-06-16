package com.ruoyi.web.applicantion.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.web.applicantion.mapper.AnwserQuestionMapper;
import com.ruoyi.web.applicantion.mapper.ArticleMapper;
import com.ruoyi.web.applicantion.service.ArticleService;
import com.ruoyi.web.applicantion.service.dto.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public AjaxResult upload(MultipartFile file,MultipartFile content,String Title,String Author) {
        if(file.isEmpty()){
            return AjaxResult.error("文件为空!");
        }
        String originalFilename = file.getOriginalFilename();
        String newFile = System.currentTimeMillis() +originalFilename.substring(originalFilename.lastIndexOf("."));
        String filePath = "D:\\aaa\\imge\\";
        String fileName = filePath+newFile;
        File f = new File(fileName);
        if (!f.exists()){
            f.mkdirs();
        }
        try {
            file.transferTo(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        return AjaxResult.success(fileName);

        try{
            ArticleDto articleDto = new ArticleDto();
            articleDto.setTitle(Title);
            articleDto.setAuthor(Author);
            articleDto.setContent(new String(content.getBytes(), StandardCharsets.UTF_8));
            articleDto.setUrl(fileName);

            // 获取当前时间
            LocalDateTime currentTime  = LocalDateTime.now();
            articleDto.setPublishDate(currentTime);

            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();
            articleDto.setId(randomUUIDString);

            System.out.println(articleDto);
            articleMapper.upload(articleDto);
            return AjaxResult.success("添加成功");
        } catch (IOException e) {
            return AjaxResult.error("Failed to upload novel");
        }

    }

    @Override
    public List<ArticleDto> getById(String id) {
        return articleMapper.getById(id);
    }

    @Override
    public List<ArticleDto> getAll() {
        return articleMapper.getAll();
    }

    @Override
    public int deleteById(String id,String url) {
        //删除封面
        File file = new File(url);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("文件删除成功");
                articleMapper.deleteById(id);
                return 1;
            } else {
                System.out.println("文件删除失败");
            }
        } else {
            System.out.println("文件不存在，无法删除");
        }
        return 0;
    }

    @Override
    public void update(String id,MultipartFile file, MultipartFile content, String Title, String Author,String src) throws IOException {
        File f = new File(src);
        file.transferTo(f);

        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(id);
        articleDto.setTitle(Title);
        articleDto.setAuthor(Author);
        articleDto.setContent(new String(content.getBytes(), StandardCharsets.UTF_8));
        // 获取当前时间
        LocalDateTime currentTime  = LocalDateTime.now();
        articleDto.setPublishDate(currentTime);

        articleMapper.update(articleDto);
    }
}

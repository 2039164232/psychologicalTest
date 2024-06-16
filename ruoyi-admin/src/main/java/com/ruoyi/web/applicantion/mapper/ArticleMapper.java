package com.ruoyi.web.applicantion.mapper;

import com.ruoyi.web.applicantion.service.dto.ArticleDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
//    @Insert("insert into article(id, Title, Author, Content, PublishDate, url) values(#{id},#{Title},#{Author},#{Content},#{PublishDate},#{url})")
    void upload(ArticleDto articleDto);

    List<ArticleDto> getById(String id);

    List<ArticleDto> getAll();

    void deleteById(String id);

    void update(ArticleDto articleDto);
}

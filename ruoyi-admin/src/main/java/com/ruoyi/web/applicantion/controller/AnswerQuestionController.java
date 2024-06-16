package com.ruoyi.web.applicantion.controller;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.applicantion.mapper.WebSocketMapper;
import com.ruoyi.web.applicantion.service.AnwserQuestionService;
import com.ruoyi.web.applicantion.service.dto.AnalysisDto;
import com.ruoyi.web.applicantion.service.dto.AnswerQuestionDto;
import com.ruoyi.web.applicantion.service.dto.SubtopicDto;
import com.ruoyi.web.applicantion.service.dto.subjectDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/psycho")
public class AnswerQuestionController {
    @Autowired
    private AnwserQuestionService anwserQuestionService;

//    @Anonymous
    //查询题库题目
    @GetMapping("/list")
    public AjaxResult questionList(@RequestParam Integer id) throws IOException{
        log.info("id:"+id);
        List<AnswerQuestionDto> list = anwserQuestionService.questionList(id);
        return AjaxResult.success(list);
    }

    //查询主页数据
    @GetMapping("/subList")
    public AjaxResult getSubList() throws IOException{
        log.info("查询主页数据");
        List<subjectDto> sublist = anwserQuestionService.getSubList();
        return AjaxResult.success(sublist);
    }

    //查询子页数据
    @GetMapping("/test")
    public AjaxResult getTest(@RequestParam Integer id) throws IOException{
        log.info("查询子页数据");
        List<SubtopicDto> sublist = anwserQuestionService.getTest(id);
        return AjaxResult.success(sublist);
    }

    //查询总记录数(题目数量)
    @GetMapping("/count")
    public AjaxResult getCount(@RequestParam Integer id) throws IOException{
        log.info("查询总记录数(题目数量)");
        Integer count = anwserQuestionService.getCount(id);
        return AjaxResult.success(count);
    }

    //根据分数查询对应等级
    @GetMapping("/analysis")
    public AjaxResult getAnalysis(@RequestParam Integer id,@RequestParam Integer score) throws IOException{
        log.info("查询对应等级,id:"+id+",score:"+score);
        List<AnalysisDto> list = anwserQuestionService.getAnalysis(id,score);
        return AjaxResult.success(list);
    }
}


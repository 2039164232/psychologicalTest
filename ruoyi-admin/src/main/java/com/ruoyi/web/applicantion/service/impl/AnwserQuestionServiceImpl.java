package com.ruoyi.web.applicantion.service.impl;

import com.ruoyi.web.applicantion.mapper.AnwserQuestionMapper;
import com.ruoyi.web.applicantion.service.AnwserQuestionService;
import com.ruoyi.web.applicantion.service.dto.AnalysisDto;
import com.ruoyi.web.applicantion.service.dto.AnswerQuestionDto;
import com.ruoyi.web.applicantion.service.dto.SubtopicDto;
import com.ruoyi.web.applicantion.service.dto.subjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

@Service
public class AnwserQuestionServiceImpl implements AnwserQuestionService {
    @Autowired
    private AnwserQuestionMapper anwserQuestionMapper;

    @Override
    @Cacheable(value = "cacheName")
    public List<AnswerQuestionDto> questionList(Integer id) {
        return anwserQuestionMapper.questionList(id);
    }

    @Override
    public List<subjectDto> getSubList() {
        return anwserQuestionMapper.getSubList();
    }

    @Override
    public List<SubtopicDto> getTest(Integer id) {
        return anwserQuestionMapper.getTest(id);
    }

    @Override
    public Integer getCount(Integer id) {
        return anwserQuestionMapper.getCount(id);
    }

    @Override
    public List<AnalysisDto> getAnalysis(Integer id, Integer score) {
        System.out.println("ok");
        System.out.println("id:"+id+",score:"+score);
        return anwserQuestionMapper.getAnalysis(id, score);
    }
}

package com.ruoyi.web.applicantion.service;

import com.ruoyi.web.applicantion.service.dto.AnalysisDto;
import com.ruoyi.web.applicantion.service.dto.AnswerQuestionDto;
import com.ruoyi.web.applicantion.service.dto.SubtopicDto;
import com.ruoyi.web.applicantion.service.dto.subjectDto;

import java.util.List;

public interface AnwserQuestionService{
    List<AnswerQuestionDto> questionList(Integer id);

    List<subjectDto> getSubList();

    List<SubtopicDto> getTest(Integer id);

    Integer getCount(Integer id);

    List<AnalysisDto> getAnalysis(Integer id, Integer score);
}

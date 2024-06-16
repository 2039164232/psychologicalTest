package com.ruoyi.web.applicantion.mapper;

import com.ruoyi.web.applicantion.service.dto.AnalysisDto;
import com.ruoyi.web.applicantion.service.dto.AnswerQuestionDto;
import com.ruoyi.web.applicantion.service.dto.SubtopicDto;
import com.ruoyi.web.applicantion.service.dto.subjectDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface AnwserQuestionMapper {
    @Select("select * from anwser_question where subId=#{id}")
    List<AnswerQuestionDto> questionList(Integer id);

    @Select("select * from subject")
    List<subjectDto> getSubList();

    @Select("select subId,subTitle,subDescribe from subtopic where ID=#{id}")
    List<SubtopicDto> getTest(Integer id);

    @Select("SELECT COUNT(*) FROM anwser_question WHERE subId = #{id}")
    Integer getCount(Integer id);

    @Select("select analysis,suggestion,describe_report as describeReport from anwser_analysis WHERE subId=#{id} and ((min<=#{score} and max>#{score}) or (#{score}>= (SELECT MAX(min) from `anwser_analysis` where subId=#{id}) and min = (SELECT MAX(min) from `anwser_analysis` where subId=#{id})))")
    List<AnalysisDto> getAnalysis(@Param("id") Integer id, @Param("score") Integer score);

}

package com.ruoyi.web.applicantion.service.dto;

import lombok.Data;

@Data
public class AnswerQuestionDto {
    private Integer id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private Integer answerA;
    private Integer answerB;
    private Integer answerC;
    private Integer answerD;
    private Integer answerE;
    private Integer subId;
}

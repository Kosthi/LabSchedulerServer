package com.kosthi.labschedulerserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
public class Teacher extends User {

    // 名字
    private String teacherName;

    // 所属学院
    private String schoolName;

    // 教授学院 学院->专业->课程->班级
    private List<School> schoolTeach;

    public Teacher(String account, String password, List<School> schoolTeach) {
        super(account, password, false);
        this.schoolTeach = schoolTeach;
    }
}

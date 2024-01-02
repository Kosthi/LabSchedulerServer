package com.kosthi.labschedulerserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Jacksonized
public class ScheduleInfo {
    private List<String> teachersName;
    private String courseName;
    private String majorName;
    private List<ClassYearInfo> classYearInfos;
    private String note;

    @Override
    public String toString() {
        String teachers = String.join(" ", teachersName);

        List<String> formattedClassNames = classYearInfos.stream().map(classYearInfo -> '[' + classYearInfo.toString() + ']')
                .collect(Collectors.toList());
        String formattedClassNamesStr = String.join("", formattedClassNames);

        return "教师:" + teachers +
                "\n课程:" + courseName +
                "\n专业:" + majorName +
                formattedClassNamesStr +
                "\n备注:" + note;
    }
}

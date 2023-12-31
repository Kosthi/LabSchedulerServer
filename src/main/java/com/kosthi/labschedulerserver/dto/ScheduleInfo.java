package com.kosthi.labschedulerserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@Builder
public class ScheduleInfo {
    private List<String> teachersName;
    private String courseName;
    private String majorName;
    private List<String> className;
    private List<Integer> year;
    private String note;

    @Override
    public String toString() {
        String teachers = String.join(" ", teachersName);

        // 确保两个列表的长度一致
        if (className.size() != year.size()) {
            throw new RuntimeException();
        }

        System.out.println(className);
        System.out.println(year);

        int size = className.size();
        List<String> formattedClassNames = IntStream.range(0, size)
                .mapToObj(i -> '[' + String.format("%02d", year.get(i) % 100) + String.format("%02d", Integer.parseInt(className.get(i))) + ']')
                .collect(Collectors.toList());
        String formattedClassNamesStr = String.join("", formattedClassNames);

        return "教师:" + teachers +
                "\n课程:" + courseName +
                "\n专业:" + majorName +
                formattedClassNamesStr +
                "\n备注:" + note;
    }
}

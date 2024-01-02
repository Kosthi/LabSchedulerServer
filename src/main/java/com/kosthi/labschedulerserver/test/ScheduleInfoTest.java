package com.kosthi.labschedulerserver.test;

import java.util.ArrayList;
import java.util.List;

public class ScheduleInfoTest {
    public static void main(String[] args) {
        List<String> teacherList = new ArrayList<>();
        teacherList.add("于霞");
        teacherList.add("常燕");

        List<String> classList = new ArrayList<>();
        classList.add("1");
        classList.add("2");
        classList.add("3");

        List<Integer> yearList = new ArrayList<>();
        yearList.add(2021);
        yearList.add(2022);
        yearList.add(2021);

//        ScheduleInfo scheduleInfo = ScheduleInfo.builder()
//                .teachersName(teacherList)
//                .courseName("数据库与软件工程课程设计")
//                .majorName("计算机科学与技术")
//                .classYearInfos()
//                .note("无")
//                .build();
//        System.out.println(scheduleInfo);
    }
}

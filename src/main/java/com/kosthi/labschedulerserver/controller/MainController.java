package com.kosthi.labschedulerserver.controller;

import com.kosthi.labschedulerserver.dto.Calendar;
import com.kosthi.labschedulerserver.dto.*;
import com.kosthi.labschedulerserver.mapper.MainMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/api")
public class MainController {
    @Resource
    private MainMapper mainMapper;

    @GetMapping("/calendar")
    public Calendar getCalendar() {
        return mainMapper.getCalendar();
    }

    @GetMapping("/schoolLabs")
    public String getShoolLabs() {
        List<SchoolLab> mapList = mainMapper.getSchoolLabsMap();
        System.out.println(mapList);
        Map<String, List<String>> schoolLabsMap = new HashMap<>();
        mapList.stream().forEach(sb -> {
            String[] labsName = sb.getLabsName().split(",");
            Arrays.stream(labsName).forEach(labName -> schoolLabsMap.computeIfAbsent(sb.getSchoolName(), k -> new ArrayList<>()).add(labName));
        });
        return schoolLabsMap.toString();
    }

    @GetMapping("/schedule")
    public Map<Character, List<String>> getSchedule(@RequestParam("week") String week, @RequestParam("schoolName") String schoolName, @RequestParam("labName") String labName) {
        Map<Character, List<String>> result = new HashMap<>();
        List<MainSchedule> scheduleList = mainMapper.getSchedule(week, schoolName, labName);
        scheduleList.stream().forEach(schedule -> {
            List<String> teacherNameList = mainMapper.getTeacherNameByScheduleId(schedule.getId());
            String courseName = mainMapper.getCourseNameByCourseId(schedule.getCourseId());
            String majorName = mainMapper.getMajorNameByMajorId(schedule.getMajorId());
            List<ClassYearInfo> classYearInfoList = mainMapper.getClassYearInfoByScheduleId(schedule.getId());

            ScheduleInfo scheduleInfo = ScheduleInfo.builder()
                    .teachersName(teacherNameList)
                    .courseName(courseName)
                    .majorName(majorName)
                    .classYearInfos(classYearInfoList)
                    .note(schedule.getNote())
                    .build();

            result.computeIfAbsent(schedule.getWeekDay(), k -> new ArrayList<>()).add(schedule.getSession() + scheduleInfo.toString());
        });
        return result;
    }
}

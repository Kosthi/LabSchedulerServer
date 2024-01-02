package com.kosthi.labschedulerserver.controller;

import com.kosthi.labschedulerserver.dto.ClassYearInfo;
import com.kosthi.labschedulerserver.dto.Schedule;
import com.kosthi.labschedulerserver.dto.ScheduleReq;
import com.kosthi.labschedulerserver.mapper.ScheduleMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    @Resource
    private ScheduleMapper scheduleMapper;

    @PostMapping("schedule")
    public boolean schedule(@RequestBody ScheduleReq scheduleReq) {
        Long calendarId = scheduleMapper.getCalendarIdByYearAndSemester(scheduleReq.getCalendar().getYear(), scheduleReq.getCalendar().getSemester());
        if (calendarId == null) {
            return false;
        }
        String week = scheduleReq.getWeek();
        char weekDay = scheduleReq.getWeekDay();
        char session = scheduleReq.getSession();
        Long labId = scheduleMapper.getLabIdBySchoolNameAndLabName(scheduleReq.getSchoolName(), scheduleReq.getLabName());
        Long teacherId = scheduleMapper.getTeacherIdByAccount(scheduleReq.getAccount());
        Long courseId = scheduleMapper.getCourseIdByCourseName(scheduleReq.getScheduleInfo().getCourseName());
        Long majorId = scheduleMapper.getMajorIdByYearAndClassName(scheduleReq.getScheduleInfo().getClassYearInfos().get(0).getYear(), scheduleReq.getScheduleInfo().getClassYearInfos().get(0).getClassName());
        String note = scheduleReq.getScheduleInfo().getNote();

        Schedule schedule = Schedule.builder()
                .calendarId(calendarId)
                .week(week)
                .weekDay(weekDay)
                .session(session)
                .labId(labId)
                .courseId(courseId)
                .majorId(majorId)
                .note(note)
                .build();

        // scheduleMapper.addSchedule(schedule, calendarId, week, weekDay, session, labId, courseId, majorId, scheduleReq.getScheduleInfo().getNote());

        scheduleMapper.addSchedule(schedule);
        System.out.println(schedule.getId());

        List<ClassYearInfo> classYearInfoList = scheduleReq.getScheduleInfo().getClassYearInfos();

        classYearInfoList.stream().forEach(classYearInfo -> {
            Long classId = scheduleMapper.getClassIdByYearAndClassName(classYearInfo.getYear(), classYearInfo.getClassName());
            scheduleMapper.addScheduleClass(schedule.getId(), classId);
        });

        // 目前简单处理 加一门老师
        scheduleMapper.addScheduleTeacher(schedule.getId(), teacherId);

        return true;
    }
}

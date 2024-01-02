package com.kosthi.labschedulerserver.mapper;

import com.kosthi.labschedulerserver.dto.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface ScheduleMapper {

    Long getCalendarIdByYearAndSemester(@Param("year") String year, @Param("semester") char semester);

    Long getLabIdBySchoolNameAndLabName(@Param("schoolName") String schoolName, @Param("labName") String labName);

    Long getMajorIdByYearAndClassName(@Param("year") int year, @Param("className") String className);

    Long getCourseIdByCourseName(String courseName);

    Long getTeacherIdByAccount(String account);

    Long getClassIdByYearAndClassName(@Param("year") int year, @Param("className") String className);

    // 添加到lab_schedule表
//    Long addSchedule(@Param("scheduleId") Long id, @Param("calendarId") Long calendarId, @Param("week") String week,
//                     @Param("weekday") char weekday, @Param("session") char session, @Param("labId") Long labId,
//                     @Param("courseId") Long courseId, @Param("majorId") Long majorId, @Param("note") String note);

    int addSchedule(Schedule schedule);

    // 添加到lab_schedule_class表
    int addScheduleClass(@Param("scheduleId") Long scheduleId, @Param("classId") Long classId);

    // 添加到lab_schedule_teacher表
    int addScheduleTeacher(@Param("scheduleId") Long scheduleId, @Param("teacherId") Long teacherId);
}

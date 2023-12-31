package com.kosthi.labschedulerserver.mapper;

import com.kosthi.labschedulerserver.dto.ClassYearInfo;
import com.kosthi.labschedulerserver.dto.Schedule;
import com.kosthi.labschedulerserver.dto.ScheduleInfo;
import com.kosthi.labschedulerserver.dto.SchoolLab;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Mapper
@Transactional
public interface MainMapper {
    java.sql.Date getStartDate();

    List<SchoolLab> getSchoolLabsMap();

    List<Schedule> getSchedule(@Param("week") String week, @Param("schoolName") String schoolName, @Param("labName") String labName);

    List<String> getTeacherNameByScheduleId(Long scheduleId);

    String getCourseNameByCourseId(Long courseId);

    String getMajorNameByMajorId(Long majorId);

    List<ClassYearInfo> getClassYearInfoByScheduleId(Long scheduleId);
}

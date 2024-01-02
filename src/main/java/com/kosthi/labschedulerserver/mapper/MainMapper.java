package com.kosthi.labschedulerserver.mapper;

import com.kosthi.labschedulerserver.dto.Calendar;
import com.kosthi.labschedulerserver.dto.ClassYearInfo;
import com.kosthi.labschedulerserver.dto.MainSchedule;
import com.kosthi.labschedulerserver.dto.SchoolLab;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Mapper
@Transactional
public interface MainMapper {
    Calendar getCalendar();

    List<SchoolLab> getSchoolLabsMap();

    List<MainSchedule> getSchedule(@Param("week") String week, @Param("schoolName") String schoolName, @Param("labName") String labName);

    List<String> getTeacherNameByScheduleId(Long scheduleId);

    String getCourseNameByCourseId(Long courseId);

    String getMajorNameByMajorId(Long majorId);

    List<ClassYearInfo> getClassYearInfoByScheduleId(Long scheduleId);
}

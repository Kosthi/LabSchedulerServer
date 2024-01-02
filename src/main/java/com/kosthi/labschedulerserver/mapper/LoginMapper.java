package com.kosthi.labschedulerserver.mapper;

import com.kosthi.labschedulerserver.dto.ClassYearInfo;
import com.kosthi.labschedulerserver.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface LoginMapper {
    Boolean checkIfUserExists(String account);

    User findUserByAccount(String account);

    Boolean updateTeacherStatus(@Param("account") String account, @Param("isLogin") Boolean isLogin);

    // 用于建立教师实体
    String findTeacherNameByAccount(String account);

    String findSchoolNameByAccount(String account);

    List<String> querySchoolTeachNameByAccount(String account);

    List<String> queryMajorTeachByAccountAndSchoolName(@Param("account") String account, @Param("schoolName") String schoolName);

    List<String> queryCourseTeachByAccountAndSchoolNameAndMajorName(@Param("account") String account, @Param("schoolName") String schoolName, @Param("majorName") String majorName);

    List<ClassYearInfo> queryClassTeachByAccountAndSchoolNameAndMajorNameByCourseName(@Param("account") String account, @Param("schoolName") String schoolName, @Param("majorName") String majorName, @Param("courseName") String courseName);
}

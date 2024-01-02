package com.kosthi.labschedulerserver.controller;

import com.kosthi.labschedulerserver.dto.*;
import com.kosthi.labschedulerserver.mapper.LoginMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Resource
    private LoginMapper loginMapper;

    /**
     * 检查指定的账户是否存在。
     * 该方法用于查询数据库，以确定提供的账户名是否已经存在于系统中。它在管理员表和教师表中查找与提供的账户名匹配的记录。
     *
     * @param account 要检查的账户名。该参数应该是一个非空的字符串，代表用户的唯一账户名。
     * @return 返回一个布尔值。如果账户存在于管理员表或教师表中，返回 true；否则返回 false。
     */
    @GetMapping("/exist")
    public String checkIfUserExists(@RequestParam("account") String account) {
        return loginMapper.checkIfUserExists(account).toString();
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User foundUser = loginMapper.findUserByAccount(user.getAccount());
        if (foundUser.getPassword().equals(user.getPassword())) {
            if (foundUser.getIsAdmin()) {
                foundUser.setPassword(null);
                return (Admin) foundUser;
            }

            String account = user.getAccount();

            // 更新登录态
            loginMapper.updateTeacherStatus(account, true);

            Teacher teacher = new Teacher(account, null, null);

            // 学院->专业->课程->班级
            String teacherName = loginMapper.findTeacherNameByAccount(account);
            String schoolName = loginMapper.findSchoolNameByAccount(account);

            teacher.setTeacherName(teacherName);
            teacher.setSchoolName(schoolName);

            List<School> schoolTeachList = new ArrayList<>();
            List<String> schoolTeachNameList = loginMapper.querySchoolTeachNameByAccount(account);
            schoolTeachNameList.stream().forEach(schoolTeachName -> {

                List<Major> majorList = new ArrayList<>();
                List<String> majorTeachList = loginMapper.queryMajorTeachByAccountAndSchoolName(account, schoolTeachName);
                majorTeachList.stream().forEach(majorTeach -> {

                    List<Course> courseList = new ArrayList<>();
                    List<String> courseTeachList = loginMapper.queryCourseTeachByAccountAndSchoolNameAndMajorName(account, schoolTeachName, majorTeach);
                    courseTeachList.stream().forEach(courseTeach -> {

                        List<ClassYearInfo> className = loginMapper.queryClassTeachByAccountAndSchoolNameAndMajorNameByCourseName(account, schoolTeachName, majorTeach, courseTeach);
                        Course course = new Course(courseTeach, className);
                        courseList.add(course);
                    });

                    majorList.add(new Major(majorTeach, courseList));
                });

                schoolTeachList.add(new School(schoolTeachName, majorList));
            });

            teacher.setSchoolTeach(schoolTeachList);
            return teacher;
        }
        return null;
    }

    @PostMapping("logout")
    public void logout(@RequestParam("account") String account) {
        loginMapper.updateTeacherStatus(account, false);
    }
}

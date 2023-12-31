package com.kosthi.labschedulerserver.test;

import com.kosthi.labschedulerserver.dto.Schedule;
import com.kosthi.labschedulerserver.mapper.MainMapper;

import javax.annotation.Resource;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MainMapperTest {
    @Resource
    public MainMapper mainMapper;

    @Test
    public void getSchedule(String[] args) {
        List<Schedule> scheduleList = mainMapper.getSchedule("19", "信息科学与工程学院", "220");
        System.out.println(scheduleList);
    }
}

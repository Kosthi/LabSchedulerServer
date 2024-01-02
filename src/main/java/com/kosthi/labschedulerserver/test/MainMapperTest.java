package com.kosthi.labschedulerserver.test;

import com.kosthi.labschedulerserver.dto.MainSchedule;
import com.kosthi.labschedulerserver.mapper.MainMapper;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

public class MainMapperTest {
    @Resource
    public MainMapper mainMapper;

    @Test
    public void getSchedule(String[] args) {
        List<MainSchedule> scheduleList = mainMapper.getSchedule("19", "信息科学与工程学院", "220");
        System.out.println(scheduleList);
    }
}

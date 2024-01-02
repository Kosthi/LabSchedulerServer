package com.kosthi.labschedulerserver.dto;


import lombok.Getter;
import lombok.Setter;

// 读出数据库对象
@Setter
@Getter
public class MainSchedule {
    private Long id;
    private char weekDay;
    private char session;
    private Long courseId;
    private Long majorId;
    private String note;
}

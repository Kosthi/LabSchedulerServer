package com.kosthi.labschedulerserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

// 写入数据库对象
@Setter
@Getter
@Builder
@Jacksonized
public class Schedule {
    private Long id;
    private Long calendarId;
    private String week;
    private char weekDay;
    private char session;
    private Long labId;
    private Long courseId;
    private Long majorId;
    private String note;
}

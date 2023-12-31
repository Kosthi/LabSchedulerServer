package com.kosthi.labschedulerserver.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Schedule {
    private Long id;
    private char weekDay;
    private char session;
    private Long courseId;
    private Long majorId;
    private String note;
}

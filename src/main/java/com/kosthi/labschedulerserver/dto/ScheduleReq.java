package com.kosthi.labschedulerserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Setter
@Getter
@Builder
@Jacksonized
public class ScheduleReq {
    private String account;
    private Calendar calendar;
    private String week;
    private String schoolName;
    private String labName;
    private Character weekDay;
    private Character session;
    private ScheduleInfo scheduleInfo;
}

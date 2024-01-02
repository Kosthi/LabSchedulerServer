package com.kosthi.labschedulerserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Calendar {
    private String year;
    private char semester;
    private LocalDate startDate;
}

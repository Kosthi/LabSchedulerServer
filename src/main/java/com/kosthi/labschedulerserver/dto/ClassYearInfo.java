package com.kosthi.labschedulerserver.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassYearInfo {
    private String className;
    private int year;

    @Override
    public String toString() {
        return String.format("%02d", year % 100) + String.format("%02d", Integer.parseInt(className));
    }
}

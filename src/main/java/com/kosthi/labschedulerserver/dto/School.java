package com.kosthi.labschedulerserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class School {
    private String schoolName;
    private List<Major> majors;
}

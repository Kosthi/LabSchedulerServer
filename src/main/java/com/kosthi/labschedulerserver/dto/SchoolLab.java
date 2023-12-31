package com.kosthi.labschedulerserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SchoolLab {
    private String schoolName;
    private String labsName; // 实验室名称，由 GROUP_CONCAT 生成的字符串
}

package com.kosthi.labschedulerserver.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class User {
    private String account;
    private String password;
    private Boolean isAdmin;

    @JsonCreator
    public User(@JsonProperty("account") String account, @JsonProperty("password") String password) {
        this.account = account;
        this.password = password;
    }
}

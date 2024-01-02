package com.kosthi.labschedulerserver.dto;

public class Admin extends User {
    public Admin(String account, String password) {
        super(account, password, true);
    }
}

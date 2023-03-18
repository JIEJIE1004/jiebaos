package com.wz.jiebaos.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum  Admin {
    VAR1(1,"管理员"),
    VAR2(0,"普通");

    @EnumValue
    private Integer adminId;
    private String adminName;

    Admin(Integer adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
    }
}

package com.wz.jiebaos.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName account
 */
@TableName(value ="account")
@Data
public class Account implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private Long account;

    /**
     * 密码
     */
    private String password;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
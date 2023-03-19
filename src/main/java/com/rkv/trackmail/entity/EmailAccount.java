package com.rkv.trackmail.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("EmailAccount")
@Data
public class EmailAccount implements Serializable {
    private String id;
    private String emailId;
    private String appPwd;
}

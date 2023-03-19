package com.rkv.trackmail.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDate;


@RedisHash("Recipient")
@Data
public class EmailRecipient implements Serializable {
    private String id;
    private String emailAddress;
    private String emailSubject;
    private String pixelId;
    private int readCount=0;
    private boolean failedToSend;
    private LocalDate creationDateTime;

    public EmailRecipient(String id, String emailAddress, String emailSubject, LocalDate creationDateTime){
        this.emailAddress = emailAddress;
        this.creationDateTime = creationDateTime;
        this.id = id;
        this.emailSubject = emailSubject;
    }
}

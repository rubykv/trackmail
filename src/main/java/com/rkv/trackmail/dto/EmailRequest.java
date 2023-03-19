package com.rkv.trackmail.dto;

import java.util.Objects;

public record EmailRequest(String subject, String content, String toAddress) {
    public EmailRequest {
        Objects.requireNonNull(subject, "Subject is required");
        Objects.requireNonNull(content, "Content is required");
        Objects.requireNonNull(toAddress, "To Address is required");
    }
}

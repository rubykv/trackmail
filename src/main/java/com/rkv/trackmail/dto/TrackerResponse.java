package com.rkv.trackmail.dto;

import java.time.LocalDate;

public record TrackerResponse(String emailId, LocalDate sentOn, boolean isSent, boolean isRead) {
}

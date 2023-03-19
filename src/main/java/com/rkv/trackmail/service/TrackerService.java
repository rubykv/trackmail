package com.rkv.trackmail.service;

import com.rkv.trackmail.dto.TrackerRequest;
import com.rkv.trackmail.dto.TrackerResponse;
import com.rkv.trackmail.entity.EmailRecipient;
import com.rkv.trackmail.repository.EmailRecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackerService {

    @Autowired
    private EmailRecipientRepository emailRecipientRepository;

    public List<TrackerResponse> getEmailAnalytics(TrackerRequest request) {
        List<EmailRecipient> recipients = emailRecipientRepository.findByEmailAddressAndEmailSubject(request.emailId(), request.emailSubject());
        return recipients.stream().map(r -> new TrackerResponse(r.getEmailAddress(), r.getCreationDateTime(), !r.isFailedToSend(), r.getReadCount() > 0)).toList();
    }

    public void updateEmailAnalyticsInfo(String id) {
        try {
            EmailRecipient recipient = emailRecipientRepository.findById(id).get();
            recipient.setReadCount(recipient.getReadCount() + 1);
            emailRecipientRepository.save(recipient);
        } catch (Exception ex) {
            //TODO add logs
            System.out.println(ex);
        }
    }
}

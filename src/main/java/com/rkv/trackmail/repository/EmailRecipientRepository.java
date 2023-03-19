package com.rkv.trackmail.repository;

import com.rkv.trackmail.entity.EmailRecipient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailRecipientRepository extends CrudRepository<EmailRecipient,String> {
    List<EmailRecipient> findByEmailAddressAndEmailSubject(String emailAddress, String emailSubject);

}

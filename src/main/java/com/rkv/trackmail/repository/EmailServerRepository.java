package com.rkv.trackmail.repository;

import com.rkv.trackmail.entity.EmailAccount;
import org.springframework.data.repository.CrudRepository;

public interface EmailServerRepository extends CrudRepository<EmailAccount,String> {
}

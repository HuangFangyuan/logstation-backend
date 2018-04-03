package com.hfy.logstation.repository;

import com.hfy.logstation.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by HuangFangyuan on 2018/3/13.
 */
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Contact getByDefaultUse(boolean defaultUse);
}

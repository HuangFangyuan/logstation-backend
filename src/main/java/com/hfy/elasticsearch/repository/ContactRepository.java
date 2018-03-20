package com.hfy.elasticsearch.repository;

import com.hfy.elasticsearch.entity.Contact;
import com.hfy.elasticsearch.entity.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by HuangFangyuan on 2018/3/13.
 */
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Contact getByDefaultUse(boolean defaultUse);
}

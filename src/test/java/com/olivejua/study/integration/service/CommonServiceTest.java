package com.olivejua.study.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public abstract class CommonServiceTest {
    @Autowired
    protected EntityManager em;
}

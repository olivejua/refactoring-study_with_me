package com.olivejua.study;

import com.olivejua.study.config.auth.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
public class StudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
	}

	@Bean
	public HttpSessionListener httpSessionListener() {
		return new SessionListener();
	}
}

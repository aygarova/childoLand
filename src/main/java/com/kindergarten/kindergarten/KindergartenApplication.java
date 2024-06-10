package com.kindergarten.kindergarten;

import com.kindergarten.kindergarten.services.TeacherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class KindergartenApplication implements CommandLineRunner {

	private final TeacherService teacherService;

	public KindergartenApplication(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public static void main(String[] args) {
		System.setProperty("jdk.tls.client.protocols", "TLSv1.2");

		SpringApplication.run(KindergartenApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.teacherService.initAdmin();
	}

}

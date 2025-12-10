package com.example.mini_project_community_center;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;


@SpringBootApplication
public class MiniProjectCommunityCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniProjectCommunityCenterApplication.class, args);
	}

	@PostConstruct
	public void init() {
		System.out.println(">>>JVM TIMEZONE=" + ZoneId.systemDefault());
	}
}

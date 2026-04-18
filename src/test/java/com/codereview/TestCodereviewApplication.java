package com.codereview;

import org.springframework.boot.SpringApplication;

public class TestCodereviewApplication {

	public static void main(String[] args) {
		SpringApplication.from(CodereviewApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

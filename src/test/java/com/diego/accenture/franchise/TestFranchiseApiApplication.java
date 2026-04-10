package com.diego.accenture.franchise;

import org.springframework.boot.SpringApplication;

public class TestFranchiseApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(FranchiseApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

package com.rkv.trackmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TrackmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackmailApplication.class, args);
	}

}

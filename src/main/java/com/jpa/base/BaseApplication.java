package com.jpa.base;

import com.jpa.base.Dao.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BaseApplication {
	public static void main(String[] args) {
		ApplicationContext context =SpringApplication.run(BaseApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);
	}
}

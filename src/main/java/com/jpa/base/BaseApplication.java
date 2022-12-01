package com.jpa.base;

import com.jpa.base.Dao.Repository.UserRepository;
import com.jpa.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BaseApplication {
	@Autowired
	UserService userService;
	public static void main(String[] args) {
		ApplicationContext context =SpringApplication.run(BaseApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);
	}
}

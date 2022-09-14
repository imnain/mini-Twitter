package com.jpa.base;

import com.jpa.base.Dao.UserRepository;
import com.jpa.base.Entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BaseApplication {

	public static void main(String[] args) {

		ApplicationContext context =SpringApplication.run(BaseApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);

//		User user = new User();
//		user.setName("mandeep");
//		user.setEmail("mandeepnain84@gmail.com");
//		user.setPassword("nain");
//		user.setBio("this is mandeep");
//		user.setRole("admin");
//		User user1 = userRepository.save(user);
//		System.out.print(user1);
	}

}

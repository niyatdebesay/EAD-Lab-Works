package com.example.TodoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan("com.example.TodoApp")
public class TodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
		
		
	}
	
	@Bean
	public ServletRegistrationBean<TaskRegistrationServlet> taskRegistrationServlet(){
		return new ServletRegistrationBean<>(new TaskRegistrationServlet(), "/register");
	}
	@Bean
	public ServletRegistrationBean<DisplayTask>displayTask(){
		return new ServletRegistrationBean<DisplayTask>(new DisplayTask(null), "/display");
	}
	@Bean
	public ServletRegistrationBean<DeleteTaskServlet>deleteServlet(){
		return new ServletRegistrationBean<DeleteTaskServlet>(new DeleteTaskServlet(), "/delete");
	}
}

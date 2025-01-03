package com.example.TodoApp;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString

public class Lombok {

	@Getter @Setter @NonNull
	private String name;
	
	@Getter @Setter
	private int age;

}

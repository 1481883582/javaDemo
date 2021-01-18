package com.java_fx;


import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaFxApplication {

	public static void main(String[] args) {
		//TODO JVM中一定要加   -Djava.awt.headless=false -Dfile.encoding=GBK
		SpringApplication.run(JavaFxApplication.class, args);
		Application.launch(TestApplication.class, args);
	}

}

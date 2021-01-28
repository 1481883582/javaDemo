package com.ftp;

import com.ftp.fx.TestApplication;
import com.ftp.fx.application.SysApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FtpApplication {
	private static ApplicationContext applicationContext;

	public static FXMLLoader LoadFxml(String fxmlPath) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(FtpApplication.class.getResource(fxmlPath));
		fxmlLoader.setControllerFactory(applicationContext::getBean);
		return fxmlLoader;
	}

	//TODO -Djava.awt.headless=false -Dfile.encoding=GBK
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(FtpApplication.class, args);
		Application.launch(SysApplication.class, args);
	}

}

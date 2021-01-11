package com;

import com.utils.FileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CopyFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(CopyFileApplication.class, args);

		try {
			FileUtil.copyConfig("/txt/测试.txt", "txt", "你好.txt");
			FileUtil.copyConfig("/application.properties", "", "application.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

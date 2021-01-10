package com.bo;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Accessors(prefix = "aa")  set  get  aaName   会忽略aa   aaname  不会忽略   只忽略驼峰之前一样的字符
 */
@Accessors(prefix = "aa")
@Data
public class Student3 {
	private String aaName;
	private Integer aaSize;

	public static void main(String[] args) {
		Student3 student1 = new Student3();
		//set  get  aaName   会忽略aa   aaname  不会忽略   只忽略驼峰之前一样的字符
		student1.getName();
		student1.setName("你好");

	}
}


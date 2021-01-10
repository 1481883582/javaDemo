package com.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Accessors(fluent = true)  生产的 Get Set  不会使用驼峰方式命名  并且不会有get  set
 */
@Accessors(fluent = true)
@Data
public class Student1 {
	private String name;
	private String size;
	public static void main(String[] args) {
		Student1 student1 = new Student1();
//      size 的get方法
		student1.size();
//		name 的set方法
		student1.name("我是名字");
	}
}


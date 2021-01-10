package com.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Accessors(chain = true) set返回自己  链式
 */
@Accessors(chain = true)
@Data
public class Student2 {
	private String name;
	private Integer size;

	public static void main(String[] args) {
		Student2 student1 = new Student2();
		//set  返回对象自己  链式
		student1
				.setName("我是名字")
				.setSize(10000);

	}
}


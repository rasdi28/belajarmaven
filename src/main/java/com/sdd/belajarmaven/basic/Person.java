package com.sdd.belajarmaven.basic;

public class Person {

	String name;
	String address;
	
	final String country = "indonesia";
	
	Person(String paramname, String paramaddress) {
		name = paramname;
		address = paramaddress;
	}
	
	void sayHello(String paramName) {
		System.out.println("Hello" + paramName+ "nama saya :" + name);
	}
}

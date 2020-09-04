package com.murali.school.models;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.murali.school.constans.TeacherType;

@Entity
@Table(name="TBL_TEACHER")
public class Teacher {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private int age;
	private Date joinDate;	
	private String address;
	private TeacherType teacherType;
	public TeacherType getTeacherType() {
		return teacherType;
	}
	public void setTeacherType(TeacherType teacherType) {
		this.teacherType = teacherType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
}

package com.murali.school.teacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murali.school.models.Teacher;
import com.murali.school.teacher.service.TeacherService;

@RestController
@RequestMapping("/teacherservice/api")
public class TeacherController {
	
	@Autowired
	TeacherService service;
	
	@PostMapping("/teacher")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Teacher saveTeacher(@RequestBody Teacher teacher) {
		return service.save(teacher);
	}
	
	@PutMapping("/teacher")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Teacher updateTeacher(@RequestBody Teacher teacher) {
		return service.update(teacher);
	}
	@DeleteMapping("/teacher/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteTeacher(@PathVariable("id") int id) {		
		service.delete(id);
	}
	@GetMapping("/teacher/find/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Teacher findTeacher(@PathVariable("id") int id) {
		return service.find(id);
	}
	
	@GetMapping("/teacher/{name}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Teacher findTeacher(@PathVariable("name") String name) {
		return service.findTeacherByname(name);
	}
	
}

package com.murali.school.teacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/services")
public class TeacherController {
	
	@Autowired
	TeacherService service;
	
	@PostMapping("/teacher")
	public Teacher saveTeacher(@RequestBody Teacher teacher) {
		return service.save(teacher);
	}
	
	@PutMapping("/teacher")
	public Teacher updateTeacher(@RequestBody Teacher teacher) {
		return service.update(teacher);
	}
	@DeleteMapping("/teacher/{id}")
	public void deleteTeacher(@PathVariable("id") int id) {		
		service.delete(id);
	}
	@GetMapping("/teacher/{id}")
	public Teacher findTeacher(@PathVariable("id") int id) {
		return service.find(id);
	}
	
}

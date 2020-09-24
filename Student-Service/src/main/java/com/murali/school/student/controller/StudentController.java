package com.murali.school.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murali.school.models.Student;
import com.murali.school.student.service.StudentService;

@RestController
@RequestMapping("/studentservice/api")
public class StudentController {

	@Autowired
	StudentService service;
	
	@PostMapping("/student")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Student createStudent(@RequestBody Student student) {
		return service.save(student);
	}
	@PutMapping("/student")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
		Student updatedStudent= service.update(student);
		if(updatedStudent==null) {
			return new ResponseEntity<Student>(HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<Student>(updatedStudent,HttpStatus.OK);
	}
	@DeleteMapping("/student/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteStudent(@PathVariable("id") int studentId) {
		service.delete(studentId);
		
	}
	@GetMapping("/student/find/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Student> findStudent(@PathVariable("id") int studentId) {
		Student student= service.find(studentId);
		if(student==null) {
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Student>(student,HttpStatus.OK);
	}
	
	@GetMapping("/student/{name}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Student> findStudentByName(@PathVariable("name") String studentName) {
		Student student= service.findStudentByName(studentName);
		if(student==null) {
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Student>(student,HttpStatus.OK);
	}
	public StudentService getService() {
		return service;
	}
	public void setService(StudentService service) {
		this.service = service;
	}
	
	
}

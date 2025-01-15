package com.cicad.app.controller;

import com.cicad.app.entities.Student;
import com.cicad.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Object get(@PathVariable Integer id) {
		return studentService.get(id);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Student> getAll() {
		return studentService.getAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Object create(@RequestBody Student sourceStudent) {
		return studentService.create(sourceStudent);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Student update(@PathVariable Integer id, @RequestBody Student sourceStudent) {
		sourceStudent.setId(id);
		return studentService.update(sourceStudent);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public void delete(@PathVariable Integer id) {
		studentService.delete(id);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public List<Student> filterStudents(@RequestParam(required = false) String firstName,
											 @RequestParam(required = false) String lastName,
											 @RequestParam(required = false) Integer programId,
											@RequestParam(required = false) Integer courseId,
											@RequestParam(defaultValue = "0") int page,
											@RequestParam(defaultValue = "5") int size) {
		return studentService.filterStudents(firstName, lastName, programId, courseId, page, size);
	}

	@GetMapping("/count")
	public long getCountOfStudents() {
		return studentService.getStudentCount();
	}

	@GetMapping("/countFilter")
	public long countData(@RequestParam(required = false) String firstName,
						  @RequestParam(required = false) String lastName,
						  @RequestParam(required = false) Integer programId,
						  @RequestParam(required = false) Integer courseId) {
		return studentService.countData(firstName, lastName, programId, courseId);
	}

	}

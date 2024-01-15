package com.cicad.app.controller;

import com.cicad.app.entities.Student;
import com.cicad.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Object get(@PathVariable Integer id) {
		return studentService.get(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Object create(@RequestBody Student sourceStudent) {
		return studentService.create(sourceStudent);
	}

}

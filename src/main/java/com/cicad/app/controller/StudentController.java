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

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id) {
		studentService.delete(id);
	}

}

package com.cicad.app.controller;

import com.cicad.app.entities.Program;
import com.cicad.app.entities.Student;
import com.cicad.app.entities.Course;
import com.cicad.app.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/course")

public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Integer id) {return courseService.get(id);}

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Course> getAll() {return courseService.getAll();}

    @RequestMapping(method = RequestMethod.POST)
    public Object create(@RequestBody Course sourceCourse) {return courseService.create(sourceCourse);}

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {courseService.delete(id);}
}


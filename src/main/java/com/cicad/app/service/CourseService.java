package com.cicad.app.service;

import com.cicad.app.entities.Course;
import com.cicad.app.repository.CourseRepository;
import com.cicad.app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course get(Integer id) {
        // do other stuff
        return courseRepository.get(id);
    }

    public List<Course> getAll() {
        return courseRepository.findAllCourses();
    }

    public Course create(Course sourceCourse) {
        Course actualCourse = new Course();
        actualCourse.setName(sourceCourse.getName());
        return courseRepository.create(actualCourse);
    }

    public void delete(Integer id) {
        courseRepository.delete(id);
    }
}

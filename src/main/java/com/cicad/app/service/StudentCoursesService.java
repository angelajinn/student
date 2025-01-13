package com.cicad.app.service;

import com.cicad.app.entities.Course;
import com.cicad.app.entities.Student;
import com.cicad.app.entities.StudentCourses;
import com.cicad.app.repository.CourseRepository;
import com.cicad.app.repository.StudentRepository;
import com.cicad.app.repository.StudentCoursesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StudentCoursesService {
    @Autowired
    private StudentCoursesRepository studentCoursesRepository;

    public void enroll(Integer studentId, List<Integer> courseIds) {
        studentCoursesRepository.enroll(studentId, courseIds);
    }

    public void updateStudentCourses(Integer studentId, List<Integer> courseIds) {
        studentCoursesRepository.updateStudentCourses(studentId, courseIds);
    }
}

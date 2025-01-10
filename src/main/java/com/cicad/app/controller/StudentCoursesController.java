package com.cicad.app.controller;

import com.cicad.app.entities.Course;
import com.cicad.app.entities.Student;
import com.cicad.app.entities.StudentCourses;

import com.cicad.app.service.ProgramService;
import com.cicad.app.service.StudentCoursesService;
import com.cicad.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/student-courses")

public class StudentCoursesController {
    @Autowired
    private StudentCoursesService studentCoursesService;

    public class StudentCoursesRequest {
        private List<Integer> courseIds;

        public List<Integer> getCourseIds() {
            return courseIds;
        }

        public void setCourseIds(List<Integer> courseIds) {
            this.courseIds = courseIds;
        }
    }

    @RequestMapping(value = "/{studentId}/enroll", method = RequestMethod.POST)
    public void enrollStudentInCourses(@PathVariable Integer studentId, @RequestBody StudentCoursesRequest request) {
        studentCoursesService.enroll(studentId, request.getCourseIds());
    }
}

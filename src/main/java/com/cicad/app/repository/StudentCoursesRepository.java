package com.cicad.app.repository;

import com.cicad.app.entities.Course;
import com.cicad.app.entities.Student;
import com.cicad.app.entities.StudentCourses;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional

public class StudentCoursesRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public StudentCourses get(Integer id) { return entityManager.find(StudentCourses.class, id); }

    public void enroll(Integer studentId, List<Integer> courseIds) {
        Student student = entityManager.find(Student.class, studentId);

        if (student == null) {
            throw new IllegalArgumentException("Student with id " + studentId + " not found");
        }

        for (Integer courseId : courseIds) {
            Course course = entityManager.find(Course.class, courseId);

            StudentCourses studentCourses = new StudentCourses();
            studentCourses.setStudent(student);
            studentCourses.setCourse(course);

            entityManager.persist(studentCourses);
        }
    }

}

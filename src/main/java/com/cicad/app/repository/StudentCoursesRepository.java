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

    public void updateStudentCourses(Integer studentId, List<Integer> courseIds) {
        Student student = entityManager.find(Student.class, studentId);

        if (student == null) {
            throw new IllegalArgumentException("Student with id " + studentId + " not found");
        }

        // This part gets all course enrollments for a student
        // Queries StudentCourses table and filters results to only have rows with the student's ID and returns the Student Courses objects
        List<StudentCourses> currentEnrollments = entityManager.createQuery(
                "SELECT sc FROM StudentCourses sc WHERE sc.student.id = :studentId", StudentCourses.class)
                .setParameter("studentId", studentId) // binds value of variable studentId to :studentId parameter in query
                .getResultList(); // gets list of StudentCourses objects

        for (StudentCourses studentCourses : currentEnrollments) {
            entityManager.remove(studentCourses);
        }

        for(Integer courseId : courseIds) {
            Course course = entityManager.find(Course.class, courseId);
            if (course != null) {
                StudentCourses studentCourses = new StudentCourses();
                studentCourses.setStudent(student);
                studentCourses.setCourse(course);

                entityManager.persist(studentCourses);
            }
        }
    }

}

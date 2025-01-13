package com.cicad.app.repository;

import com.cicad.app.entities.Program;
import com.cicad.app.entities.Course;
import com.cicad.app.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional

public class CourseRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Course get(Integer id) { return entityManager.find(Course.class, id); }

    public Course create(Course actualCourse) {
        entityManager.persist(actualCourse);
        return actualCourse;
    }

    public List<Course> findAllCourses() {
        String jpql = "SELECT c FROM Course c";
        return entityManager.createQuery(jpql, Course.class).getResultList();
    }

    public Course findById(Integer id) {
        return entityManager.find(Course.class, id);
    }

    public Course update(Course actualCourse) {return entityManager.merge(actualCourse); }

    public void delete(Integer id) {
        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            entityManager.remove(course);
        } else {
            throw new IllegalArgumentException("Course not found");
        }
    }
}

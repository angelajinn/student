package com.cicad.app.repository;

import com.cicad.app.entities.Course;
import com.cicad.app.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class StudentRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public Student get(Integer id) {
		return entityManager.find(Student.class, id);
	}

	public Student create(Student actualStudent) {
		entityManager.persist(actualStudent);
		return actualStudent;
	}

	public Student findById(Integer id) {
		return entityManager.find(Student.class, id);
	}

	public Student update(Student actualStudent) {
		return entityManager.merge(actualStudent);
	}

	public List<Student> findAllStudents() {
		String jpql = "SELECT s FROM Student s"; // JPQL query to fetch all students
		return entityManager.createQuery(jpql, Student.class).getResultList(); // Executes the JPQL query and maps the result to Student entities
	}

	public void delete(Integer id) {
		Student student = entityManager.find(Student.class, id);
		if (student != null) {
			entityManager.remove(student);
		} else {
			throw new IllegalArgumentException("Student with id " + id + " not found");
		}
	}


}

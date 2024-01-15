package com.cicad.app.repository;

import com.cicad.app.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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


}

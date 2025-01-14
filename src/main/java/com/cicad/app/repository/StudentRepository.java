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

	public List<Student> filterStudents(String firstName, String lastName, Integer programId, int page, int size) {
		// Start the query string with a base query for selecting students
		StringBuilder jpql = new StringBuilder("SELECT s FROM Student s WHERE 1=1");

		// Append filters based on the parameters provided
		if (firstName != null ) {
			jpql.append(" AND LOWER(s.firstName) LIKE LOWER(:firstName)");
		}
		if (lastName != null ) {
			jpql.append(" AND LOWER(s.lastName) LIKE LOWER(:lastName)");
		}
		if (programId != null) {
			jpql.append(" AND s.program.id = :programId");
		}

		// Create the query using the dynamic jpql string
		var query = entityManager.createQuery(jpql.toString(), Student.class);

		// Bind the parameters to the query
		if (firstName != null && !firstName.trim().isEmpty()) {
			query.setParameter("firstName", "%" + firstName.toLowerCase() + "%");
		}
		if (lastName != null && !lastName.trim().isEmpty()) {
			query.setParameter("lastName", "%" + lastName.toLowerCase() + "%");
		}
		if (programId != null) {
			query.setParameter("programId", programId);
		}

		if (page == 0) {
			query.setFirstResult(0);
		} else {
			query.setFirstResult((page - 1) * size);
		}
		query.setMaxResults(size);

		// Execute the query and return the results
		return query.getResultList();
	}

}


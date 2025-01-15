package com.cicad.app.repository;

import com.cicad.app.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

	public List<Student> filterStudents(String firstName, String lastName, Integer programId, Integer courseId, int page, int size) {
		// Start the query string with a base query for selecting students
		StringBuilder jpql = new StringBuilder("SELECT s FROM Student s");

		// join StudentCourses table to filter by courseId
		if (courseId != null) {
			jpql.append(" JOIN s.courses sc JOIN sc.course c");
		}

		jpql.append(" WHERE 1=1");

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
		if (courseId != null) {
			jpql.append(" AND c.id = :courseId");
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
		if (courseId != null) {
			query.setParameter("courseId", courseId);
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

		// count always return type long
	public long countStudents() {
		String jpql = "SELECT COUNT(*) FROM Student";
		return entityManager.createQuery(jpql, Long.class).getSingleResult();
	}


	public long countData(String firstName, String lastName, Integer programId, Integer courseId) {

		// Start the query string with a base query for selecting students
		StringBuilder queryStr = new StringBuilder("SELECT COUNT(s) FROM Student s");

		// join StudentCourses table to filter by courseId
		if (courseId != null) {
			queryStr.append(" JOIN s.courses sc JOIN sc.course c");
		}

		queryStr.append(" WHERE 1=1");

//		// Count all students in the query
//		// 'WHERE 1=1' is always true to allow for AND conditions
//		StringBuilder queryStr = new StringBuilder("SELECT COUNT(s) FROM Student s WHERE 1=1");

		// Add conditions/FILTERS to query if they are not null -> select the rows from the database
		if (firstName != null && !firstName.isEmpty()) {
			queryStr.append(" AND s.firstName LIKE :firstName");
		}
		if (lastName != null && !lastName.isEmpty()) {
			queryStr.append(" AND s.lastName LIKE :lastName");
		}
		if (programId != null) {
			queryStr.append(" AND s.program.id = :programId");
		}
		if (courseId != null) {
			queryStr.append(" AND c.id = :courseId");
			// student doesn't directly hold a course field (many-to-many), rather it holds a collection of StudentCourses which then connects to Course
			// s.course.id would lead to incorrect queries because assumes a direct reference to Course from Student
			// c.id: c refers to course identity that is introduced by the JOIN clause from "join sc.course c"
			// JOIN clause establishes the correct relationship between Student/Course as course is properly joined through StudentCourses relationship
		}

		// Create the query based on the querystring
		Query query = entityManager.createQuery(queryStr.toString());

		// Set parameters to provide actual values
		if (firstName != null && !firstName.isEmpty()) {
			query.setParameter("firstName", "%" + firstName + "%");
		}
		if (lastName != null && !lastName.isEmpty()) {
			query.setParameter("lastName", "%" + lastName + "%");
		}
		if (programId != null) {
			query.setParameter("programId", programId);
		}
		if (courseId != null) {
			query.setParameter("courseId", courseId);
		}

		// Execute the query and return the count
		return (long) query.getSingleResult();
	}
}


package com.cicad.app.service;

import com.cicad.app.entities.Student;
import com.cicad.app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public Student get(Integer id) {
		// do other stuff
		return studentRepository.get(id);
	}

	public List<Student> getAll() {
		return studentRepository.findAllStudents();
	}

	public Student create(Student sourceStudent) {
		Student actualStudent = new Student();
		actualStudent.setFirstName(sourceStudent.getFirstName());
		actualStudent.setLastName(sourceStudent.getLastName());
		if (sourceStudent.getDateOfBirth() != null
				&& sourceStudent.getDateOfBirth().isBefore(LocalDate.now())) {
			actualStudent.setDateOfBirth(sourceStudent.getDateOfBirth());
		}
		actualStudent.setGpa(sourceStudent.getGpa());
		return studentRepository.save(actualStudent);
	}

	public Student update(Student sourceStudent) {
		// Fetch the existing student
		Student existingStudent = studentRepository.get(sourceStudent.getId());

			if (sourceStudent.getFirstName() != null) {
				existingStudent.setFirstName(sourceStudent.getFirstName());
			}
			if (sourceStudent.getLastName() != null) {
				existingStudent.setLastName(sourceStudent.getLastName());
			}
			if (sourceStudent.getDateOfBirth() != null) {
				existingStudent.setDateOfBirth(sourceStudent.getDateOfBirth());
			}
			if (sourceStudent.getGpa() != null) {
				existingStudent.setGpa(sourceStudent.getGpa());
			}
			// Save updated entity
			return studentRepository.save(existingStudent);
	}

	public void delete(Integer id) {
		studentRepository.delete(id);
	}


}

package com.cicad.app.service;

import com.cicad.app.entities.Student;
import com.cicad.app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public Student get(Integer id) {
		// do other stuff
		return studentRepository.get(id);
	}

	public Student create(Student sourceStudent) {
		Student actualStudent = new Student();
		actualStudent.setFirstName(sourceStudent.getFirstName());
		actualStudent.setLastName(sourceStudent.getLastName());
		if (sourceStudent.getDateOfBirth() != null
				&& sourceStudent.getDateOfBirth().isBefore(LocalDate.now())) {
			actualStudent.setDateOfBirth(sourceStudent.getDateOfBirth());
		}
		return studentRepository.create(actualStudent);
	}

}

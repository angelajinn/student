package com.cicad.app.service;

import com.cicad.app.entities.Program;
import com.cicad.app.entities.Student;
import com.cicad.app.repository.ProgramRepository;
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
	@Autowired
	private ProgramRepository programRepository;

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
		if (sourceStudent.getGpa() >= 0 && sourceStudent.getGpa() <= 5) {
			actualStudent.setGpa(sourceStudent.getGpa());
		}
		actualStudent.setProgram(sourceStudent.getProgram());
		actualStudent.setCourse(sourceStudent.getCourses());
		return studentRepository.create(actualStudent);
	}

	public Student update(Student sourceStudent) {
		Student existingStudent = studentRepository.get(sourceStudent.getId());
		if (existingStudent != null) {
			existingStudent.setFirstName(sourceStudent.getFirstName());
			existingStudent.setLastName(sourceStudent.getLastName());
			existingStudent.setDateOfBirth(sourceStudent.getDateOfBirth());
			existingStudent.setGpa(sourceStudent.getGpa());

			Program program = programRepository.findById(sourceStudent.getProgram().getId());
			existingStudent.setProgram(program);

		}
		return studentRepository.update(existingStudent);
	}

	public void delete(Integer id) {
		studentRepository.delete(id);
	}


}

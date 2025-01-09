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
public class ProgramService {
    @Autowired
    private ProgramRepository programRepository;

    public Program get(Integer id) {
        // do other stuff
        return programRepository.get(id);
    }

    public List<Program> getAll() {
        return programRepository.findAllPrograms();
    }

    public Program create(Program sourceProgram) {
        Program actualProgram = new Program();
        actualProgram.setName(sourceProgram.getName());
        return programRepository.create(actualProgram);
    }

    public void delete(Integer id) {
        programRepository.delete(id);
    }

}



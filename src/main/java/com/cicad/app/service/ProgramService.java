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
        if (sameName(sourceProgram.getName())) {
            throw new IllegalArgumentException("Already have this program name");
        } else {
            actualProgram.setName(sourceProgram.getName());
        }
        return programRepository.create(actualProgram);
    }

    public Program update(Program sourceProgram) {
        Program existingProgram = programRepository.get(sourceProgram.getId());
        if (existingProgram != null) {
            existingProgram.setName(sourceProgram.getName());
        }

        return programRepository.update(existingProgram);
    }

    public void delete(Integer id) {
        programRepository.delete(id);
    }

    public Boolean sameName(String name) {
        for (Program program : programRepository.findAllPrograms()) {
            if (program.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}



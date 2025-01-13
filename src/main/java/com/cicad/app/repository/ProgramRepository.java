package com.cicad.app.repository;

import com.cicad.app.entities.Program;
import com.cicad.app.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional

public class ProgramRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Program get(Integer id) {
        return entityManager.find(Program.class, id);
    }

    public Program create(Program actualProgram) {
        entityManager.persist(actualProgram);
        return actualProgram;
    }

    public List<Program> findAllPrograms() {
        String jpql = "SELECT p FROM Program p"; // JPQL query to fetch all students
        return entityManager.createQuery(jpql, Program.class).getResultList(); // Executes the JPQL query and maps the result to Student entities
    }

    public Program findById(Integer id) {
        return entityManager.find(Program.class, id);
    }

    public Program update(Program actualProgram) { return entityManager.merge(actualProgram); }

    public void delete(Integer id) {
        Program program = entityManager.find(Program.class, id);
        if (program != null) {
            entityManager.remove(program);
        } else {
            throw new IllegalArgumentException("Program with id " + id + " not found");
        }
    }

}




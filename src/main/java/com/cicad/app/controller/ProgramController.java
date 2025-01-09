package com.cicad.app.controller;

import com.cicad.app.entities.Program;
import com.cicad.app.entities.Student;
import com.cicad.app.service.ProgramService;
import com.cicad.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/program")
public class ProgramController {
    @Autowired
    private ProgramService programService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Integer id) {
        return programService.get(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Program> getAll() {
        return programService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object create(@RequestBody Program sourceProgram) {
        return programService.create(sourceProgram);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        programService.delete(id);
    }
}

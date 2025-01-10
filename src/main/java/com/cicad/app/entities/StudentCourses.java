package com.cicad.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "STUDENT_COURSES")
public class StudentCourses implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    @JsonIgnoreProperties({"courses"}) // PREVENTS INFINITE RECURSION
    private Student student;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    @JsonIgnoreProperties({"students"})
    private Course course;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public Student getStudent() { return student; }

    public void setStudent(Student student) { this.student = student; }

}

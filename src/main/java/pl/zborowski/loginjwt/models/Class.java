package pl.zborowski.loginjwt.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table( name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String className;

    @OneToMany(mappedBy = "classes")
    private List<User> students = new ArrayList<>();

    @ManyToMany(mappedBy = "classes")
    private List<Subject> subjects = new ArrayList<>();

    public Class() {
    }

    public Class(@NotBlank @Size(max = 255) String className, List<User> students, List<Subject> subjects) {
        this.className = className;
        this.students = students;
        this.subjects = subjects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}

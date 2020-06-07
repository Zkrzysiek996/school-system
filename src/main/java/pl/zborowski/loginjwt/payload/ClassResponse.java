package pl.zborowski.loginjwt.payload;

import pl.zborowski.loginjwt.models.Subject;
import pl.zborowski.loginjwt.models.User;

import java.util.ArrayList;
import java.util.List;

public class ClassResponse {

    private Long id;
    private String className;
    private List<User> students = new ArrayList<>();
    private List<Subject> subject = new ArrayList<>();

    public ClassResponse(Long id, String className, List<User> students, List<Subject> subject) {
        this.id = id;
        this.className = className;
        this.students = students;
        this.subject = subject;
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

    public List<Subject> getSubject() {
        return subject;
    }

    public void setSubject(List<Subject> subject) {
        this.subject = subject;
    }
}

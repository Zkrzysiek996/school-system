package pl.zborowski.loginjwt.payload;

import pl.zborowski.loginjwt.models.Subject;
import pl.zborowski.loginjwt.models.User;

import java.util.ArrayList;
import java.util.List;

public class ClassRequest {

    private String className;
    private List<Long> students = new ArrayList<>();
    private List<Long> subjects = new ArrayList<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Long> getStudents() {
        return students;
    }

    public void setStudents(List<Long> students) {
        this.students = students;
    }

    public List<Long> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Long> subjects) {
        this.subjects = subjects;
    }
}

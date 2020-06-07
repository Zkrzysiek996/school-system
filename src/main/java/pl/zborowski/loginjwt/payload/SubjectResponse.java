package pl.zborowski.loginjwt.payload;

public class SubjectResponse {

    private Long id;
    private String subjectName;
    private Long teacherId;


    public SubjectResponse(Long id, String subjectName, Long teacherId) {
        this.id = id;
        this.subjectName = subjectName;
        this.teacherId = teacherId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

}

package pl.zborowski.loginjwt.payload;

public class GradeResponse {

    private Long id;
    private Long userId;
    private Long subjectId;
    private int grade;
    private String description;

    public GradeResponse(Long id, Long userId, Long subjectId, int grade, String description) {
        this.id = id;
        this.userId = userId;
        this.subjectId = subjectId;
        this.grade = grade;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

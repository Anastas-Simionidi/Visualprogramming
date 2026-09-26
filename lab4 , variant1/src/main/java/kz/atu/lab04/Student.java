package kz.atu.lab04;

public class Student {
    private String fullName;
    private String group;
    private String course;
    private String studyForm;
    private String note;

    public Student(String fullName, String group, String course, String studyForm, String note) {
        this.fullName = fullName;
        this.group = group;
        this.course = course;
        this.studyForm = studyForm;
        this.note = note;
    }

    public String getFullName() { return fullName; }
    public String getGroup() { return group; }
    public String getCourse() { return course; }
    public String getStudyForm() { return studyForm; }
    public String getNote() { return note; }
}
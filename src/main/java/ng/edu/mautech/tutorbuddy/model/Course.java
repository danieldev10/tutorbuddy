package ng.edu.mautech.tutorbuddy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long course_id;
    private String course_code;
    private String course_title;
    private String course_credit_unit;
    private String files_link;

    @ManyToMany
    @JoinTable(name = "course_tutor", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> tutors = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseDetail> courseDetail = new ArrayList<>();

    public Course() {
    }

    public Course(String course_code, String course_title, String course_credit_unit, String files_link,
            Set<User> tutors, List<CourseDetail> courseDetail) {
        this.course_code = course_code;
        this.course_title = course_title;
        this.course_credit_unit = course_credit_unit;
        this.files_link = files_link;
        this.tutors = tutors;
        this.courseDetail = courseDetail;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getCourse_credit_unit() {
        return course_credit_unit;
    }

    public void setCourse_credit_unit(String course_credit_unit) {
        this.course_credit_unit = course_credit_unit;
    }

    public String getFiles_link() {
        return files_link;
    }

    public void setFiles_link(String files_link) {
        this.files_link = files_link;
    }

    public Set<User> getTutors() {
        return tutors;
    }

    public void setTutors(Set<User> tutors) {
        this.tutors = tutors;
    }

    public List<CourseDetail> getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(List<CourseDetail> courseDetail) {
        this.courseDetail = courseDetail;
    }

    @Override
    public String toString() {
        return "Course [course_id=" + course_id + ", course_title=" + course_title + "]";
    }

}

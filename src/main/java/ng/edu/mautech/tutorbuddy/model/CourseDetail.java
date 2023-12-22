package ng.edu.mautech.tutorbuddy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CourseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String timeDuration;
    private String classVideoLink;
    private String physicalLocation;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CourseDetail() {
    }

    public CourseDetail(String timeDuration, String classVideoLink, String physicalLocation, String phoneNumber,
            Course course, User user) {
        this.timeDuration = timeDuration;
        this.classVideoLink = classVideoLink;
        this.physicalLocation = physicalLocation;
        this.phoneNumber = phoneNumber;
        this.course = course;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    public String getClassVideoLink() {
        return classVideoLink;
    }

    public void setClassVideoLink(String classVideoLink) {
        this.classVideoLink = classVideoLink;
    }

    public String getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(String physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CourseDetail [id=" + id + ", timeDuration=" + timeDuration + ", classVideoLink=" + classVideoLink
                + ", physicalLocation=" + physicalLocation + ", phoneNumber=" + phoneNumber + ", course="
                + course.getCourse_title()
                + ", user=" + user.getUsername() + "]";
    }

}

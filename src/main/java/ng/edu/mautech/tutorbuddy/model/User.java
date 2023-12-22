package ng.edu.mautech.tutorbuddy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long user_id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id"), })
    Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "tutors")
    private Set<Course> courses = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_course_details", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_detail_id"))
    private Set<CourseDetail> courseDetails = new HashSet<>();

    @ManyToMany(mappedBy = "students")
    private Set<Question> questions = new HashSet<>();

    public User() {
    }

    public User(String firstname, String lastname, String email, String username, String password, Set<Role> roles,
            Set<Course> courses, Set<CourseDetail> courseDetails, Set<Question> questions) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.courses = courses;
        this.courseDetails = courseDetails;
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "User [user_id=" + user_id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
                + ", username=" + username + ", password=" + password + "]";
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<CourseDetail> getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(Set<CourseDetail> courseDetails) {
        this.courseDetails = courseDetails;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

}

package ng.edu.mautech.tutorbuddy.service;

import java.util.List;
import java.util.Optional;

import ng.edu.mautech.tutorbuddy.model.Course;
import ng.edu.mautech.tutorbuddy.model.User;

public interface UserService {
    public void save(User user, String selectedRole);

    public User findByUsername(String un);

    public User getCurrentUser();

    List<User> get();

    void deleteById(Long id);

    Optional<User> findById(Long id);

    List<User> getStudents();

    List<User> getTutors();

    void addTutorToCourse(Long tutorId, Long courseId);

    List<Course> getCoursesForTutor(Long tutorId);

    public void updateUserCourses(User currentUser);

    public void updateUserQuestions(User currentUser);
}

package ng.edu.mautech.tutorbuddy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ng.edu.mautech.tutorbuddy.model.Course;
import ng.edu.mautech.tutorbuddy.model.Role;
import ng.edu.mautech.tutorbuddy.model.User;
import ng.edu.mautech.tutorbuddy.repository.CourseRepo;
import ng.edu.mautech.tutorbuddy.repository.RoleRepository;
import ng.edu.mautech.tutorbuddy.repository.UserRepository;
import ng.edu.mautech.tutorbuddy.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepo courseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(User user, String selectedRole) {
        user.setPassword(encoder.encode(user.getPassword()));

        Role existingRole = roleRepository.findByName(selectedRole);

        if (existingRole != null) {
            user.getRoles().add(existingRole);
        } else {
            Role newRole = new Role(selectedRole, Collections.singleton(user));
            user.getRoles().add(newRole);
        }

        userRepository.save(user);
    }

    @Override
    public User findByUsername(String un) {
        return userRepository.findByUsername(un);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> get() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getStudents() {
        return userRepository.findByRoles_Name("student");
    }

    @Override
    public List<User> getTutors() {
        return userRepository.findByRoles_Name("tutor");
    }

    @Override
    public void addTutorToCourse(Long tutorId, Long courseId) {
        User tutor = userRepository.findById(tutorId).orElse(null);
        Course course = courseRepository.get(courseId);

        if (tutor != null && course != null) {
            course.getTutors().add(tutor);
            courseRepository.save(course);
        }
    }

    @Override
    public List<Course> getCoursesForTutor(Long tutorId) {
        User tutor = userRepository.findById(tutorId).orElse(null);
        return (tutor != null) ? new ArrayList<>(tutor.getCourses()) : Collections.emptyList();
    }

    @Override
    public void updateUserCourses(User currentUser) {
        userRepository.save(currentUser);
    }

    @Override
    public void updateUserQuestions(User currentUser) {
        userRepository.save(currentUser);
    }

}

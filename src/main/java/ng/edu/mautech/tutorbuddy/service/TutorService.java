package ng.edu.mautech.tutorbuddy.service;

import ng.edu.mautech.tutorbuddy.model.User;

import java.util.List;

public interface TutorService {
    List<User> getTutors();

    List<User> getTutorsWithAssignedCourses();
}

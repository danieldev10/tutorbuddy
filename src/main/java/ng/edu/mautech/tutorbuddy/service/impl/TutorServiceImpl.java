package ng.edu.mautech.tutorbuddy.service.impl;

import ng.edu.mautech.tutorbuddy.model.User;
import ng.edu.mautech.tutorbuddy.repository.TutorRepository;
import ng.edu.mautech.tutorbuddy.repository.UserRepository;
import ng.edu.mautech.tutorbuddy.service.TutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorServiceImpl implements TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private UserRepository userRepository;

    public TutorServiceImpl(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Override
    public List<User> getTutors() {
        return tutorRepository.findByRoles_Name("tutor");
    }

    @Override
    public List<User> getTutorsWithAssignedCourses() {
        return userRepository.findTutorsWithAssignedCourses("tutor");
    }

}

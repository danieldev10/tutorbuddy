package ng.edu.mautech.tutorbuddy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ng.edu.mautech.tutorbuddy.model.Course;
import ng.edu.mautech.tutorbuddy.repository.CourseRepo;
import ng.edu.mautech.tutorbuddy.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepo courseRepo;

    @Transactional
    @Override
    public List<Course> get() {
        return courseRepo.get();
    }

    @Transactional
    @Override
    public Course get(Long course_id) {
        return courseRepo.get(course_id);
    }

    @Transactional
    @Override
    public Course get(String course_title) {
        return courseRepo.get(course_title);
    }

    @Transactional
    @Override
    public void save(Course course) {
        courseRepo.save(course);
    }

    @Transactional
    @Override
    public void delete(Long course_id) {
        courseRepo.delete(course_id);
    }

}

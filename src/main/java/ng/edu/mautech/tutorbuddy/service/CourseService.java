package ng.edu.mautech.tutorbuddy.service;

import java.util.List;

import ng.edu.mautech.tutorbuddy.model.Course;

public interface CourseService {
    List<Course> get();

    public Course get(Long course_id);

    public Course get(String course_title);

    void save(Course course);

    void delete(Long course_id);
}

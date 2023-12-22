package ng.edu.mautech.tutorbuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.mautech.tutorbuddy.model.Course;
import ng.edu.mautech.tutorbuddy.model.CourseDetail;
import ng.edu.mautech.tutorbuddy.model.User;
import ng.edu.mautech.tutorbuddy.repository.CourseDetailRepo;
import ng.edu.mautech.tutorbuddy.repository.UserRepository;

import java.util.List;

@Service
public class CourseDetailService {
    @Autowired
    private final CourseDetailRepo courseDetailRepository;

    @Autowired
    private UserRepository userRepository;

    public CourseDetailService(CourseDetailRepo courseDetailRepository) {
        this.courseDetailRepository = courseDetailRepository;
    }

    public List<CourseDetail> getAllCourseDetails() {
        return courseDetailRepository.findAll();
    }

    public CourseDetail getCourseDetailById(Long id) {
        return courseDetailRepository.findById(id).orElse(null);
    }

    public CourseDetail createCourseDetail(CourseDetail courseDetail) {
        return courseDetailRepository.save(courseDetail);
    }

    public CourseDetail updateCourseDetail(Long id, CourseDetail updatedCourseDetail) {
        if (courseDetailRepository.existsById(id)) {
            updatedCourseDetail.setId(id);
            return courseDetailRepository.save(updatedCourseDetail);
        }
        return null;
    }

    public void deleteCourseDetail(Long id) {
        courseDetailRepository.deleteById(id);
    }

    public CourseDetail findByUserAndCourse(User user, Course course) {
        return courseDetailRepository.findByUserAndCourse(user, course);
    }

    public List<CourseDetail> getCourseDetailsByTutor(User tutor) {
        return courseDetailRepository.findByUser(tutor);
    }

    public void removeCourseDetailFromStudent(Long courseDetailId, User student) {
        CourseDetail courseDetail = courseDetailRepository.findById(courseDetailId).orElse(null);

        if (courseDetail != null) {
            student.getCourseDetails().remove(courseDetail);
            userRepository.save(student);
        }
    }
}

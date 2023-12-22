package ng.edu.mautech.tutorbuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ng.edu.mautech.tutorbuddy.model.Course;
import ng.edu.mautech.tutorbuddy.model.CourseDetail;
import ng.edu.mautech.tutorbuddy.model.User;

public interface CourseDetailRepo extends JpaRepository<CourseDetail, Long> {
    CourseDetail findByUserAndCourse(User user, Course course);

    List<CourseDetail> findByUser(User user);

    @Query("SELECT u FROM User u JOIN u.courseDetails c WHERE c.id = :courseDetailId")
    List<User> findStudentsByCourseDetailId(@Param("courseDetailId") Long courseDetailId);

}

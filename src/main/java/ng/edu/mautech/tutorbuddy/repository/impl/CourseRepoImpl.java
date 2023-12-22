package ng.edu.mautech.tutorbuddy.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ng.edu.mautech.tutorbuddy.model.Course;
import ng.edu.mautech.tutorbuddy.repository.CourseRepo;

@Repository
public class CourseRepoImpl implements CourseRepo {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Course> get() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Course> query = currentSession.createQuery("from Course", Course.class);
        List<Course> list = query.getResultList();
        return list;
    }

    @Override
    public Course get(Long course_id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Course courseObj = currentSession.get(Course.class, course_id);
        return courseObj;
    }

    @Override
    public Course get(String course_title) {
        Session currentSession = entityManager.unwrap(Session.class);
        Course courseObj = currentSession.get(Course.class, course_title);
        return courseObj;
    }

    @Override
    public void save(Course course) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.merge(course);
    }

    @Override
    public void delete(Long course_id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Course courseObj = currentSession.get(Course.class, course_id);
        currentSession.remove(courseObj);
    }

}

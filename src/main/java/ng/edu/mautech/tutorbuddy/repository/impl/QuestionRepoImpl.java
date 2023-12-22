package ng.edu.mautech.tutorbuddy.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ng.edu.mautech.tutorbuddy.model.Question;
import ng.edu.mautech.tutorbuddy.repository.QuestionRepo;

@Repository
public class QuestionRepoImpl implements QuestionRepo {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Question> get() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Question> query = currentSession.createQuery("from Question", Question.class);
        List<Question> list = query.getResultList();
        return list;
    }

    @Override
    public Question get(Long question_id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Question questionObj = currentSession.get(Question.class, question_id);
        return questionObj;
    }

    @Override
    public Question get(String question_title) {
        Session currentSession = entityManager.unwrap(Session.class);
        Question questionObj = currentSession.get(Question.class, question_title);
        return questionObj;
    }

    @Override
    public void save(Question question) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.merge(question);
    }

    @Override
    public void delete(Question question) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.delete(question);
    }

    @Override
    public List<Question> getWithUsers() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Question> query = currentSession.createQuery("SELECT q FROM Question q JOIN FETCH q.students",
                Question.class);
        List<Question> list = query.getResultList();
        return list;
    }

}

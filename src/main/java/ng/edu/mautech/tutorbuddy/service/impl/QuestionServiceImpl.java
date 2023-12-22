package ng.edu.mautech.tutorbuddy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ng.edu.mautech.tutorbuddy.model.Question;
import ng.edu.mautech.tutorbuddy.repository.QuestionRepo;
import ng.edu.mautech.tutorbuddy.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepo questionRepo;

    @Transactional
    @Override
    public List<Question> get() {
        return questionRepo.get();
    }

    @Transactional
    @Override
    public Question get(Long question_id) {
        return questionRepo.get(question_id);
    }

    @Transactional
    @Override
    public Question get(String question_title) {
        return questionRepo.get(question_title);
    }

    @Transactional
    @Override
    public void save(Question question) {
        questionRepo.save(question);
    }

    @Transactional
    @Override
    public void delete(Question question) {
        questionRepo.delete(question);
    }

    @Transactional
    @Override
    public List<Question> getWithUsers() {
        return questionRepo.getWithUsers();
    }
}

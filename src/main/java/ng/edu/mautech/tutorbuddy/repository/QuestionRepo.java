package ng.edu.mautech.tutorbuddy.repository;

import java.util.List;

import ng.edu.mautech.tutorbuddy.model.Question;

public interface QuestionRepo {
    List<Question> get();

    public Question get(Long question_id);

    public Question get(String question_title);

    void save(Question question);

    void delete(Question question);

    List<Question> getWithUsers();
}
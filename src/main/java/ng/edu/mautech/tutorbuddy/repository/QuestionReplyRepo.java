package ng.edu.mautech.tutorbuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ng.edu.mautech.tutorbuddy.model.Question;
import ng.edu.mautech.tutorbuddy.model.QuestionReply;
import ng.edu.mautech.tutorbuddy.model.User;

public interface QuestionReplyRepo extends JpaRepository<QuestionReply, Long> {
    QuestionReply findByUserAndQuestion(User user, Question question);
}

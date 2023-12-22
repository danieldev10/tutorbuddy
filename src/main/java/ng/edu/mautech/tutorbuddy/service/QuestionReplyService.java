package ng.edu.mautech.tutorbuddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ng.edu.mautech.tutorbuddy.model.Question;
import ng.edu.mautech.tutorbuddy.model.QuestionReply;
import ng.edu.mautech.tutorbuddy.model.User;
import ng.edu.mautech.tutorbuddy.repository.QuestionReplyRepo;

@Service
public class QuestionReplyService {
    @Autowired
    private final QuestionReplyRepo questionReplyRepository;

    public QuestionReplyService(QuestionReplyRepo questionReplyRepository) {
        this.questionReplyRepository = questionReplyRepository;
    }

    public List<QuestionReply> getAllQuestionReplies() {
        return questionReplyRepository.findAll();
    }

    public QuestionReply getQuestionReplyById(Long id) {
        return questionReplyRepository.findById(id).orElse(null);
    }

    @Transactional
    public QuestionReply createQuestionReply(QuestionReply questionReply) {
        return questionReplyRepository.save(questionReply);
    }

    public QuestionReply updateQuestionReply(Long id, QuestionReply updatedQuestionReply) {
        if (questionReplyRepository.existsById(id)) {
            updatedQuestionReply.setReply_id(id);
            return questionReplyRepository.save(updatedQuestionReply);
        }
        return null;
    }

    public void deleteQuestionReply(Long id) {
        questionReplyRepository.deleteById(id);
    }

    public QuestionReply findByUserAndQuestion(User user, Question question) {
        return questionReplyRepository.findByUserAndQuestion(user, question);
    }
}

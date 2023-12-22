package ng.edu.mautech.tutorbuddy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_id;
    private String question_title;
    private String question_message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "question_user", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> students = new HashSet<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionReply> questionReplies = new ArrayList<>();

    public Question() {
    }

    public Question(String question_title, String question_message, User owner, Set<User> students,
            List<QuestionReply> questionReplies) {
        this.question_title = question_title;
        this.question_message = question_message;
        this.owner = owner;
        this.students = students;
        this.questionReplies = questionReplies;
    }

    @Override
    public String toString() {
        return "Question [question_id=" + question_id + ", question_title=" + question_title + ", question_message="
                + question_message + "]";
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_message() {
        return question_message;
    }

    public void setQuestion_message(String question_message) {
        this.question_message = question_message;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public List<QuestionReply> getQuestionReplies() {
        return questionReplies;
    }

    public void setQuestionReplies(List<QuestionReply> questionReplies) {
        this.questionReplies = questionReplies;
    }

}

package ng.edu.mautech.tutorbuddy.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ng.edu.mautech.tutorbuddy.model.Course;
import ng.edu.mautech.tutorbuddy.model.CourseDetail;
import ng.edu.mautech.tutorbuddy.model.Question;
import ng.edu.mautech.tutorbuddy.model.QuestionReply;
import ng.edu.mautech.tutorbuddy.model.User;
import ng.edu.mautech.tutorbuddy.service.CourseDetailService;
import ng.edu.mautech.tutorbuddy.service.CourseService;
import ng.edu.mautech.tutorbuddy.service.QuestionReplyService;
import ng.edu.mautech.tutorbuddy.service.QuestionService;
import ng.edu.mautech.tutorbuddy.service.UserService;

@Controller
public class StudentController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseDetailService courseDetailService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionReplyService questionReplyService;

    // Student Dashboard - Home Page for student
    @GetMapping("/student/index")
    public String studentLogin(Model model) {
        // This method handles the GET request to "/student/index."
        // It is the student's dashboard and provides information about the student's
        // courses.

        // Retrieve the currently logged-in student.
        User student = userService.getCurrentUser();

        // Retrieve the course details associated with the student.
        Set<CourseDetail> courseDetails = student.getCourseDetails();

        // Add the student and course details to the model for rendering in the view.
        model.addAttribute("student", student);
        model.addAttribute("courseDetails", courseDetails);

        // Return the view name "student-course-assigned" to display the student's
        // course details.
        return "student-course-assigned";
    }

    // View Course Detail - Display Details of a Course
    @GetMapping("/student/course/{courseId}")
    public String viewCourseDetail(@PathVariable Long courseId, Model model) {
        // This method handles the GET request to view the details of a specific course.

        // Retrieve the course based on the courseId.
        Course course = courseService.get(courseId);
        List<CourseDetail> courseDetails = course.getCourseDetail();

        // Add the course and its details to the model for rendering in the view.
        model.addAttribute("course", course);
        model.addAttribute("courseDetails", courseDetails);

        // Return the view name "student-course-detail" to display the course details.
        return "student-course-detail";
    }

    // Assign Course Detail to Student
    @GetMapping("/student/assign-course-detail/{courseId}")
    public String assignCourseDetail(@PathVariable Long courseId) {
        // This method handles the GET request to assign a course detail to a student.

        // Retrieve the currently logged-in user.
        User currentUser = userService.getCurrentUser();

        // Retrieve the course detail based on the courseId.
        CourseDetail courseDetail = courseDetailService.getCourseDetailById(courseId);

        // Ensure the user is a student (you can add role checks if needed).
        if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("student"))) {
            // If the user is a student, add the course detail to their list of assigned
            // course details.
            currentUser.getCourseDetails().add(courseDetail);

            // Update the user's assigned courses (not the password).
            userService.updateUserCourses(currentUser);
        }

        // Redirect to the student's dashboard.
        return "redirect:/student/index";
    }

    // List Available Courses for Student
    @GetMapping("/student/courses")
    public String studentCourseDetails(Model model) {
        // This method handles the GET request to list available courses for a student.

        // Retrieve a list of all courses.
        List<Course> courseList = courseService.get();

        // Retrieve the currently logged-in student.
        User student = userService.getCurrentUser();

        // Retrieve the set of course details already assigned to the student.
        Set<CourseDetail> assignedCourseDetails = student.getCourseDetails();

        // Filter out courses for which the student already has assigned course details.
        courseList.removeIf(course -> course.getCourseDetail().stream()
                .anyMatch(courseDetail -> assignedCourseDetails.contains(courseDetail)));

        // Add the available courses to the model for rendering in the view.
        model.addAttribute("courses", courseList);

        // Return the view name "student-courses" to display the available courses.
        return "student-courses";
    }

    // Remove Course Detail from Student
    @GetMapping("/student/course/remove/{courseId}")
    public String removeCourseDetail(
            @PathVariable Long courseId,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        // This method handles the GET request to remove a course detail from a
        // student's assigned courses.

        // Retrieve the student based on the currently logged-in user.
        User student = userService.findByUsername(userDetails.getUsername());

        // Call the service method to remove the course detail from the student.
        courseDetailService.removeCourseDetailFromStudent(courseId, student);

        // Redirect to the student's dashboard.
        return "redirect:/student/index";
    }

    // Student Forum - View Forum Questions and Answers
    @GetMapping("/student/forum")
    public String studentForum(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // This method handles the GET request to view a forum for student questions and
        // answers.

        // Retrieve the currently logged-in user.
        User currentUser = userService.findByUsername(userDetails.getUsername());

        // Retrieve a list of questions with associated users.
        List<Question> questions = questionService.getWithUsers();

        // Separate questions asked by the current user and other users.
        List<Question> currentUserQuestions = new ArrayList<>();
        List<Question> otherUserQuestions = new ArrayList<>();

        for (Question question : questions) {
            if (question.getOwner() != null && question.getOwner().equals(currentUser)) {
                currentUserQuestions.add(question);
            } else {
                otherUserQuestions.add(question);
            }
        }

        // Add the questions to the model for rendering in the view.
        model.addAttribute("currentUserQuestions", currentUserQuestions);
        model.addAttribute("otherUserQuestions", otherUserQuestions);

        // Return the view name "student-forum" to display the forum questions and
        // answers.
        return "student-forum";
    }

    // Ask a Question in the Forum - Create a New Question
    @GetMapping("/student/question/new")
    public String createQuestion(Model model) {
        // This method handles the GET request to create a new question in the forum.

        // Create a new Question object.
        Question question = new Question();

        // Add the question to the model for rendering in the view.
        model.addAttribute("questions", question);

        // Return the view name "student-ask-question" to create a new question.
        return "student-ask-question";
    }

    @PostMapping("/student/question/new")
    public String saveQuestion(@ModelAttribute("questions") Question question, Principal principal) {
        // This method handles the POST request to save a new question in the forum.

        // Get the currently logged in user.
        User currentUser = userService.findByUsername(principal.getName());

        // Create a new question and associate it with the current user.
        Set<User> students = new HashSet<>();
        students.add(currentUser);
        question.setStudents(students);
        question.setOwner(currentUser);

        // Save the new question.
        questionService.save(question);

        // Redirect to the student forum.
        return "redirect:/student/forum";
    }

    // Reply to a Question in the Forum
    @GetMapping("/student/forum/question/reply/{questionId}")
    public String replyQuestion(@PathVariable Long questionId, Model model) {
        // This method handles the GET request to reply to a question in the forum.

        // Retrieve the question based on the questionId.
        Question question = questionService.get(questionId);
        List<QuestionReply> replies = question.getQuestionReplies();

        // Add the question and its replies to the model for rendering in the view.
        model.addAttribute("question", question);
        model.addAttribute("replies", replies);

        // Return the view name "student-forum-reply" to reply to the question.
        return "student-forum-reply";
    }

    @PostMapping("/student/forum/question/reply-sent/{questionId}")
    public String sendReplyToQuestion(
            @PathVariable Long questionId,
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute QuestionReply questionReplyForm) {
        // This method handles the POST request to send a reply to a question in the
        // forum.

        // Retrieve the currently logged-in student.
        User student = userService.findByUsername(userDetails.getUsername());

        // Retrieve the question based on the questionId.
        Question question = questionService.get(questionId);

        // Create a new QuestionReply and set its properties.
        QuestionReply reply = new QuestionReply();
        reply.setReply_message(questionReplyForm.getReply_message());
        reply.setQuestion(question);
        reply.setUser(student);

        // Create the question reply.
        questionReplyService.createQuestionReply(reply);

        // Redirect to the page for viewing the question and its replies.
        return "redirect:/student/forum/question/reply/{questionId}";
    }

    @RequestMapping(value = "/student/forum/question/delete/{questionId}", method = { RequestMethod.DELETE,
            RequestMethod.GET })
    public String deleteQuestion(@PathVariable Long questionId) {
        // This method handles the GET and DELETE requests to delete a question from the
        // forum.

        // Retrieve the question entity based on the questionId.
        Question question = questionService.get(questionId);

        if (question != null) {
            // Delete the question.
            questionService.delete(question);
        }

        // Redirect to the student forum.
        return "redirect:/student/forum";
    }
}

package ng.edu.mautech.tutorbuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ng.edu.mautech.tutorbuddy.model.Course;
import ng.edu.mautech.tutorbuddy.model.User;
import ng.edu.mautech.tutorbuddy.service.CourseDetailService;
import ng.edu.mautech.tutorbuddy.service.CourseService;
import ng.edu.mautech.tutorbuddy.service.TutorService;
import ng.edu.mautech.tutorbuddy.service.UserService;

@Controller
public class AdminController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private CourseDetailService courseDetailService;

    // Admin Dashboard - Home Page for admin
    @GetMapping("/admin/dashboard")
    public String adminLogin(Model model) {
        // This method handles the GET request to "/admin/dashboard."
        // It is the admin's dashboard and provides statistics and information about the
        // system.

        // Retrieve various statistics and information about the application.
        long totalStudents = userService.getStudents().size();
        long totalTutors = userService.getTutors().size();
        long totalCourses = courseService.get().size();
        long totalCourseDetails = courseDetailService.getAllCourseDetails().size();
        double averageTutorsPerCourse = totalTutors / (double) totalCourses;
        double averageStudentsPerCourseDetail = totalStudents / (double) totalCourseDetails;

        // Add these statistics to the model for rendering in the view.
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalTutors", totalTutors);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalCourseDetails", totalCourseDetails);
        model.addAttribute("averageTutorsPerCourse", averageTutorsPerCourse);
        model.addAttribute("averageStudentsPerCourseDetail", averageStudentsPerCourseDetail);

        // Return the view name "admin-dashboard" to display the admin dashboard.
        return "admin-dashboard";
    }

    // Admin Course - Display List of Courses Created by Admin
    @GetMapping("/admin/course")
    public String adminCourse(Model model) {
        // This method handles the GET request to "/admin/course."
        // It retrieves a list of courses created by the admin and displays them.

        // Retrieve the list of courses.
        List<Course> courseList = courseService.get();
        User user = userService.getCurrentUser();
        model.addAttribute("firstName", user.getFirstname());
        model.addAttribute("lastName", user.getLastname());
        model.addAttribute("courses", courseList);

        // Return the view name "admin-course" to display the list of courses.
        return "admin-course";
    }

    // Update information about a course - GET request
    @GetMapping("/admin/course/update/{courseId}")
    public String editCourse(@PathVariable Long courseId, Model model) {
        // This method handles the GET request to update course information.
        // It retrieves the course to be updated and prepares the form for editing.

        // Retrieve the course based on the courseId.
        model.addAttribute("course", courseService.get(courseId));

        // Return the view name "admin-update-course" for editing the course.
        return "admin-update-course";
    }

    // Update information about a course - POST request
    @PostMapping("/admin/course/update/{courseId}")
    public String updateCourse(@PathVariable Long courseId, @ModelAttribute("course") Course course, Model model) {
        // This method handles the POST request to update course information.
        // It updates the course with the modified data and redirects back to the course
        // list.

        // Retrieve the existing course based on the courseId.
        Course existingCourse = courseService.get(courseId);

        // Update the course properties with the new data.
        existingCourse.setCourse_code(course.getCourse_code());
        existingCourse.setCourse_title(course.getCourse_title());
        existingCourse.setCourse_credit_unit(course.getCourse_credit_unit());
        existingCourse.setFiles_link(course.getFiles_link());

        // Save the updated course.
        courseService.save(existingCourse);

        // Redirect to the course list.
        return "redirect:/admin/course";
    }

    @RequestMapping(value = "/admin/course/delete/{courseId}", method = { RequestMethod.DELETE, RequestMethod.GET })
    public String deleteCourse(@PathVariable Long courseId) {
        // This method handles the request to delete a course.
        // It deletes the specified course and redirects back to the course list.

        courseService.delete(courseId);
        return "redirect:/admin/course";
    }

    @GetMapping("/admin/tutors")
    public String adminTutors(Model model) {
        // This method handles the GET request to "/admin/tutors."
        // It retrieves a list of tutors and tutors with assigned courses and displays
        // them.

        List<User> tutors = tutorService.getTutors();
        model.addAttribute("tutors", tutors);

        List<User> tutorsAndCourses = tutorService.getTutorsWithAssignedCourses();
        model.addAttribute("tutors", tutorsAndCourses);

        // Return the view name "admin-tutors" to display the list of tutors.
        return "admin-tutors";
    }

    @GetMapping("/admin/students")
    public String adminStudents(Model model) {
        // This method handles the GET request to "/admin/students."
        // It retrieves a list of students and displays them.

        List<User> students = userService.getStudents();
        model.addAttribute("students", students);

        // Return the view name "admin-students" to display the list of students.
        return "admin-students";
    }

    @GetMapping("/admin/course/new")
    public String createCourse(Model model) {
        // This method handles the GET request to create a new course.
        // It prepares the form for creating a new course.

        Course course = new Course();
        User user = userService.getCurrentUser();
        model.addAttribute("firstName", user.getFirstname());
        model.addAttribute("lastName", user.getLastname());
        model.addAttribute("courses", course);

        // Return the view name "admin-create-course" for creating a new course.
        return "admin-create-course";
    }

    @PostMapping("/admin/course/new")
    public String saveCourse(@ModelAttribute("courses") Course course) {
        // This method handles the POST request to save a new course.
        // It saves the course and redirects back to the course list.

        courseService.save(course);

        // Redirect to the course list.
        return "redirect:/admin/course";
    }
}

package ng.edu.mautech.tutorbuddy.controller;

import java.util.List;

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
import ng.edu.mautech.tutorbuddy.model.User;
import ng.edu.mautech.tutorbuddy.repository.CourseDetailRepo;
import ng.edu.mautech.tutorbuddy.service.CourseDetailService;
import ng.edu.mautech.tutorbuddy.service.CourseService;
import ng.edu.mautech.tutorbuddy.service.UserService;

@Controller
public class TutorController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseDetailService courseDetailService;

    @Autowired
    private CourseDetailRepo courseDetailRepo;

    // Tutor Dashboard - Home Page for tutors
    @GetMapping("/tutor/index")
    public String tutorLogin(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // This method handles the GET request to the tutor's dashboard.

        // Get the currently logged-in tutor.
        User tutor = userService.findByUsername(userDetails.getUsername());

        // Retrieve the course details associated with the tutor.
        List<CourseDetail> courseDetails = courseDetailService.getCourseDetailsByTutor(tutor);

        // Add course details to the model for rendering in the view.
        model.addAttribute("courseDetails", courseDetails);

        // Return the view name "tutor-my-courses" to display the tutor's courses.
        return "tutor-my-courses";
    }

    // List of Available Courses
    @GetMapping("/tutor/courses")
    public String listOfCourses(Model model) {
        // This method handles the GET request to list available courses for tutors.

        // Retrieve a list of all courses.
        List<Course> course = courseService.get();

        // Add the courses to the model for rendering in the view.
        model.addAttribute("courses", course);

        // Return the view name "tutor-course-list" to display the list of courses.
        return "tutor-course-list";
    }

    // Get Course Details
    @GetMapping("/tutor/course/{courseId}")
    public String getCourseDetails(@PathVariable Long courseId, Model model) {
        // This method handles the GET request to view details of a specific course.

        // Retrieve the course based on the courseId.
        Course course = courseService.get(courseId);

        // Add the course to the model for rendering in the view.
        model.addAttribute("course", course);

        // Return the view name "tutor-course-details" to display course details.
        return "tutor-course-details";
    }

    // Add Course to Tutor
    @PostMapping("/tutor/add-course/{courseId}")
    public String addCourseToTutor(
            @PathVariable Long courseId,
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute CourseDetail courseDetailForm) {
        // This method handles the POST request to add a course to a tutor's assigned
        // courses.

        // Retrieve the currently logged-in tutor.
        User tutor = userService.findByUsername(userDetails.getUsername());

        // Retrieve the course based on the courseId.
        Course course = courseService.get(courseId);

        // Check if the course detail already exists for the tutor.
        CourseDetail existingCourseDetail = courseDetailService.findByUserAndCourse(tutor, course);

        if (existingCourseDetail == null) {
            // If the course detail does not exist, create a new one and associate it with
            // the tutor and course.
            CourseDetail courseDetail = new CourseDetail();
            courseDetail.setTimeDuration(courseDetailForm.getTimeDuration());
            courseDetail.setClassVideoLink(courseDetailForm.getClassVideoLink());
            courseDetail.setPhysicalLocation(courseDetailForm.getPhysicalLocation());
            courseDetail.setPhoneNumber(courseDetailForm.getPhoneNumber());

            courseDetail.setUser(tutor);
            courseDetail.setCourse(course);

            if (!course.getTutors().contains(tutor)) {
                course.getTutors().add(tutor);
                courseService.save(course);
            }

            // Create the course detail.
            courseDetailService.createCourseDetail(courseDetail);
        }

        // Redirect to the tutor's course details page.
        return "redirect:/tutor/course-details";
    }

    // Tutor Course Details - List of Courses Assigned to Tutor
    @GetMapping("/tutor/course-details")
    public String tutorCourseDetails(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // This method handles the GET request to view the list of courses assigned to a
        // tutor.

        // Get the currently logged-in tutor.
        User tutor = userService.findByUsername(userDetails.getUsername());

        // Retrieve the course details associated with the tutor.
        List<CourseDetail> courseDetails = courseDetailService.getCourseDetailsByTutor(tutor);

        // Add course details to the model for rendering in the view.
        model.addAttribute("courseDetails", courseDetails);

        // Return the view name "tutor-my-courses" to display the tutor's courses.
        return "tutor-my-courses";
    }

    // Edit Course Detail
    @GetMapping("/tutor/course/update/{courseId}")
    public String editCourseDetail(@PathVariable Long courseId, Model model) {
        // This method handles the GET request to edit a course detail.

        // Add the course detail to the model for rendering in the view.
        model.addAttribute("courseDetail", courseDetailService.getCourseDetailById(courseId));

        // Return the view name "tutor-update-course-detail" to edit the course detail.
        return "tutor-update-course-detail";
    }

    @PostMapping("/tutor/course/update/{courseId}")
    public String updateCourseDetail(@PathVariable Long courseId,
            @ModelAttribute("courseDetail") CourseDetail courseDetail, Model model) {
        // This method handles the POST request to update a course detail.

        // Retrieve the existing course detail based on the courseId.
        CourseDetail existingCourseDetail = courseDetailService.getCourseDetailById(courseId);

        // Update the properties of the existing course detail with new values.
        existingCourseDetail.setTimeDuration(courseDetail.getTimeDuration());
        existingCourseDetail.setClassVideoLink(courseDetail.getClassVideoLink());
        existingCourseDetail.setPhysicalLocation(courseDetail.getPhysicalLocation());
        existingCourseDetail.setPhoneNumber(courseDetail.getPhoneNumber());

        // Update the course detail.
        courseDetailService.updateCourseDetail(courseId, existingCourseDetail);

        // Redirect to the tutor's course details page.
        return "redirect:/tutor/course-details";
    }

    @RequestMapping(value = "/tutor/course/delete/{courseId}", method = { RequestMethod.DELETE, RequestMethod.GET })
    public String deleteCourseDetail(@PathVariable Long courseId) {
        // This method handles the GET and DELETE requests to delete a course detail.

        // Delete the course detail based on the courseId.
        courseDetailService.deleteCourseDetail(courseId);

        // Redirect to the tutor's course details page.
        return "redirect:/tutor/course-details";
    }

    @GetMapping("/tutor/my-course/detail/{courseDetailId}")
    public String viewCourseDetailForTutor(@PathVariable Long courseDetailId, Model model) {
        // This method handles the GET request to view details of a specific course
        // detail assigned to a tutor.

        // Retrieve the course detail based on the courseDetailId.
        CourseDetail courseDetail = courseDetailService.getCourseDetailById(courseDetailId);

        // Add the course detail to the model for rendering in the view.
        model.addAttribute("courseDetail", courseDetail);

        // Retrieve the list of students associated with the CourseDetail.
        List<User> students = courseDetailRepo.findStudentsByCourseDetailId(courseDetailId);

        // Add the list of students to the model.
        model.addAttribute("students", students);

        // Return the view name "tutor-my-course-detail" to display course detail and
        // associated students.
        return "tutor-my-course-detail";
    }
}

// This is a Spring Controller class that handles HTTP requests and serves web
// pages.
package ng.edu.mautech.tutorbuddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ng.edu.mautech.tutorbuddy.model.User;
import ng.edu.mautech.tutorbuddy.service.UserService;

// This class is marked as a Spring MVC controller by the @Controller annotation.
// It handles HTTP requests and serves as a controller for this application.

// Other methods and annotations in this class define how it handles specific requests.
@Controller
public class ApplicationController {

    // The @Autowired annotation is used to inject the UserService bean into this
    // controller.
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        // This method handles HTTP GET requests to "/login".
        // It returns a string "login," which is the html page login.html

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        // This method handles HTTP GET requests to "/register".
        // It returns a string "register," which is the html page register.html

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @RequestParam("userRole") String userRole) {
        // This method handles HTTP POST requests to "/register".

        // It takes user input as a @ModelAttribute and the user's role as a
        // @RequestParam.

        // It then calls the userService to save the user data and redirects to the
        // login page.

        userService.save(user, userRole);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        // This method handles HTTP GET requests to "/logout".

        // It returns a string "login," which is the html page login.html

        return "login";
    }
}

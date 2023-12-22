package ng.edu.mautech.tutorbuddy;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // This class is a custom authentication success handler, extending the
        // SavedRequestAwareAuthenticationSuccessHandler provided by Spring Security.
        // It allows custom logic to be executed when a user successfully logs in.

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Retrieve the user's authorities (roles) from the authentication object.

        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("admin"))) {
            // If the user has the "admin" role, redirect them to the admin dashboard.
            response.sendRedirect("/admin/dashboard");
        } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("tutor"))) {
            // If the user has the "tutor" role, redirect them to the tutor dashboard.
            response.sendRedirect("/tutor/index");
        } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("student"))) {
            // If the user has the "student" role, redirect them to the student dashboard.
            response.sendRedirect("/student/index");
        } else {
            // If the user doesn't have any of the specified roles, use the default behavior
            // provided by the parent class (SavedRequestAwareAuthenticationSuccessHandler).
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}

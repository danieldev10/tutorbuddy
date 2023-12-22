package ng.edu.mautech.tutorbuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ng.edu.mautech.tutorbuddy.model.User;
import ng.edu.mautech.tutorbuddy.model.UserPrincipal;
import ng.edu.mautech.tutorbuddy.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        // You should pass the selected role as a parameter to the UserPrincipal
        // constructor
        return new UserPrincipal(user, "role"); // Replace "your_selected_role" with the actual selected role
    }
}

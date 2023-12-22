package ng.edu.mautech.tutorbuddy.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.mautech.tutorbuddy.model.Role;
import ng.edu.mautech.tutorbuddy.model.User;
import ng.edu.mautech.tutorbuddy.repository.RoleRepository;
import ng.edu.mautech.tutorbuddy.repository.UserRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    };

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public void assignUserRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);

        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);

        userRepository.save(user);
    }

    public void unassignedRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElse(null);
        Set<Role> userRoles = user.getRoles();

        userRoles.removeIf(x -> x.getId() == roleId);
        userRepository.save(user);
    }

    public Set<Role> getUserRoles(User user) {
        return user.getRoles();
    }

    public Set<Role> getUserNotRoles(User user) {
        return roleRepository.getUserNotRoles(user.getUser_id());
    }
}
package ng.edu.mautech.tutorbuddy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ng.edu.mautech.tutorbuddy.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByFirstnameAndLastname(String firstname, String lastname);

    List<User> findAll();

    void deleteById(Long id);

    Optional<User> findById(Long id);

    List<User> findByRoles_Name(String roleName);

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.courses c WHERE 'tutor' = ?1")
    List<User> findTutorsWithAssignedCourses(String roleName);

}

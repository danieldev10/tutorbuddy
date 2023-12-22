package ng.edu.mautech.tutorbuddy.repository;

import ng.edu.mautech.tutorbuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends JpaRepository<User, Long> {
    List<User> findByRoles_Name(String roleName);

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.courses c WHERE 'ROLE_TUTOR' MEMBER OF u.roles")
    List<User> findTutorsWithAssignedCourses();
}

package bg.softuni.bookworld.data;

import bg.softuni.bookworld.model.Role;
import bg.softuni.bookworld.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleType roleType);
}

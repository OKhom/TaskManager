package ua.kiev.prog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.prog.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}

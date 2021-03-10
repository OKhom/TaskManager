package ua.kiev.prog.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.kiev.prog.model.Group;
import ua.kiev.prog.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT c FROM Task c WHERE c.group = :group")
    List<Task> findByGroup(@Param("group") Group group, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Task c WHERE c.group = :group")
    long countByGroup(@Param("group") Group group);

    @Query("SELECT c FROM Task c WHERE LOWER(c.taskName) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Task> findByPattern(@Param("pattern") String pattern,
                             Pageable pageable);

    // List<Task> findByNameOrEmailOrderById(String s);
    // List<Task> findByNameAndEmail(String name, String email);
}

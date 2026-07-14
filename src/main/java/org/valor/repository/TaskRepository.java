package org.valor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.valor.model.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}

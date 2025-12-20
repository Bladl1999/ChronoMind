package org.valor.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.valor.model.entity.TaskDiary;

import java.util.UUID;

@Repository
public interface TaskDiaryRepository extends JpaRepository<TaskDiary, UUID> {
}

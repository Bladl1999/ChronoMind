package org.valor.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.valor.model.entity.TaskDiary;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskDiaryRepository extends JpaRepository<TaskDiary, UUID> {
    @Query("SELECT t FROM TaskDiary t WHERE t.name ilike :query ")
    List<TaskDiary> search(@Param("query") String query);
}

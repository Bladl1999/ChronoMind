package org.valor.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.valor.model.entity.TaskDiary;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface TaskDiaryRepository extends JpaRepository<TaskDiary, UUID> {

    @Modifying
    @Query("UPDATE TaskDiary t SET " +
            "t.name = '[ ' :now ' ]',\n" +
            "t.deleted = 1,\n" +
            "t.deleteTimestamp = :now")
    UUID safeDelete(UUID id, Instant now);
}

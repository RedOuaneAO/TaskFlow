package com.redone.taskflow.repositories;

import com.redone.taskflow.demain.models.TaskModification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskModificationRepository extends JpaRepository<TaskModification ,Long> {
}

package com.redone.taskflow.repositories;

import com.redone.taskflow.demain.models.Task;
import com.redone.taskflow.demain.models.TaskModification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskModificationRepository extends JpaRepository<TaskModification ,Long> {

}

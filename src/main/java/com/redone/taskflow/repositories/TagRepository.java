package com.redone.taskflow.repositories;

import com.redone.taskflow.demain.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
}

package org.crud.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.crud.entity.Task;

import java.util.List;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {
    public List<Task> findByUserId(Long userID) {return list("userId", userID); }
}

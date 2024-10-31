package org.crud.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.crud.dto.TaskResponseDto;
import org.crud.dto.UserDto;
import org.crud.entity.Task;
import org.crud.repository.TaskRepository;
import org.crud.rest.UserRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@ApplicationScoped
public class TaskServices {
    private final Logger LOGGER = LoggerFactory.getLogger(TaskServices.class);

    @Inject @RestClient
    UserRestClient userRestClient;
    @Inject
    TaskRepository taskRepository;

    public TaskServices(TaskRepository taskRepository) { this.taskRepository = taskRepository; }

    public void createTask(Task task) { taskRepository.persist(task); }

    public Task getTaskById(Long id) {
        if (taskRepository.findById(id)!=null){
            return taskRepository.findById(id);
        }else{
            LOGGER.warn("La tarea con el id {} no existe", id);
            return null;
        }
    }

    public List<Task> listAllTask() { return taskRepository.listAll(); }

    public void updateTask (Long id, Task task2) {
        if (taskRepository.findById(id)!=null){
            Task oldTask = taskRepository.findById(id);
            oldTask.setTitle(task2.getTitle());
            oldTask.setDescription(task2.getDescription());
            oldTask.setExpirationDate(task2.getExpirationDate());
            oldTask.setStatus(task2.getStatus());
            LOGGER.info("La tarea ha sido actualizada existosamente ");
        } else {
            LOGGER.warn("La tarea con el id {} no existe ", id);
        }
    }
    public void deleteTask(Long id){
        if (taskRepository.findById(id)!=null){
            taskRepository.delete(taskRepository.findById(id));
            LOGGER.info("Tarea eliminada exitosamente");
        } else {
            LOGGER.warn("La tarea con el id : {} no existe",id);
        }
    }

    public TaskResponseDto asignarTarea(Long tareaId, Long usuarioId) {
        UserDto usuario = userRestClient.getUserById(usuarioId);
        if(usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Task tarea = taskRepository.findById(tareaId);
        if (tarea == null) {
            throw new RuntimeException("Tarea no encontrada");
        }
        tarea.setUserId(usuarioId);
        taskRepository.persist(tarea);
        return new TaskResponseDto(tarea.getId(),tarea.getTitle(),tarea.getDescription(),tarea.getStatus(), usuario);
    }

    public List<TaskResponseDto> getTaskByUser(Long usuarioId) {
        List<Task> tareas = taskRepository.findByUserId(usuarioId);

        UserDto usuario = userRestClient.getUserById(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        return tareas.stream()
                .map(tarea -> new TaskResponseDto(tarea.getId(),tarea.getTitle(),tarea.getDescription(),tarea.getStatus(), usuario))
                .collect(Collectors.toList());
    }


}

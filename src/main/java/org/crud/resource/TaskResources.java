package org.crud.resource;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.crud.dto.TaskResponseDto;
import org.crud.entity.Task;
import org.crud.service.TaskServices;

import java.util.List;

@Path("/task")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResources {

    @Inject
    TaskServices taskServices;

    @POST
    public Response createTask(Task task) {
        taskServices.createTask(task);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Task> taskList () {return taskServices.listAllTask();}

    @GET
    @Path("/{id}")
    public Task getTaskById(@PathParam("id") Long id) {return taskServices.getTaskById(id); }

    @PUT
    @Path("/{id}")
    public void updateTask(@PathParam("id") Long id, Task task2) {taskServices.updateTask(id, task2);}

    @DELETE
    @Path("/{id}")
    public void deleteTask(@PathParam("id")Long id) {taskServices.deleteTask(id);}

    @POST
    @Path("/asignar/{taskId}/{user}")
    public TaskResponseDto asignarTarea(@PathParam("taskId")Long id, @PathParam("user") Long userId) {
        return taskServices.asignarTarea(id,userId);
    }

    @GET
    @Path("/usuario/{id}")
    public List<TaskResponseDto> getTaskByUser(@PathParam("id")Long id) {return taskServices.getTaskByUser(id);}
}

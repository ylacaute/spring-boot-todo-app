/**
 * Created by Yannick Lacaute on 18/01/17.
 * Copyright 2015-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thorpora.module.todo.web;

import com.thorpora.module.core.web.AbstractRestController;
import com.thorpora.module.todo.TaskResource;
import com.thorpora.module.todo.domain.Task;
import com.thorpora.module.todo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Each mapping is rewrited : more readable and ease the search and refactoring.
 */
public class TaskController extends AbstractRestController<TaskResource, Task> {

    private final static Logger log = LoggerFactory.getLogger(TaskController.class);

    public static final String TASK_MAPPING = "/api/todo/{todoId}/task";
    public static final String TASK_ID_MAPPING = "/api/todo/{todoId}/task/{id}";
    public static final String TASKS_MAPPING = "/api/todo/{todoId}/tasks";

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        super(TASK_MAPPING, taskService, new TaskResourceConverter());
        this.taskService = taskService;
    }

    /**
     * POST
     */
    @PostMapping(TASK_MAPPING)
    public ResponseEntity<TaskResource> postResource(
            @PathVariable UUID todoId,
            @RequestBody TaskResource resource) {

        Task task = converter.toEntity(resource);
        task.setHref(getServletPath() + "/" + task.getId());
        task = taskService.save(task, todoId);

        return postResponse(task);
    }

    /**
     * GET ONE
     */
    @GetMapping(TASK_ID_MAPPING)
    public ResponseEntity<TaskResource> getResource(
            @PathVariable UUID todoId,
            @PathVariable UUID id) {

        Task task = taskService.getOne(id, todoId);

        return super.getRequest(id);
    }

    /**
     * GET MANY
     */
    @GetMapping(TASKS_MAPPING)
    public ResponseEntity<List<TaskResource>> getResources(
            @PathVariable UUID todoId) {

        List<Task> tasks = taskService.findAll(todoId);

        return super.getListRequest();
    }

    /**
     * PUT ONE
     */
    @PutMapping(TASK_ID_MAPPING)
    public ResponseEntity<TaskResource> putResource(
            @PathVariable UUID todoId,
            @PathVariable UUID id,
            @RequestBody TaskResource resource) {

        Task task = taskService.updateOne(id, todoId, converter.toEntity(resource));

        return super.putRequest(id, resource);
    }

    /**
     * DELETE ONE
     */
    @DeleteMapping(TASK_ID_MAPPING)
    public ResponseEntity<Void> deleteResource(
            @PathVariable UUID todoId,
            @PathVariable UUID id) {

        taskService.deleteOne(todoId, id);

        return super.deleteResponse(id);
    }


}

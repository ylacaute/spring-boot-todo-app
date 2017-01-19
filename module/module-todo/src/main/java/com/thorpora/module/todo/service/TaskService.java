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
package com.thorpora.module.todo.service;

import com.thorpora.module.core.service.AbstractRestService;
import com.thorpora.module.todo.domain.Task;
import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.exception.TaskNotFoundException;
import com.thorpora.module.todo.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskService extends AbstractRestService<Task> {

    private final static Logger log = LoggerFactory.getLogger(TaskService.class);

    private TaskRepository taskRepository;

    private TodoService todoService;

    public TaskService(TaskRepository taskRepository,
                       TodoService todoService) {
        this.taskRepository = taskRepository;
        this.todoService = todoService;
    }


    @Transactional(readOnly = true)
    @Override
    public Task getOne(UUID id) {
        return taskRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task save(Task entity) {
        return taskRepository.save(entity);
    }

    @Transactional
    @Override
    public Iterable<Task> save(Collection<Task> tasks) {
        Iterable<Task> savedUsers = taskRepository.save(tasks);
        log.debug("{} Task(s) have been saved", tasks.size());
        return savedUsers;
    }

    @Transactional
    @Override
    public Task updateOne(UUID id, Task entity) {
        entity.setId(id);
        return taskRepository.save(entity);
    }

    @Override
    public Optional<Task> deleteOne(UUID id) {
        return null;
    }

    @Transactional(readOnly = true)
    public Task getOne(UUID id, UUID todoId) {
        Todo todo = todoService.getOne(todoId);
        Optional<Task> task = todo.getTask(id);
        if (!task.isPresent()) {
            throw new TaskNotFoundException(String.format("Task %s doesn't exist", id));
        }
        return task.get();
    }

    @Transactional(readOnly = true)
    public List<Task> findAll(UUID todoId) {
        return todoService.getOne(todoId).getTasks();
    }


    @Transactional
    public Task save(Task entity, UUID todoId) {
        Todo todo = todoService.getOne(todoId);
        todo.addTask(entity);
        todoService.save(todo);

        return taskRepository.save(entity);
    }

    @Transactional
    public Task updateOne(UUID id, UUID todoId, Task entity) {
        entity.setId(id);
        Todo todo = todoService.getOne(todoId);
        assertSecurity(id, todo);
        todo.getTasks().removeIf(t -> t.getId().equals(id));
        todo.getTasks().add(entity);
        todo = todoService.save(todo);
        return todo.getTasks().stream()
                .filter(t -> t.equals(entity))
                .findFirst()
                .get();
    }

    @Transactional
    public void deleteOne(UUID todoId, UUID id) {
        Todo todo = todoService.getOne(todoId);
        assertSecurity(id, todo);
        todo.removeTask(id);
        taskRepository.delete(id);
    }

    private void assertSecurity(UUID taskId, Todo todo) {
        if (!todo.containsTask(taskId)) {
            throw new SecurityException(String.format(
                    "Trying to operate on a taskId=%s which doesn't exist in todoId=%s",
                    taskId, todo.getId()));
        }
    }

}

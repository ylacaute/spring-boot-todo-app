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
import com.thorpora.module.todo.TodoResource;
import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Each mapping is rewrited : more readable and ease the search and refactoring.
 */
public class TodoController extends AbstractRestController<TodoResource, Todo> {

    private final static Logger log = LoggerFactory.getLogger(TodoController.class);

    public static final String TODO_MAPPING = "/api/todo";
    public static final String TODO_ID_MAPPING = "/api/todo/{id}";
    public static final String TODOS_MAPPING = "/api/todos";

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        super(TODO_MAPPING, todoService, new TodoResourceConverter());
        this.todoService = todoService;
    }

    /**
     * POST
     */
    @PostMapping(TODO_MAPPING)
    @Override
    public ResponseEntity<TodoResource> postRequest(@RequestBody TodoResource resource) {
        return super.postRequest(resource);
    }

    /**
     * GET ONE
     */
    @GetMapping(TODO_ID_MAPPING)
    @Override
    public ResponseEntity<TodoResource> getRequest(@PathVariable UUID id) {
        return super.getRequest(id);
    }

    /**
     * GET MANY
     */
    @GetMapping(TODOS_MAPPING)
    @Override
    public ResponseEntity<List<TodoResource>> getListRequest() {
        return super.getListRequest();
    }

    /**
     * PUT ONE
     */
    @PutMapping(TODO_ID_MAPPING)
    @Override
    public ResponseEntity<TodoResource> putRequest(@PathVariable UUID id,
                                                   @RequestBody TodoResource resource) {
        return super.putRequest(id, resource);
    }

    /**
     * DELETE ONE
     */
    @DeleteMapping(TODO_ID_MAPPING)
    @Override
    public ResponseEntity<Void> deleteRequest(@PathVariable UUID id) {
        return super.deleteRequest(id);
    }

}

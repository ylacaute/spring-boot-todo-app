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
package com.thorpora.module.todo.fixture;

import com.thorpora.module.todo.domain.Task;
import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.web.TodoController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TodoFixtures {

    private static AtomicLong idCounter = new AtomicLong(0L);
    private Todo todo;

    private TodoFixtures() {
        todo = createDefaultTodo();
    }

    public static Todo create() {
        return createDefaultTodo();
    }

    public static List<Todo> create(int todoCount) {
        return IntStream
                .range(0, todoCount)
                .mapToObj(i -> TodoFixtures.create())
                .collect(Collectors.toList());
    }

    public static TodoFixtures builder() {
        return new TodoFixtures();
    }

    public TodoFixtures id(UUID id) {
        todo.setId(id);
        return this;
    }

    public TodoFixtures tasks(List<Task> tasks) {
        todo.setTasks(tasks);
        return this;
    }

    public TodoFixtures tasks(int taskCount) {
        todo.setTasks(IntStream
                .range(0, taskCount)
                .mapToObj(i -> TaskFixtures.create(todo.getId().toString()))
                .collect(Collectors.toList()));
        return this;
    }

    public TodoFixtures ownerId(UUID owerId) {
        todo.setOwnerId(owerId);
        return this;
    }

    public Todo build() {
        return todo;
    }

    public static Todo createDefaultTodo() {
        Long id = idCounter.getAndIncrement();
        Todo todo = new Todo();
        todo.setHref(TodoController.TODO_ID_MAPPING.replace("{id}", todo.getId().toString()));
        todo.setOwnerId(UUID.randomUUID());
        todo.setTasks(new ArrayList<>());
        return todo;
    }

}

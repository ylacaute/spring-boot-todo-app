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

import com.thorpora.module.todo.TaskResource;
import com.thorpora.module.todo.TodoResource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TodoResourceFixtures {

    private static AtomicLong idCounter = new AtomicLong(0L);
    private TodoResource todo;

    private TodoResourceFixtures() {
        todo = createDefaultTodoResource();
    }

    public static TodoResource create() {
        return createDefaultTodoResource();
    }

    public static List<TodoResource> create(int resourceCount) {
        return IntStream
                .range(0, resourceCount)
                .mapToObj(i -> createDefaultTodoResource())
                .collect(Collectors.toList());
    }

    public static TodoResourceFixtures builder() {
        return new TodoResourceFixtures();
    }

    public TodoResourceFixtures id(UUID id) {
        todo.setId(id);
        return this;
    }

    public TodoResourceFixtures task(TaskResource task) {
        todo.addTask(task);
        return this;
    }

    public TodoResourceFixtures tasks(List<TaskResource> tasks) {
        todo.setTasks(tasks);
        return this;
    }

    public TodoResourceFixtures generateTasks(int taskCount) {
        todo.setTasks(TaskResourceFixtures.create(taskCount));
        return this;
    }

    public TodoResourceFixtures ownerId(UUID owerId) {
        todo.setOwnerId(owerId);
        return this;
    }

    public TodoResource build() {
        return todo;
    }

    public static TodoResource createDefaultTodoResource() {
        Long id = idCounter.getAndIncrement();
        TodoResource todo = new TodoResource();
        todo.setOwnerId(UUID.randomUUID());
        todo.setTasks(new ArrayList<>());
        return todo;
    }

}

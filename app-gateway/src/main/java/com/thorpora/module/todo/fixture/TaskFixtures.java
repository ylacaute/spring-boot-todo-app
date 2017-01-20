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
import com.thorpora.module.todo.web.TaskController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskFixtures {

    private static AtomicLong idCounter = new AtomicLong(0L);
    private Task Task;

    private TaskFixtures(String todoId) {
        Task = createDefaultTask(todoId);
    }

    public static Task create(String todoId) {
        return createDefaultTask(todoId);
    }

    public static List<Task> create(String todoId, int taskCount) {
        return IntStream
                .range(0, taskCount)
                .mapToObj(i -> TaskFixtures.create(todoId))
                .collect(Collectors.toList());
    }

    public static TaskFixtures builder(String todoId) {
        return new TaskFixtures(todoId);
    }

    public TaskFixtures id(UUID id) {
        Task.setId(id);
        return this;
    }

    public TaskFixtures name(String name) {
        Task.setName(name);
        return this;
    }

    public Task build() {
        return Task;
    }

    public static Task createDefaultTask(String todoId) {
        Long id = idCounter.getAndIncrement();
        Task task = new Task();
        task.setHref(TaskController.TASK_ID_MAPPING
                .replace("{todoId}", todoId)
                .replace("{id}", task.getId().toString()));
        task.setName("name_" + id);
        return task;
    }

}

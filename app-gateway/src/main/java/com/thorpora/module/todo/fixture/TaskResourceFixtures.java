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

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskResourceFixtures {

    private static AtomicLong idCounter = new AtomicLong(0L);
    private TaskResource task;

    private TaskResourceFixtures() {
        task = createDefaultTaskResource();
    }

    public static TaskResource create() {
        return createDefaultTaskResource();
    }

    public static List<TaskResource> create(int resourceCount) {
        return IntStream
                .range(0, resourceCount)
                .mapToObj(i -> createDefaultTaskResource())
                .collect(Collectors.toList());
    }

    public static TaskResourceFixtures builder() {
        return new TaskResourceFixtures();
    }

    public TaskResourceFixtures id(UUID id) {
        task.setId(id);
        return this;
    }

    public TaskResourceFixtures name(String name) {
        task.setName(name);
        return this;
    }

    public TaskResource build() {
        return task;
    }

    public static TaskResource createDefaultTaskResource() {
        Long id = idCounter.getAndIncrement();
        TaskResource task = new TaskResource();
        task.setName("name_" + id);
        return task;
    }

}

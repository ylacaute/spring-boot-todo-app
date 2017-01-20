/**
 * Created by Yannick Lacaute on 12/01/17.
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

import com.thorpora.module.core.web.AbstractRestResourceConverter;
import com.thorpora.module.todo.TodoResource;
import com.thorpora.module.todo.domain.Todo;

import java.util.List;
import java.util.stream.Collectors;

public class TodoResourceConverter extends AbstractRestResourceConverter<TodoResource, Todo> {

    private final TaskResourceConverter taskConverter;

    public TodoResourceConverter() {
        this.taskConverter = new TaskResourceConverter();
    }

    @Override
    public TodoResource toResource(Todo entity) {
        TodoResource rsc = new TodoResource();
        rsc.setId(entity.getId());
        rsc.setOwnerId(entity.getOwnerId());
        rsc.setTasks(taskConverter.toResource(entity.getTasks()));
        rsc.setHref(entity.getHref());
        return rsc;
    }

    @Override
    public List<TodoResource> toResource(List<Todo> todos) {
        return todos.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    @Override
    public Todo toEntity(TodoResource resource) {
        Todo entity = new Todo();
        entity.setOwnerId(resource.getOwnerId());
        entity.setTasks(taskConverter.toEntity(resource.getTasks()));
        entity.setHref(resource.getHref());
        return entity;
    }

    @Override
    public List<Todo> toEntity(List<TodoResource> entities) {
        return entities.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

}

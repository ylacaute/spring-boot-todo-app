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
import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TodoService extends AbstractRestService<Todo> {

    private final static Logger log = LoggerFactory.getLogger(TodoService.class);

    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Todo getOne(UUID id) {
        return todoRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Transactional
    @Override
    public Todo save(Todo entity) {
        return todoRepository.save(entity);
    }

    @Transactional
    @Override
    public Iterable<Todo> save(Collection<Todo> todos) {
        Iterable<Todo> savedUsers = todoRepository.save(todos);
        log.debug("{} Todo(s) have been saved", todos.size());
        return savedUsers;
    }

    @Transactional
    @Override
    public Todo updateOne(UUID pathId, Todo entity) {
        entity.setId(pathId);
        return todoRepository.save(entity);
    }

    @Transactional
    @Override
    public Optional<Todo> deleteOne(UUID id) {
        todoRepository.delete(id);
        return Optional.ofNullable(null);
    }

}

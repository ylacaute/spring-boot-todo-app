/**
 * Created by Yannick Lacaute on 21/01/17.
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
package com.thorpora.module.todo;

import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.fixture.TodoFixtures;
import com.thorpora.module.todo.repository.TodoRepository;
import com.thorpora.module.todo.service.TodoService;
import com.thorpora.test.junit.TestDecorator;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.MockitoAnnotations.initMocks;


public class TodoServiceTest {

    private final static Logger log = LoggerFactory.getLogger(TodoServiceTest.class);

    @Rule
    public TestDecorator testDecorator = new TestDecorator();

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;


    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void todo_findAll_emptyResult() {
        Mockito.when(todoRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertThat(todoService.findAll()).hasSize(0);
    }

    @Test
    public void todo_findAll_onResult() {
        List<Todo> todos = TodoFixtures.create(5);
        Mockito.when(todoRepository.findAll()).thenReturn(todos);
        Assertions.assertThat(todoService.findAll()).hasSize(todos.size());
    }

    @Test
    public void todo_getOne_getIt() {
        Todo todo = TodoFixtures.create();
        Mockito.when(todoRepository.getOne(todo.getId())).thenReturn(todo);
        Assertions.assertThat(todoService.getOne(todo.getId())).isEqualTo(todo);
    }

    @Test
    public void todo_deleteNotExist_optionalNotPresent() {
        Assertions.assertThat(todoService.deleteOne(UUID.randomUUID()).isPresent()).isFalse();
    }

}

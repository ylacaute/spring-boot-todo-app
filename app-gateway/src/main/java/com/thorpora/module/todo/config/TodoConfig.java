/**
 * Created by Yannick Lacaute on 09/01/17.
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
package com.thorpora.module.todo.config;

import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.repository.TaskRepository;
import com.thorpora.module.todo.repository.TodoRepository;
import com.thorpora.module.todo.service.TaskService;
import com.thorpora.module.todo.service.TodoService;
import com.thorpora.module.todo.web.TaskController;
import com.thorpora.module.todo.web.TodoController;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = Todo.class)
@EnableJpaRepositories(basePackageClasses = TodoRepository.class)
public class TodoConfig {

    @Bean
    public TodoController todoController(TodoService todoService) {
        return new TodoController(todoService);
    }

    @Bean
    public TaskController taskController(TaskService taskService) {
        return new TaskController(taskService);
    }

    @Bean
    public TodoService todoService(TodoRepository todoRepository) {
        return new TodoService(todoRepository);
    }

    @Bean
    public TaskService taskService(TaskRepository taskRepository, TodoService todoService) {
        return new TaskService(taskRepository, todoService);
    }


}

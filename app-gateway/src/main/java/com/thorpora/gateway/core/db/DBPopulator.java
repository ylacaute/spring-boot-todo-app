/**
 * Created by Yannick Lacaute on 27/12/16.
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
package com.thorpora.gateway.core.db;


import com.thorpora.gateway.core.log.LogColorUtils;
import com.thorpora.module.mail.core.MailService;
import com.thorpora.module.mail.fixture.MailFixtures;
import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.fixture.TodoFixtures;
import com.thorpora.module.todo.service.TaskService;
import com.thorpora.module.todo.service.TodoService;
import com.thorpora.module.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public class DBPopulator {

    private final static Logger logger = LoggerFactory.getLogger(DBPopulator.class);

    private UserService userService;
    private MailService mailService;
    private TodoService todoService;
    private TaskService taskService;

    public DBPopulator(UserService userService,
                       MailService mailService,
                       TodoService todoService,
                       TaskService taskService) {
        this.userService = userService;
        this.mailService = mailService;
        this.todoService = todoService;
        this.taskService = taskService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        populate();
    }

    public void populate() {
        mailService.save(MailFixtures.create());
        /*
        mailService.save(MailFixtures.builder()
                .addParameter("user", users.get(0))
                .build());
        */
        //taskService.save(TaskFixtures.create());
        Todo todo = TodoFixtures.builder().tasks(3).build();
        todoService.save(todo);
        logger.info("DB populated");
    }

}

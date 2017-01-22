package com.thorpora.module.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thorpora.gateway.core.db.DBCleaner;
import com.thorpora.test.junit.TestDecorator;
import com.thorpora.test.rest.*;
import com.thorpora.test.env.AbstractServletEnvIT;
import com.thorpora.module.todo.domain.Task;
import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.fixture.TaskResourceFixtures;
import com.thorpora.module.todo.fixture.TodoFixtures;
import com.thorpora.module.todo.repository.TaskRepository;
import com.thorpora.module.todo.repository.TodoRepository;
import com.thorpora.module.todo.service.TodoService;
import com.thorpora.module.todo.web.TaskController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Convention name test: resource_action_expectedBehavior
 */

public class TaskControllerIT extends AbstractServletEnvIT {

    private final static Logger log = LoggerFactory.getLogger(TaskControllerIT.class);

    @Inject
    private ObjectMapper jMapper;

    @Inject
    private DBCleaner dbCleaner;

    @Inject
    private TestRestTemplate restTemplate;

    @Rule
    public TestDecorator testDecorator = new TestDecorator();

    @Inject
    private TaskRepository taskRepository;

    @Inject
    private TodoRepository todoRepository;

    @Inject
    private TodoService todoService;

    private final UUID randomUUUID = UUID.randomUUID();

    private TestRestHelper<TaskResource, Task> restTest;

    @PostConstruct
    public void beforeClass() {
        restTest = new TestRestHelper<TaskResource, Task>(
                restTemplate,
                TaskController.TASK_MAPPING,
                TaskController.TASK_ID_MAPPING,
                TaskController.TASKS_MAPPING) {
            @Override
            protected ParameterizedTypeReference<List<TaskResource>> getResourceListType() {
                return new ParameterizedTypeReference<List<TaskResource>>() {};
            }
        };
    }

    @Before
    public void beforeTest() {
        dbCleaner.clear("todo", "task");
    }

    @Test
    public void task_getMany_validNoResult() {
        final Todo todo = todoService.save(TodoFixtures.create());
        restTest.getManyWithoutResult(todo.getId());
    }

    @Test
    public void task_postOne_validPost() {
        final Todo todo = todoService.save(TodoFixtures.create());
        ResourceCreator<TaskResource> creator = TaskResourceFixtures::create;
        ResourceVerifier<TaskResource> verifier = this::genericVerifier;
        restTest.postOne(creator, verifier, todo.getId());
    }

    @Test
    public void task_postOneAndGetIt_validGet() {
        final Todo todo = todoService.save(TodoFixtures.create());
        ResourceCreator<TaskResource> creator = TaskResourceFixtures::create;
        ResourceVerifier<TaskResource> verifier = this::genericVerifier;
        restTest.postOneAndGetIt(creator, verifier, todo.getId());
    }

    @Test
    public void task_postManyAndGetThem_validGet() {
        final Todo todo = todoService.save(TodoFixtures.create());
        final int taskCount = 5;
        final int specificTaskTaskCount = 3;

        ResourceListCreator<TaskResource> creator = () -> {
            Collection<TaskResource> resources = TaskResourceFixtures
                    .create(taskCount - 1);
            resources.add(TaskResourceFixtures.builder()
                    .name(randomUUUID.toString())
                    .build());
            return resources;
        };

        ResourceListVerifier<TaskResource> verifier = (resourcesGot) -> {

            // Verify we got all elements
            assertThat(resourcesGot).hasSize(taskCount);

            // Verify a particular element
            Optional<TaskResource> task = resourcesGot.stream()
                    .filter(r -> r.getName().equals(randomUUUID.toString()))
                    .findFirst();
            assertThat(task.isPresent()).isTrue();
            assertThat(task.get().getHref()).contains(todo.getHref());
        };

        restTest.postManyAndGetThem(creator, verifier, todo.getId());
    }

    @Test
    public void task_postOnePutItWithoutModificationAndGetIt_validGet() {
        final Todo todo = todoService.save(TodoFixtures.create());
        ResourceCreator<TaskResource> creator = TaskResourceFixtures::create;
        ResourceModifier<TaskResource> modifier = (r) -> r;
        ResourceVerifier<TaskResource> verifier = this::genericVerifier;
        restTest.postOnePutItAndGetIt(creator, modifier, verifier, todo.getId());
    }

    @Test
    public void task_postOnePutItAndGetIt_validGet() {
        final Todo todo = todoService.save(TodoFixtures.create());
        final int initialTaskCount = 0;

        ResourceCreator<TaskResource> creator = TaskResourceFixtures::create;
        ResourceModifier<TaskResource> modifier = (r) -> {
            r.setName(randomUUUID.toString());
            return r;
        };
        ResourceVerifier<TaskResource> verifier = (postedRrc, rscGot) -> {
            assertThat(rscGot.getName()).isEqualTo(randomUUUID.toString());
        };

        restTest.postOnePutItAndGetIt(creator, modifier, verifier, todo.getId());
    }

    @Test
    public void task_postOneDeleteItAndGetItNotFound_validGet() {
        final Todo todo = todoService.save(TodoFixtures.create());
        ResourceCreator<TaskResource> creator = TaskResourceFixtures::create;
        restTest.postOneDeleteItAndGetItNotFound(creator, todo.getId());
    }

    @Test
    public void task_deleteNotExist_validDelete() {
        final UUID unexistingTodoId = UUID.randomUUID();
        final UUID unexistingTaskId = UUID.randomUUID();
        restTest.deleteNotExist(unexistingTodoId, unexistingTaskId);
    }

    private void genericVerifier(TaskResource postedResource, TaskResource resourceGot) {
        assertThat(postedResource.getName()).isEqualTo(resourceGot.getName());
    }

}
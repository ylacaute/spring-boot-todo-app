package com.thorpora.module.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thorpora.gateway.core.db.DBCleaner;
import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.fixture.TaskResourceFixtures;
import com.thorpora.module.todo.fixture.TodoResourceFixtures;
import com.thorpora.module.todo.repository.TodoRepository;
import com.thorpora.module.todo.web.TodoController;
import com.thorpora.test.env.AbstractServletEnvIT;
import com.thorpora.test.junit.TestDecorator;
import com.thorpora.test.rest.*;
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
public class TodoControllerIT extends AbstractServletEnvIT {

    private final static Logger log = LoggerFactory.getLogger(TodoControllerIT.class);

    @Inject
    private ObjectMapper jMapper;

    @Inject
    private DBCleaner dbCleaner;

    @Inject
    private TestRestTemplate restTemplate;

    @Inject
    private TodoRepository todoRepository;

    @Rule
    public TestDecorator testDecorator = new TestDecorator();

    private final UUID randomUUUID = UUID.randomUUID();

    private TestRestHelper<TodoResource, Todo> restTest;

    @PostConstruct
    public void beforeClass() {
        restTest = new TestRestHelper<TodoResource, Todo>(
                restTemplate,
                TodoController.TODO_MAPPING,
                TodoController.TODO_ID_MAPPING,
                TodoController.TODOS_MAPPING) {
            @Override
            protected ParameterizedTypeReference<List<TodoResource>> getResourceListType() {
                return new ParameterizedTypeReference<List<TodoResource>>() {};
            }
        };
    }

    @Before
    public void beforeTest() {
        dbCleaner.clear("todo", "task");
    }

    @Test
    public void todo_getMany_validNoResult() {
        restTest.getManyWithoutResult();
    }

    @Test
    public void todo_postOneWithoutTask_validPost() {
        ResourceCreator<TodoResource> creator = TodoResourceFixtures::create;
        ResourceVerifier<TodoResource> verifier = this::verifyEqualFields;
        restTest.postOne(creator, verifier);
    }

    @Test
    public void todo_postOneWithTasks_validPost() {
        final int generatedTaskCount = 3;

        ResourceCreator<TodoResource> creator =
                () -> TodoResourceFixtures.builder()
                        .generateTasks(generatedTaskCount).build();

        ResourceVerifier<TodoResource> verifier =
                (postedResource, resourceGot) -> {
                    verifyEqualFields(postedResource, resourceGot);
                    assertThat(resourceGot.getTasks().size()).isEqualTo(generatedTaskCount);
                };

        restTest.postOne(creator, verifier);
    }

    @Test
    public void todo_postOneWithTasksAndGetIt_validGet() {
        final int generatedTaskCount = 3;

        ResourceCreator<TodoResource> creator =
                () -> TodoResourceFixtures.builder()
                        .generateTasks(generatedTaskCount).build();

        ResourceVerifier<TodoResource> verifier =
                (postedResource, resourceGot) -> {
                    verifyEqualFields(postedResource, resourceGot);
                    assertThat(resourceGot.getTasks().size()).isEqualTo(generatedTaskCount);
                };

        restTest.postOneAndGetIt(creator, verifier);
    }

    @Test
    public void todo_postManyAndGetThem_validGet() {
        final int expectedTodoCount = 5;
        final int specificTodoTaskCount = 3;

        ResourceListCreator<TodoResource> creator = () -> {
            Collection<TodoResource> resources = TodoResourceFixtures.create(expectedTodoCount - 1);
            resources.add(TodoResourceFixtures.builder()
                    .ownerId(randomUUUID)
                    .tasks(TaskResourceFixtures.create(specificTodoTaskCount))
                    .build());
            return resources;
        };

        ResourceListVerifier<TodoResource> verifier = (resourcesGot) -> {

            // Verify we got all elements
            assertThat(resourcesGot).hasSize(expectedTodoCount);

            // Verify a particular element
            int taskCount = resourcesGot.stream()
                    .filter(r -> r.getOwnerId().equals(randomUUUID))
                    .findFirst().get()
                    .getTasks().size();
            assertThat(taskCount).isEqualTo(specificTodoTaskCount);
        };

        restTest.postManyAndGetThem(creator, verifier);
    }

    @Test
    public void todo_postOnePutItWithoutModificationAndGetIt_validGet() {
        ResourceCreator<TodoResource> creator = TodoResourceFixtures::create;
        ResourceModifier<TodoResource> modifier = (r) -> r;
        ResourceVerifier<TodoResource> verifier = this::verifyEqualFields;
        restTest.postOnePutItAndGetIt(creator, modifier, verifier);
    }

    @Test
    public void todo_postOnePutItAndGetIt_validGet() {
        final int initialTaskCount = 0;
        final String newTaskName = UUID.randomUUID().toString();

        ResourceCreator<TodoResource> creator = TodoResourceFixtures::create;

        ResourceModifier<TodoResource> modifier = (r) -> {
            r.addTask(TaskResourceFixtures.builder().name(newTaskName).build());
            return r;
        };

        ResourceVerifier<TodoResource> verifier = (postedRrc, rscGot) -> {
            assertThat(rscGot.getTasks()).hasSize(initialTaskCount + 1);
            Optional<TaskResource> addedTask = rscGot.getTasks().stream()
                    .filter(t -> t.getName().equals(newTaskName))
                    .findFirst();
            assertThat(addedTask.isPresent()).isTrue();
        };

        restTest.postOnePutItAndGetIt(creator, modifier, verifier);
    }

    @Test
    public void todo_postOnePutItRemoveTaskAndGetIt_validGet() {
        String removedTaskname = randomUUUID.toString();
        final int generatedTaskCount = 10 - 1;

        ResourceCreator<TodoResource> creator = () -> TodoResourceFixtures.builder()
                .task(TaskResourceFixtures.builder()
                        .name(removedTaskname).build())
                .tasks(TaskResourceFixtures
                        .create(generatedTaskCount))
                .build();

        ResourceModifier<TodoResource> modifier = (r) -> {
            r.getTasks().removeIf(t -> t.getName().equals(removedTaskname));
            return r;
        };

        ResourceVerifier<TodoResource> verifier = (postedRrc, rscGot) -> {
            assertThat(rscGot.getTasks()).hasSize(generatedTaskCount);
            Optional<TaskResource> task = rscGot.getTasks().stream()
                    .filter(t -> t.getName().equals(removedTaskname))
                    .findFirst();
            assertThat(task.isPresent()).isFalse();
        };

        restTest.postOnePutItAndGetIt(creator, modifier, verifier);
    }

    @Test
    public void todo_postOneDeleteItAndGetIt_getNotFound() {
        ResourceCreator<TodoResource> creator = TodoResourceFixtures::create;
        restTest.postOneDeleteItAndGetItNotFound(creator);
    }

    @Test
    public void todo_deleteNotExist_validDelete() {
        final UUID unexistingTodoId = UUID.randomUUID();
        restTest.deleteNotExist(unexistingTodoId);
    }


    private void verifyEqualFields(TodoResource postedResource, TodoResource resourceGot) {
        assertThat(postedResource.getTasks().size())
                .isEqualTo(resourceGot.getTasks().size());
        assertThat(postedResource.getOwnerId())
                .isEqualTo(resourceGot.getOwnerId());
    }

}
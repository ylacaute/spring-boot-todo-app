package com.thorpora.gateway.todo;

import com.thorpora.gateway.*;
import com.thorpora.module.todo.TaskResource;
import com.thorpora.module.todo.TodoResource;
import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.fixture.TaskResourceFixtures;
import com.thorpora.module.todo.fixture.TodoResourceFixtures;
import com.thorpora.module.todo.repository.TodoRepository;
import com.thorpora.module.todo.web.TodoController;
import com.thorpora.module.todo.web.TodoResourceConverter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoControllerIT extends AbstractRestControllerIT<TodoResource, Todo> {

    @Inject
    private TodoRepository todoRepository;

    private final UUID randomUUUID = UUID.randomUUID();

    public TodoControllerIT() {
        super(new TodoResourceConverter(),
                TodoResource.class,
                TodoController.TODO_MAPPING,
                TodoController.TODO_ID_MAPPING,
                TodoController.TODOS_MAPPING);
    }

    @Before
    public void beforeTest() {
        todoRepository.deleteAll();
    }

    /**
     * TEST GET MANY EMPTY
     */
    @Test
    public void getManyWithoutResult() {
        super.getManyWithoutResult();
    }

    /**
     * TEST POST
     */
    @Test
    public void postOneWithoutTask() {
        ResourceCreator<TodoResource> creator = TodoResourceFixtures::create;
        ResourceVerifier<TodoResource> verifier = this::verifyEqualFields;

        super.postOne(creator, verifier);
    }

    /**
     * TEST POST
     */
    @Test
    public void postOneWithTasks() {
        final int generatedTaskCount = 3;

        ResourceCreator<TodoResource> creator =
                () -> TodoResourceFixtures.builder()
                        .generateTasks(generatedTaskCount).build();

        ResourceVerifier<TodoResource> verifier =
                (postedResource, resourceGot) -> {
                    verifyEqualFields(postedResource, resourceGot);
                    assertThat(resourceGot.getTasks().size()).isEqualTo(generatedTaskCount);
                };

        super.postOne(creator, verifier);
    }

    /**
     * TEST POST AND GET
     */
    @Test
    public void postOneAndGetItWithSomeTasks() {
        final int generatedTaskCount = 3;

        ResourceCreator<TodoResource> creator =
                () -> TodoResourceFixtures.builder()
                        .generateTasks(generatedTaskCount).build();

        ResourceVerifier<TodoResource> verifier =
                (postedResource, resourceGot) -> {
                    verifyEqualFields(postedResource, resourceGot);
                    assertThat(resourceGot.getTasks().size()).isEqualTo(generatedTaskCount);
                };

        super.postOneAndGetIt(creator, verifier);
    }

    /**
     * TEST GET MANY
     */
    @Test
    public void postManyAndGetThem() {
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

        super.postManyAndGetThem(creator, verifier);
    }

    /**
     * TEST POST ONE, PUT IT (no modification), GET IT
     */
    @Test
    public void postOnePutItWithoutModificationAndGetIt() {
        ResourceCreator<TodoResource> creator = TodoResourceFixtures::create;
        ResourceModifier<TodoResource> modifier = (r) -> r;
        ResourceVerifier<TodoResource> verifier = this::verifyEqualFields;

        super.postOnePutItAndGetIt(creator, modifier, verifier);
    }

    /**
     * TEST POST ONE, PUT IT (add Child), GET IT
     */
    @Test
    public void postOnePutItAndGetIt() {
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

        super.postOnePutItAndGetIt(creator, modifier, verifier);
    }

    /**
     * TEST POST ONE, PUT IT (remove child), GET IT
     */
    @Test
    public void postOnePutItRemoveTaskAndGetIt() {
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

        super.postOnePutItAndGetIt(creator, modifier, verifier);
    }

    /**
     * TEST DELETE (element exist) AND VERIFY GET NOT FOUND
     */
    @Test
    public void postOneDeleteItAndGetItNotFound() {
        ResourceCreator<TodoResource> creator = TodoResourceFixtures::create;

        super.postOneDeleteItAndGetItNotFound(creator);
    }

    /**
     * TEST DELETE (not exist, must not fail)
     */
    @Test
    public void deleteNotExist() {
        super.deleteNotExist();
    }


    private void verifyEqualFields(TodoResource postedResource, TodoResource resourceGot) {
        assertThat(postedResource.getTasks().size())
                .isEqualTo(resourceGot.getTasks().size());
        assertThat(postedResource.getOwnerId())
                .isEqualTo(resourceGot.getOwnerId());
    }

    @Override
    protected ParameterizedTypeReference<List<TodoResource>> getResourceListType() {
        return new ParameterizedTypeReference<List<TodoResource>>() {
        };
    }

}
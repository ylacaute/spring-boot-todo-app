package com.thorpora.gateway.todo;

import com.thorpora.gateway.*;
import com.thorpora.module.todo.TaskResource;
import com.thorpora.module.todo.domain.Task;
import com.thorpora.module.todo.domain.Todo;
import com.thorpora.module.todo.fixture.TaskResourceFixtures;
import com.thorpora.module.todo.fixture.TodoFixtures;
import com.thorpora.module.todo.repository.TaskRepository;
import com.thorpora.module.todo.repository.TodoRepository;
import com.thorpora.module.todo.service.TodoService;
import com.thorpora.module.todo.web.TaskController;
import com.thorpora.module.todo.web.TaskResourceConverter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskControllerIT extends AbstractRestControllerIT<TaskResource, Task> {

    @Inject
    private TaskRepository taskRepository;

    @Inject
    private TodoRepository todoRepository;

    @Inject
    private TodoService todoService;

    private final UUID randomUUUID = UUID.randomUUID();

    public TaskControllerIT() {
        super(new TaskResourceConverter(),
                TaskResource.class,
                TaskController.TASK_MAPPING,
                TaskController.TASK_ID_MAPPING,
                TaskController.TASKS_MAPPING);
    }

    @Before
    public void beforeTest() {
        todoRepository.deleteAll();
    }

    @Override
    protected ParameterizedTypeReference<List<TaskResource>> getResourceListType() {
        return new ParameterizedTypeReference<List<TaskResource>>() {
        };
    }

    /**
     * TEST GET MANY EMPTY
     */
    @Test
    public void getManyWithoutResult() {
        final Todo todo = todoService.save(TodoFixtures.create());

        super.getManyWithoutResult(todo.getId());
    }

    /**
     * TEST POST
     */
    @Test
    public void postOne() {
        final Todo todo = todoService.save(TodoFixtures.create());

        ResourceCreator<TaskResource> creator = TaskResourceFixtures::create;
        ResourceVerifier<TaskResource> verifier = this::genericVerifier;

        super.postOne(creator, verifier, todo.getId());
    }

    /**
     * TEST POST AND GET
     */
    @Test
    public void postOneAndGetIt() {
        final Todo todo = todoService.save(TodoFixtures.create());

        ResourceCreator<TaskResource> creator = TaskResourceFixtures::create;
        ResourceVerifier<TaskResource> verifier = this::genericVerifier;

        super.postOneAndGetIt(creator, verifier, todo.getId());
    }

    /**
     * TEST GET MANY
     */
    @Test
    public void postManyAndGetThem() {
        final Todo todo = todoService.save(TodoFixtures.create());
        final int taskCount = 5;
        final int specificTaskTaskCount = 3;

        ResourceListCreator<TaskResource> creator = () -> {
            Collection<TaskResource> resources = TaskResourceFixtures.create(taskCount - 1);
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
        };

        super.postManyAndGetThem(creator, verifier, todo.getId());
    }

    /**
     * TEST POST ONE, PUT IT, GET IT
     */
    @Test
    public void postOnePutItWithoutModificationAndGetIt() {
        final Todo todo = todoService.save(TodoFixtures.create());

        ResourceCreator<TaskResource> creator = TaskResourceFixtures::create;
        ResourceModifier<TaskResource> modifier = (r) -> r;
        ResourceVerifier<TaskResource> verifier = this::genericVerifier;

        super.postOnePutItAndGetIt(creator, modifier, verifier, todo.getId());
    }

    /**
     * TEST POST ONE, PUT IT, GET IT
     */
    @Test
    public void postOnePutItAndGetIt() {
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

        super.postOnePutItAndGetIt(creator, modifier, verifier, todo.getId());
    }

    /**
     * TEST POST ONE DELETE IT AND GET NOT FOUND
     */
    @Test
    public void postOneDeleteItAndGetItNotFound() {
        final Todo todo = todoService.save(TodoFixtures.create());

        ResourceCreator<TaskResource> creator = TaskResourceFixtures::create;

        super.postOneDeleteItAndGetItNotFound(creator, todo.getId());
    }

    private void genericVerifier(TaskResource postedResource, TaskResource resourceGot) {
        assertThat(postedResource.getName()).isEqualTo(resourceGot.getName());
    }

}
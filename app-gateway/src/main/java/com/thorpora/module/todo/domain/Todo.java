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
package com.thorpora.module.todo.domain;

import com.thorpora.module.core.domain.AbstractRestEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Please notice the dependency inversion with User, needed if we want to turn the module into
 * a micro service later. Moreover, we must use user id and not the User object.
 */
@Entity
public class Todo extends AbstractRestEntity {

    @NotNull
    private UUID ownerId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public boolean containsTask(UUID taskId) {
        return tasks.stream().anyMatch(t -> taskId.equals(taskId));
    }

    public Optional<Task> getTask(UUID taskId) {
        return tasks.stream().filter(t -> t.getId().equals(taskId)).findFirst();
    }

    public void removeTask(UUID taskId) {
        tasks.removeIf(t -> t.getId().equals(taskId));
    }
}

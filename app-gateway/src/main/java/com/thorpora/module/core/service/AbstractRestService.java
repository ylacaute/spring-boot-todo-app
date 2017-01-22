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
package com.thorpora.module.core.service;

import com.thorpora.module.core.domain.AbstractEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// FIXME Use composition, not inheritance (?)
public abstract class AbstractRestService<Entity extends AbstractEntity> {

    public abstract Entity getOne(UUID id);

    public abstract List<Entity> findAll();

    public abstract Entity save(Entity entity);

    public Entity save(Entity entity, Object... pathVariables) {
        return save(entity);
    }

    public abstract Iterable<Entity> save(Collection<Entity> entities);

    public abstract Entity updateOne(UUID id, Entity entity);

    public abstract Optional<Entity> deleteOne(UUID id);


}

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
package com.thorpora.module.core.web;

import com.thorpora.module.core.domain.AbstractEntity;

import java.util.List;

public abstract class AbstractRestResourceConverter
        <Resource extends RestResource, Entity extends AbstractEntity> {

    public abstract Resource toResource(Entity entity);

    public abstract List<Resource> toResource(List<Entity> entities);

    public abstract Entity toEntity(Resource resource);

    public abstract List<Entity> toEntity(List<Resource> entities);

}

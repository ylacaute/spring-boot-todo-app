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
package com.thorpora.gateway.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thorpora.gateway.AbstractIT;
import com.thorpora.module.todo.TodoResource;
import com.thorpora.module.todo.fixture.TodoFixtures;
import com.thorpora.module.todo.web.TodoResourceConverter;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.inject.Inject;
import java.io.IOException;

public class TodoSerializationIt extends AbstractIT {

    @Inject
    private ObjectMapper objectMapper;

    private static TodoResourceConverter converter = new TodoResourceConverter();

    @Test
    public void serializeAndDeserialize() throws IOException {
        TodoResource rsc = converter.toResource(TodoFixtures.builder().tasks(3).build());
        String rscAsString = objectMapper.writeValueAsString(rsc);
        TodoResource readRsc = objectMapper.readValue(rscAsString, TodoResource.class);
        Assertions.assertThat(rsc).isEqualTo(readRsc);
    }

}

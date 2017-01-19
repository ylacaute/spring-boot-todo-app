/**
 * Created by Yannick Lacaute on 19/01/17.
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
package com.thorpora.module.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public class AbstractRestControllerTest {

    @Test
    public void testURI() {
        String url = "http://localhost/api/todo/{todoId}/task/{id}";
        String todoId = "1";
        String id = "2";

        UriComponents comp = UriComponentsBuilder
                .fromHttpUrl(url)
                .buildAndExpand(todoId, id)
                .encode();

        //Assertions.assertThat(comp).isEqualToComparingFieldByFieldRecursively()


        // comp.to

        System.out.println("URI : " + comp.toString());
    }
}

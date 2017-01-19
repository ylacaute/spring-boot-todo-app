/**
 * Created by Yannick Lacaute on 22/12/16.
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
package com.thorpora.titi;

import com.thorpora.titi.properties.TitiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Inject
    private Environment env;

    /*
    @Inject
    private TitiProperties titiProperties;


    @RequestMapping()
    public String printProperty() {
        //UserDTO com.thorpora.user = new UserDTO(env.getProperty("sample.property"), reloadableProperties.sampleProperty);
        //UserDTO com.thorpora.user = new UserDTO(env.getProperty("sample.property"), titiProperties.getFirstName());
        return "Hello world, property : " + titiProperties.getFirstName();
    }
*/
}

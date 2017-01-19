/**
 * Created by Yannick Lacaute on 23/12/16.
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
package com.thorpora.module.user.exception;

import com.thorpora.module.core.annotation.ExceptionLog;
import com.thorpora.module.core.annotation.SilentException;
import com.thorpora.module.user.domain.User;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SilentException(value = false)
@ResponseStatus(value = HttpStatus.CONFLICT)
@ExceptionLog(level = Level.WARN, stackTrace = false)
public class UserConflictException extends RuntimeException {

    public UserConflictException(User user, String message) {
        super(String.format("User login '%s' is on conflict. {}.", user.getLogin(), message));
    }

}

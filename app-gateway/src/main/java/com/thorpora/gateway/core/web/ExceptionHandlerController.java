/**
 * Created by Yannick Lacaute on 26/12/16.
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
package com.thorpora.gateway.core.web;


import com.thorpora.module.core.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the classic application exception handler. Note that by default, you have nothing to do because
 * all {@link Exception} and {@Exception} are managed in {@link ErrorController}.
 * <p>
 * As the ErrorController is also defined with @ControllerAdvice and manage alk cases, we set this bean
 * with order{1} to getText the priority.
 */
@RestController
@ControllerAdvice
public class ExceptionHandlerController {

    private ErrorController errorController;

    public ExceptionHandlerController(ErrorController errorController) {
        this.errorController = errorController;
    }

    /**
     * Sample of a very specific exception handling use case. Be default, {@link ErrorController} would manage
     * this exception as a 409 since the {@link UserConflictException} is configured as so.
     * <p>
     * But here we would prefer verify something, and return a bad request to the client in a particular case,
     * without giving it the real reason of the exception in the DTO returned.
     */
//    @ExceptionHandler(UserConflictException.class)
//    public ResponseEntity<ErrorDTO> onUserConflict(HttpServletRequest request, HttpServletResponse response,
//                                                   UserConflictException ex) {
//
//        if (42 == (21 * 2)) {
//            return errorController.silentException(request, response, ex, HttpStatus.BAD_REQUEST);
//        } else {
//            return errorController.onException(request, response, ex);
//        }
//    }


}

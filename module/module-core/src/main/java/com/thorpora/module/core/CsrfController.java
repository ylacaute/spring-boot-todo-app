/**
 * Copyright (C) 2016 Mirakl. www.mirakl.com - info@mirakl.com
 * All Rights Reserved. Tous droits réservés.
 */
package com.thorpora.module.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/csrf")
public class CsrfController {

    /**
     * Used to reload with ajax the csrf token when expired
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    public String csrfToken(HttpServletRequest httpServletRequest) {
        //CsrfToken csrfToken = (CsrfToken) httpServletRequest.getAttribute("_csrf");
        //return csrfToken.getToken();
        return null;
    }
}

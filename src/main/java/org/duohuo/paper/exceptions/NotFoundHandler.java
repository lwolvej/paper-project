package org.duohuo.paper.exceptions;

import org.duohuo.paper.model.result.JsonResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotFoundHandler implements ErrorController {

    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public JsonResult handle404() {
        return new JsonResult(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name());
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

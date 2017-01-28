package com.simpleserver;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by villehietanen on 26/01/17.
 */

@Controller
public class SimpleController {
    @RequestMapping("/string")
    public String index() {
        return "string index";
    }

}


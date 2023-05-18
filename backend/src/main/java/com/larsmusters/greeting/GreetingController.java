package com.larsmusters.greeting;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @MessageMapping("/next")
    @SendTo("/topic/next")
    public boolean pingNextTurn() throws Exception {
        return true;
    };

}
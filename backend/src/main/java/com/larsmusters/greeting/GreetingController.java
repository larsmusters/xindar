package com.larsmusters.greeting;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    // Message mapping comes from the websocket directly (when a message is sent to /ws/next)
    @MessageMapping("/next")
    // SendTo is just to which subscribers.
    @SendTo("/topic/next")
    public boolean pingNextTurn() throws Exception {
        return true;
    };

}
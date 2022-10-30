package com.ssapidocs.backend.api.controller;

import com.ssapidocs.backend.api.dto.SocketDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @MessageMapping("/receive")

    @SendTo("/send")

    public SocketDto SocketHandler(SocketDto socketDto){

        String userName = socketDto.getUserName();
        String content = socketDto.getContent();

        SocketDto result = new SocketDto(userName,content);

        return result;
    }
}

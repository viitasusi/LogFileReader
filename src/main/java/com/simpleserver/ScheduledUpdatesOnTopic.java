package com.simpleserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by villehietanen on 26/01/17.
 */
@Component
public class ScheduledUpdatesOnTopic{
    private LogReader r = new LogReader();

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedDelayString="${monitorinInterval}")
    public void publishUpdates(){
        try {
            r.readLogFile();
        } catch(IOException ie) {
            System.out.println("error, reading the log failed");
        }
        template.convertAndSend("/topic/errors", new ErrorMessage(formatLogMessage()));
        r.resetValues();
    }

    private String formatLogMessage() {
        return "info: " + r.getInfo() + "<br>errors: " + r.getErrors().toString() + "<br>warnings: " + r.getWarnings().toString();
    }
}

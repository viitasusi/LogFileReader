package com.simpleserver;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.simpleserver.logreader.LogReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.plugin.javascript.JSObject;

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
        JSONObject o = new JSONObject();
        try {
            o.put("info", r.getInfo());
            o.put("errors", r.getErrors());
            o.put("warnings", r.getWarnings());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return o.toString();

        //return "info: " + r.getInfo() + "<br>errors: " + r.getErrors().toString() + "<br>warnings: " + r.getWarnings().toString();
    }
}

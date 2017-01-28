package com.simpleserver;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by villehietanen on 26/01/17.
 */
@Component
public class LogReader {
    private Integer warnings = 0;
    private Integer errors = 0;
    private Integer info = 0;

    @Value("${logMessageHistory}")
    private int logMessageHistory;

    public void setWarnings(Integer warnings) {
        this.warnings = warnings;
    }

    public void setErrors(Integer errors) {
        this.errors = errors;
    }

    public void setInfo(Integer info) {
        this.info = info;
    }

    public Integer getWarnings() {
        return warnings;
    }

    public Integer getErrors() {
        return errors;
    }

    public Integer getInfo() {
        return info;
    }

    @Autowired
    public LogReader() {
    }

    public void resetValues() {
        this.warnings = 0;
        this.errors = 0;
        this.info = 0;
    }

    @PostConstruct
    public void readLogFile() throws IOException {
        String logFile = "./src/main/java/com/simpleserver/file.txt";
        ReversedLinesFileReader fr = new ReversedLinesFileReader(new File(logFile));
        String line;
        Timestamp t = new Timestamp(System.currentTimeMillis());
        line = fr.readLine();
        while (line != null && isRowIncluded(t, line, logMessageHistory)) {

            if (line != null) {
                if(line.contains("INFO")) {
                    info++;
                } else if (line.contains("WARNING")) {
                    warnings++;
                } else if (line.contains("ERROR")) {
                    errors++;
                }
                line = fr.readLine();
            }
        }
        fr.close();
    }

    private static boolean isRowIncluded(Timestamp timestamp, String line, int mo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        Date tempDate = null;
        Timestamp t = null;
        Pattern p = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d");
        Matcher m = p.matcher(line);

        if(m.find())
        {
            try {tempDate = sdf.parse(m.group()); t = new Timestamp(tempDate.getTime());} catch (ParseException e) {}
        }
        if (t != null && timestamp.getTime() - t.getTime() <= mo) {
            return true;
        } else {
            return false;
        }

    }

}

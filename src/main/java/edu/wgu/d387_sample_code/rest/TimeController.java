package edu.wgu.d387_sample_code.rest;

import edu.wgu.d387_sample_code.model.TimeResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class TimeController {
    //Set Presentation time
    private static final ZonedDateTime presentationTime = LocalDateTime.of(2026, 10, 15, 20, 0).atZone(ZoneId.systemDefault());

    @RequestMapping(path = "/time", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeResponse getTime(){
        //Create the time DTO
        TimeResponse time = new TimeResponse();

        //Set the Date
        time.setYear(String.valueOf(presentationTime.getYear()));
        time.setMonth(presentationTime.format(DateTimeFormatter.ofPattern("MMMM")));
        time.setDay(String.valueOf(presentationTime.getDayOfMonth()));

        //Set the timezones
        time.setEt(timeZone("America/New_York"));
        time.setMt(timeZone("America/Denver"));
        time.setUtc(timeZone("UTC"));
        return time;
    }

    private String timeZone(String tz){
        ZoneId zone = ZoneId.of(tz);
        ZonedDateTime zonedDateTime = presentationTime.withZoneSameInstant(zone);
        return zonedDateTime.format(DateTimeFormatter.ofPattern("h:mm a z"));
    }
}

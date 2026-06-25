package edu.wgu.d387_sample_code.rest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newFixedThreadPool;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class WelcomeController {
    @RequestMapping(path = "/welcome", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getWelcome() throws InterruptedException {
        ExecutorService welcomeExecutor=newFixedThreadPool(2);
        List<String> welcomeList = new ArrayList<>();
        welcomeExecutor.execute(() -> {
            welcomeList.add(getLanguage("welcome_en_CA.properties"));
        });
        welcomeExecutor.execute(() -> {
            welcomeList.add(getLanguage("welcome_fr_CA.properties"));
        });
        welcomeExecutor.shutdown();
        welcomeExecutor.awaitTermination(5, TimeUnit.SECONDS);
        return welcomeList;
    }

    private String getLanguage(String languageProperty){
        Properties properties = new Properties();
        try {
            InputStream stream = new ClassPathResource(languageProperty).getInputStream();
            properties.load(stream);
            return properties.getProperty("welcome");
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
}

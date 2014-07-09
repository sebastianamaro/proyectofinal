package com.example;

import android.os.StrictMode;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;

public class TuttiFruttiAPI {

    private String serverURL;

    public TuttiFruttiAPI(String serverURL){

        this.serverURL=serverURL;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    public void createGame(boolean gameMode, boolean opponentsMode, boolean categoriesMode, String registrationId)
    {
        String url=serverURL+"game";/* object.body tiene que tener status=Playing*/
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new CustomErrorHandler());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        Game g= new Game();
        g.setPlayer(registrationId);

        if(gameMode)
            g.setMode("ONLINE");
        else
            g.setMode("OFFLINE");

        if(opponentsMode)
            g.setOpponentsType("RANDOM");
        else
            g.setOpponentsType("FRIENDS");

        if(categoriesMode)
            g.setCategoriesType("FIXED");
        else
            g.setCategoriesType("FREE");
        ResponseEntity<String> resp=null;
        try {
           resp=restTemplate.postForEntity(url, g, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startRound(int gameId)
    {
            String url=serverURL+"game/"+gameId+"/round";/* object.body tiene que tener status=Playing*/
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            FullRound fr= new FullRound();
            fr.setStatus("PLAYING");
            restTemplate.put(url,fr);
    }

    public void finishRound(int gameId, int roundId, String playerId, Date startTimeStamp, Play[] plays)
    {
        String url= serverURL+"game/"+gameId+"/round"; /*Aca se tiene que mandar status=Closed y SI O SI RoundId*/
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        PlayedRound pr= new PlayedRound();
        pr.setStatus("CLOSED");
        pr.setRoundId(roundId);

        RoundLine rl= new RoundLine();
        rl.setPlayer(playerId);
        rl.setStartTimestamp(startTimeStamp);
        rl.setPlays(plays);

        pr.setLine(rl);
        restTemplate.put(url,pr);

    }

    public FullRound getCurrentRoundInformation(int gameId)
    {
        String url= serverURL+"game/"+gameId+"/round";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        return restTemplate.getForObject(url,FullRound.class);
    }

}


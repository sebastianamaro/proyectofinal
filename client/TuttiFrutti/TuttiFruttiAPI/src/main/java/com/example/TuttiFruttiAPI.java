package com.example;

import android.os.StrictMode;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class TuttiFruttiAPI {

    private String serverURL;

    public TuttiFruttiAPI(String serverURL){

        this.serverURL=serverURL;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void createGame(boolean gameMode, boolean opponentsMode, boolean categoriesMode)
    {
        String url=serverURL+"game";/* object.body tiene que tener status=Playing*/
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        Game g= new Game();

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


        restTemplate.postForObject(url,g,null);
    }

    public void startRound(int gameId)
    {
            String url=serverURL+"game/"+gameId+"/round";/* object.body tiene que tener status=Playing*/
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            FullRound fr= new FullRound();
            fr.setGameId(gameId);
            fr.setStatus("PLAYING");
            restTemplate.put(url,fr);


    }

    public void endRound(int gameId, int roundId)
    {
        String url= serverURL+"game/"+gameId+"/round"; /*Aca se tiene que mandar status=Closed y SI O SI RoundId*/
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        FullRound fr= new FullRound();
        fr.setGameId(gameId);
        fr.setRoundId(roundId);
        fr.setStatus("CLOSED");
        restTemplate.put(url,fr);

    }

    public FullRound getCurrentRoundInformation(int gameId)
    {
        String url= serverURL+"game/"+gameId+"/round";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        return restTemplate.getForObject(url,FullRound.class);
    }

}


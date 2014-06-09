package com.example;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class TuttiFruttiAPI {

    private String serverURL;

    public TuttiFruttiAPI(String serverURL){
        this.serverURL=serverURL;
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

    public FullRound getRoundInformation(int gameId, int roundId)
    {
        String url= serverURL+"game/"+gameId+"/round";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        return restTemplate.getForObject(url,FullRound.class);
    }

}


package com.example;

import android.os.StrictMode;

import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.FullGame;
import com.example.TuttiFruttiCore.FullRound;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.GameScoreSummary;
import com.example.TuttiFruttiCore.InvitationResponse;
import com.example.TuttiFruttiCore.Line;
import com.example.TuttiFruttiCore.Play;
import com.example.TuttiFruttiCore.PlayedRound;
import com.example.TuttiFruttiCore.Player;
import com.example.TuttiFruttiCore.PlayerRoundScoreSummary;
import com.example.TuttiFruttiCore.RoundScoreSummary;
import com.example.TuttiFruttiCore.UserGame;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TuttiFruttiAPI {

    private String serverURL;

    public TuttiFruttiAPI(String serverURL){

        this.serverURL=serverURL;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    public ArrayList<UserGame> getGames(String fbId)
    {
        ArrayList<UserGame> games;

        String url= serverURL+"player/"+fbId+"/game";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        UserGame[] gamesArray= restTemplate.getForObject(url,UserGame[].class);

        games=new ArrayList<UserGame>(Arrays.asList(gamesArray));

        return games;
    }

    public Game getGame(int gameId)
    {
        String url= serverURL+"game/"+gameId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        Game game= restTemplate.getForObject(url,Game.class);
        return game;
    }

    public ArrayList<FullGame> getPendingInvitations(String registrationId){
        ArrayList<FullGame> invitations;

        String url= serverURL+"player/"+registrationId+"/invitations";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        FullGame[] invitationsArray= restTemplate.getForObject(url,FullGame[].class);

        invitations=new ArrayList<FullGame>(Arrays.asList(invitationsArray));

        return invitations;
    }

    public void respondInvitation(int gameId, InvitationResponse response){

        String url= serverURL+"game/"+gameId+"/invitation";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.put(url,response);

    }

    public void deleteFinishedGame (String playerId,  int gameId)
    {
        String url= serverURL+"/player/"+playerId+"/game/"+gameId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.delete(url);
    }


    public void createGame(Game game)
    {
        String url=serverURL+"game";/* object.body tiene que tener status=Playing*/
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new CustomErrorHandler());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<String> resp=null;
        try {
           resp=restTemplate.postForEntity(url, game, null);
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
        fr.setStatus("OPENED");
        restTemplate.put(url, fr);
    }

    public GameScoreSummary getGameScores(int gameId)
    {
        String url= serverURL+"game/"+gameId+"/scores";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        return restTemplate.getForObject(url,GameScoreSummary.class);
    }

    public void finishRound(int gameId, int roundId, String playerFbId, String regId ,Date startTimeStamp, ArrayList<Play> plays)
    {
        String url= serverURL+"game/"+gameId+"/round"; /*Aca se tiene que mandar status=Closed y SI O SI RoundId*/
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        PlayedRound pr= new PlayedRound();
        pr.setStatus("CLOSED");
        pr.setRoundId(roundId);

        Player p=new Player();
        p.setFbId(playerFbId);
        p.setRegistrationId(regId);

        Line rl= new Line();
        rl.setPlayer(p);
        rl.setTimestamp(startTimeStamp);
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

    public PlayerRoundScoreSummary getRoundScore(int gameId, String fbId)
    {
        String url= serverURL+"game/"+gameId+"/round/scores/for/"+fbId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        return restTemplate.getForObject(url,PlayerRoundScoreSummary.class);
    }

    public void AddPlayer(Player newPlayer)
    {
        String url= serverURL+"player";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        restTemplate.postForEntity(url, newPlayer, null);
    }

    public void reportCategory(int categoryId){

        String url= serverURL+"category/"+ categoryId+"?report";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.put(url, null);
    }

    public void starCategory(String playerId, int categoryId){

        String url= serverURL+"player/"+ playerId+"/category/"+categoryId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.put(url, null);
    }

    public ArrayList<Category> getCategories()
    {
        String url= serverURL+"category";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        Category[] lineArray= restTemplate.getForObject(url,Category[].class);
        return new ArrayList<Category>(Arrays.asList(lineArray));

    }

    public ArrayList<Category> getStaredCategories(String playerId)
    {
        String url= serverURL+"player/"+ playerId+"/category";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        Category[] lineArray= restTemplate.getForObject(url,Category[].class);
        return new ArrayList<Category>(Arrays.asList(lineArray));

    }

    public ArrayList<Category> getFixedCategories()
    {
        String url= serverURL+"category?isFixed=1";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        Category[] lineArray= restTemplate.getForObject(url,Category[].class);
        return new ArrayList<Category>(Arrays.asList(lineArray));

    }
}


module.exports = function(app) {
 
  var Game = require('../models/game.js');
  var Round = require('../models/round.js');
  var Player = require('../models/player.js');
  var Play = require('../models/play.js');

  setQualification = function(req,res){
    var statusRequired = new Game().getStatus().WAITING_FOR_QUALIFICATIONS;
    Game.findOne({ 'gameId': req.params.id, status: statusRequired }, function (err, game){
      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);
      
      var round = game.getLastRound();
      
      round.setQualification(req.params.fbId, req.body.category, req.body.isValid, req.body.judgedPlayer);
      game.moveToWaitingForNextRoundIfPossible(round, function(){

        game.save(function(err) {
          if(!err) {
            console.log('Qualification set ');
            res.send('Qualification set', 200);   
          } else {
            console.log('ERROR: ' + err);
            res.send('ERROR: ' + err,500);   
          }
        });
      });
    })
  }
  getRound = function(req, res) {
    var statusRequired = new Game().getStatus().WAITING_FOR_PLAYERS;
    Game.findOne({ 'gameId': req.params.id, status: { $ne: statusRequired } }, function (err, game){
      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);
      
      var playingRound = game.getPlayingRound();
      if (!playingRound) return res.send('No playing round', 404);
      var roundWithCategories = playingRound.asSummarized(game);
      res.send(roundWithCategories);      
    })
  }
  alterRound = function(req, res) {
    var statusRequired = req.body.status;
    if (statusRequired == new Round().getStatus().OPENED)
      startRound(req,res);
    else if (statusRequired == new Round().getStatus().CLOSED)
      finishRound(req,res);
    else
      res.send('Incomplete request',500);      
  }
  startRound = function(req, res) {
    var statusRequired = new Game().getStatus().WAITING_FOR_PLAYERS;
    Game.findOne({ 'gameId': req.params.id , status: { $ne: statusRequired }}, function (err, game){
      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);
      
      var playingRound = game.getPlayingRound();
      var reqRound = req.body;

      if (playingRound){
          console.log('Return existing round with letter '+playingRound.letter);
      } else {
        var assignedLetter = game.getNextLetter();
        var round = new Round();
        round.start(assignedLetter);
        game.addRound(round);
        game.status = game.getStatus().PLAYING;
        game.save(function(err) {
          if(!err) {
            console.log('Created round with letter '+assignedLetter);
          } else {
            console.log('ERROR: ' + err);
          }
        });
      }

      res.send('Round started', 200);
    });
  }
  finishRound = function(req, res) {
    console.log('finishing round');
    var statusRequired = new Game().getStatus().WAITING_FOR_PLAYERS;
    Game.findOne({ 'gameId': req.params.id , status: { $ne: statusRequired }}, function (err, game){
      var reqRound = req.body;

      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);
      
      var currentRound = game.getRound(reqRound.roundId);

      if (!currentRound) return res.send('Round not found', 404);

      if (currentRound.hasPlayerSentHisLine(reqRound.line.player.fbId)) {
        console.log('player: ' +reqRound.line.player+' has already sent his line.')
        return res.send('Added line to round', 200);
      }; 
      console.log("The player who stopped is "+reqRound.line.player.fbId);
      
      Player.findOne({ 'fbId': reqRound.line.player.fbId }, function (err, foundPlayer){
            if (err) return res.send(err, 500);
            if (!foundPlayer) return res.send('Player not found', 404);
          
          game.sendNotificationsRoundFinished(currentRound, reqRound.line.player.fbId, function(err){
            currentRound.addLine(reqRound.line, foundPlayer);
            currentRound.finishIfAllPlayersFinished(game);
            console.log(currentRound.status+" is the round status");
            if (game.getCategoriesType().FREE == game.categoriesType){
              game.status = game.getStatus().WAITING_FOR_QUALIFICATIONS;
            } else {
              if (currentRound.status == currentRound.getStatus().CLOSED){  
                   game.status = game.getStatus().WAITING_FOR_NEXT_ROUND;
              }
            } 
            
            game.moveToWaitingForNextRoundIfPossible(currentRound, function(){  //VER DE MOVER LO QUE ESTA ARRIBA, ADENTRO DE ESTE METODO
              game.save(function(err) {
                if(!err) {
                  console.log('game.status '+ game.status);
                  console.log('Finished round');
                } else {
                  console.log('ERROR: ' + err);
                }
              });    
            }); // solo sirve si son todas controladas aunque eligio free
          });
          res.send('Round finished', 200);
      });
        
    });
  }
  createGame = function(req,res){
    Game.findOne({}).sort('-gameId').exec(function(err, doc) { 
      var largerId;
      if (doc){
        largerId = doc.gameId + 1;  
      } else {
        largerId = 1;
      }
      var game = new Game();
      game.gameId = largerId;
      game.status = game.getStatus().WAITING_FOR_PLAYERS;
      game.setValues(req.body);
      game.save(function(err) {
        if(!err) {
          console.log('Created game with gameId '+largerId);

          game.addPlayer(req.body.owner);
          
          
        } else {
          console.log('ERROR en createGame: ' + err);
        }
      });
      return res.send('Game started with gameId '+largerId, 200);
     });
  }
  respondInvitation = function(req, res){
    var statusRequired = new Game().getStatus().WAITING_FOR_PLAYERS;
    Game.findOne({ 'gameId': req.params.id , status: statusRequired}, function (err, game){
      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);
            
      var response = req.body.response;
      if (response == 'ACCEPTED'){
        game.acceptInvitation(req.body, function(err){
          if (err){
            console.log(err);
            res.send('Invitation cant be accepted',500);
          } else{
            res.send('Invitation accepted',200);
          }
        });
      } else if (response == 'REJECTED'){
        game.rejectInvitation(req.body, function(err){
          if (err){
            console.log(err);
          } 
          res.send('Invitation rejected',200);
        });
      } else{
        res.send('Incomplete request',500);
      }

    });
  }

  getRoundScores = function(req, res){
    var statusRequired = new Game().getStatus().WAITING_FOR_PLAYERS;
    Game.findOne({ 'gameId': req.params.id , status: { $ne: statusRequired }}, function (err, game){
      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);          
      
      var roundToShow = game.getPlayingRound();
      var showScores;
      var playersWhoHaveLines;
      var canPlayerPlay;
      var isComplete;

      if (roundToShow == undefined)
      {
        console.log('no hay ronda abierta');
        //si todavia no hay actual, mostrame los resultados de la anterior que ya esta cerrada
        roundToShow = game.getLastRound();
        showScores = true;
        playersWhoHaveLines = game.players;
        canPlayerPlay = game.status !== game.getStatus().WAITING_FOR_QUALIFICATIONS; //quizas no hay ronda abierta pero la anterior todavia no tiene las calificaciones
        isComplete = game.status !== game.getStatus().WAITING_FOR_QUALIFICATIONS; //quizas no hay ronda abierta pero la anterior todavia no tiene las calificaciones
      }
      else if (!roundToShow.hasPlayerSentHisLine(req.params.fbId))
      {
        console.log('hay ronda abierta y no jugue');
        //si la actual todavia no la jugue, mostrame los resultados de la anterior que ya esta cerrada
        roundToShow = game.getRound(roundToShow.roundId -1);
        showScores = true;
        playersWhoHaveLines = game.players;
        canPlayerPlay = true; //va a jugar la actual
        isComplete = true;
      }
      else
      {
        console.log('hay ronda abierta y SI jugue');
        //si ya jugue pero no esta cerrada, mostrame solo las jugadas de la actual abierta
        showScores = false; //si hay una abierta es porque todos todavia no mandaron sus lines
        playersWhoHaveLines = roundToShow.getPlayersWhoHavePlayed(); //mostrar solo las jugadas de los que si mandaron su line
        console.log('playersWhoHaveLines: ' + playersWhoHaveLines);
        canPlayerPlay = false;
        isComplete = false;
      }

      if (!roundToShow || roundToShow == undefined) return res.send('No round to show results of', 404);

      console.log("can player play: " + canPlayerPlay);
      
      var scoresArray=roundToShow.getScores(req.params.fbId, game.categories, playersWhoHaveLines, showScores);
      
      //roundNumber: para mostrar a que ronda pertenecen los resultados
      //isComplete: para saber si muestra con scores o sin (si es parcial)
      //canPlayerPlay: para saber si el usuario puede jugar la proxima ronda o todavía no
      var roundScoresResult = { 
                            'roundNumber':roundToShow.roundId,
                            'isComplete':isComplete,
                            'canPlayerPlay': canPlayerPlay,
                            'roundScoreSummaries': scoresArray };

      res.send(roundScoresResult, 200);            
     });
  }
  getGamesForPlayer = function(req, res) {
    var numId =  parseInt(req.params.id);
    
    Player.findOne({ 'fbId': req.params.id }, function (err, player){
      if (err) return res.send(err, 500);
      if (!player) return res.send('Player not found', 404);     

      res.send(player.getGames(), 200); //add error manipulation
    });
  } 
  getGameScores = function(req,res){
    Game.findOne({ 'gameId': req.params.id }, function (err, game){
      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);          
      var roundResults = game.getRoundResults();
      var gameScoresMap = { 'playersName': game.getPlayerNames(),
                            'roundsResult': roundResults,
                            'playerResult': game.getPlayerResults(roundResults) };
      res.send(gameScoresMap, 200);            
     });
  }
  getGame = function(req, res) {
    Game.findOne({ 'gameId': req.params.id}, function (err, game){
      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);
     
      res.send(game.asSummarized(),200);      
    })
  }
}
module.exports = function(app) {
 
  var Game = require('../models/game.js');
  var Round = require('../models/round.js');
  var FullRound = require('../models/fullRound.js');
  var Player = require('../models/player.js');
  var Play = require('../models/play.js');

  getRound = function(req, res) {
    Game.findOne({ 'gameId': req.params.id , status: 'PLAYING'}, function (err, game){
      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);
      
      var playingRound = game.getPlayingRound();
      
      if (!playingRound) return res.send('No playing round', 404);
      
      var roundWithCategories = new FullRound();
      roundWithCategories.fillData(game, playingRound);
      res.send(roundWithCategories);      
    })
  }
  alterRound = function(req, res) {
    var statusRequired = req.body.status;
    if (statusRequired == 'PLAYING')
      startRound(req,res);
    else if (statusRequired == 'CLOSED')
      finishRound(req,res);
    else
      res.send('Incomplete request',500);      
  }
  startRound = function(req, res) {
        Game.findOne({ 'gameId': req.params.id , status: 'PLAYING'}, function (err, game){
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
        Game.findOne({ 'gameId': req.params.id , status: 'PLAYING'}, function (err, game){
          var reqRound = req.body;
          if (err) return res.send(err, 500);
          if (!game) return res.send('Game not found', 404);
          
          var currentRound = game.getRound(reqRound.roundId);

          if (!currentRound) return res.send('Round not found', 404);

          if (currentRound.hasPlayerSentHisLine(reqRound.line.player)) {
            return res.send('Added line to round', 200);
          }; 

          game.sendNotifications(currentRound, reqRound.line.player.registrationId, function(err){
            currentRound.addLine(reqRound.line);
            
            if (currentRound.checkAllPlayersFinished(game)){
              currentRound.finish(game);
            }

            game.save(function(err) {
              console.log("pasa por save");
              if(!err) {
                console.log('Finished round');
              } else {
                console.log('ERROR: ' + err);
              }
            });
          });
          res.send('Round finished', 200);
            
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
      game.status = "PLAYING";
      game.categories = ["ANIMALES", "COLORES"];
      game.setValues(req.body);
      game.save(function(err) {
        if(!err) {
          console.log('Created game with gameId '+largerId);
        } else {
          console.log('ERROR: ' + err);
        }
      });
      return res.send('Game started with gameId '+largerId, 200);
     });
  }
  getRoundScores = function(req, res){
    Game.findOne({ 'gameId': req.params.id , status: 'PLAYING'}, function (err, game){
      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);          
      var currentRound = game.getRound(req.params.roundId);
      if (!currentRound) return res.send('Round not found with roundId '+req.params.roundId, 404);
      //if (!currentRound.isFinished()) return res.send('Round is not yet finished', 403);
      res.send(currentRound.lines, 200);            
     });
  }
  getGamesForPlayer = function(req, res) {
    var numId =  parseInt(req.params.id);
    console.log(numId);
    Player.findOne({}, function (err, player){
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
      var gameScoresMap = { 'players': game.getPlayerNames(),
                            'roundsResult': roundResults,
                            'playerResult': game.getPlayerResults(roundResults) };
      res.send(gameScoresMap, 200);            
     });
  }
}
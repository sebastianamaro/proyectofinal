module.exports = function(app) {
 
  var Game = require('../models/game.js');
  var Round = require('../models/round.js');
  var FullRound = require('../models/fullRound.js');
  
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
      res.send('No action selected'+req.body+"-----"+req.body.status,500);      
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
            
            //if (!assignedLetter) finishGame();
            
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

          if (currentRound.hasLineOfPlayer(reqRound.line.player)) {
            return res.send('Round finished', 200)
          }; 

          currentRound.addLine(reqRound.line);
          game.save(function(err) {
            if(!err) {
              console.log('Finished round');
            } else {
              console.log('ERROR: ' + err);
            }
          });
          game.sendNotifications(currentRound, function(err){
            if(err) {
              console.log('ERROR: ' + err);
            } else {
              console.log('Notifications sent');
            }
          });
          if (currentRound.checkAllPlayersFinished(game)){
            currentRound.calculateScores();
            currentRound.save(function(err) {
            if(!err) {
              console.log('Finished round');
            } else {
              console.log('ERROR: ' + err);
            }
          });
          }
          res.send('Round finished', 200);
        })
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
      game.categories = ["ANIMALES", "COLORES", "LUGARES", "FRUTAS", "MARCAS DE AUTO"];
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
  sendNotifications = function(req, res) {
        Game.findOne({ 'gameId': req.params.id , status: 'PLAYING'}, function (err, game){
          game.sendNotifications(function(err){
            if(err) {
              console.log('ERROR: ' + err);
            } else {
              console.log('Notifications sent');
            }
          });
          
          //check if all finished---> count points

          res.send('Notifications sent', 200);
        })
    }
}
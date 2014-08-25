module.exports = function(app) {
 
  var Game = require('../models/game.js');
  var Round = require('../models/round.js');
  var FullRound = require('../models/fullRound.js');
  var Player = require('../models/player.js');

  getGamesForPlayer = function(req, res) {
    Player.findOne({ registrationId: req.params.id }, function (err, player){
    	console.log(player);
      	if (err) return res.send(err, 500);
      	if (!player) return res.send('Player not found', 404);   
      	console.log(player);
      	Game.find({ gameId: { $in : player.games } }, function(err, games) {
	        var gamesToReturn = [];
	        if (games){
		        for (var i = games.length - 1; i >= 0; i--) {
		        	var game = games[i];
		        	gamesToReturn.push({"gameId":game.gameId, "status": game.status});
		        };
	        }
	      	res.send(gamesToReturn, 200); //add error manipulation
	    });
    });
  }

  getInvitationsForPlayer = function(req, res) {
      Player.findOne({ registrationId: req.params.id }, function (err, player){
          if (err) return res.send(err, 500);
          if (!player) return res.send('Player not found', 404);   

          Game.find({ gameId: { $in : player.invitations } }, function(err, games) {
          var gamesToReturn = [];
          if (games){
            for (var i = games.length - 1; i >= 0; i--) {
              var game = games[i];

              gamesToReturn.push(game.asSummarized());
            };
          }

          res.send(gamesToReturn, 200); //add error manipulation
          });

      });
    }

    createPlayer = function(req, res)
    {
        console.log('createPlayer');
          var player = new Player();
          player.setValues(req.body);

          player.save(function(err) {
            if(!err) {
              console.log('Player '+player.getName()+' inserted');
            } else {
              console.log('ERROR en createPlayer: ' + err);
            }
          });

          return res.send('Player '+player.getName()+' inserted', 200);
    }
}
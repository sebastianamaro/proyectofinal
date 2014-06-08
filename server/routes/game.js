module.exports = function(app) {
 
  var Game = require('../models/game.js');
  var Round = require('../models/round.js');
  var FullRound = require('../models/fullRound.js');

  getRound = function(req, res) {
    Game.findOne({ 'gameId': req.params.id , status: 'PLAYING'}, function (err, game){
      if (err) return res.send(err, 500);
      if (!game) return res.send('Game not found', 404);
      
      var playingRound = game.rounds.filter(function (round) {
          return round.status === 'PLAYING';
        }).pop();
      
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
          
          var playingRound = game.rounds.filter(function (round) {return round.status === 'PLAYING';}).pop();

          if (playingRound){
              console.log('Return existing round with letter '+playingRound.letter);
          } else {
            var letters = ['A','B','C','D','E','F'];
            var usedLetters = [];

            game.rounds.reduce(function(previousRound, currentRound, index, array){
                return usedLetters.push(currentRound.letter);
            }, usedLetters);
            
            for(var iLetter = letters.length - 1; iLetter >= 0; iLetter--) {
                if( usedLetters.indexOf(letters[iLetter]) != -1 ) {
                   letters.splice(iLetter, 1);
                }
            }

            if (letters.length == 0){ //no more letters!
              // finishGame(req,res);
            }
            var assignedLetter = letters[Math.floor(Math.random() * letters.length)];
            var round = new Round();
            round.start(game, assignedLetter);

            game.rounds.push(round);

            game.save(function(err) {
              if(!err) {
                console.log('Created game with letter '+assignedLetter);
              } else {
                console.log('ERROR: ' + err);
              }
            });
            playingRound = round;
        }
        res.send('Round started', 200);
    });
  }

  finishRound = function(req, res) {
        Game.findOne({ 'gameId': req.params.id , status: 'PLAYING'}, function (err, game){
          if (err) return res.send(err, 500);
          if (!game) return res.send('Game not found', 404);
          
          var playingRound = game.rounds.filter(function (round) {
              return round.roundId === req.body.roundId && round.status === 'PLAYING';
            }).pop();
          
          if (!playingRound) return res.send('Round not found', 404);

          game.rounds[playingRound.roundId - 1] = playingRound.finish();

          game.save(function(err) {
            if(!err) {
              console.log('Finished round');
            } else {
              console.log('ERROR: ' + err);
            }
          });
          res.send('Round finished', 200);
        })
    }
} 
module.exports = function(app) {
 
  var Game = require('../models/game.js');

  alterRound = function(req, res) {
        startRound(req,res);
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

            if (letters.length == 0){
              //TODO finalizar partida
            }
            var assignedLetter = letters[Math.floor(Math.random() * letters.length)];

            var round = {
                  roundId: game.rounds.length + 1,
                  status: 'PLAYING',
                  letter:   assignedLetter,
                  startTimestamp:  new Date()
                };
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
        var roundWithCategories = {
          gameId: game.gameId,
          categories: game.categories,
          roundId: playingRound.roundId,
          letter: playingRound.letter,
          startTimestamp: playingRound.startTimestamp
        }
        res.send(roundWithCategories);
    });
}

}
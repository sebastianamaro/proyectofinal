var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;
var Round = require('./round.js');
var Player = require('./player.js');
var NotificationFile = require('./notification.js');

var gameSchema = new Schema({
  	gameId:   { type: Number },
  	startTimestamp: { type: Date }, 
  	rounds: [Round.schema],
  	status: { type: String },
  	categories: [ { type: String } ],
    mode : { type: String },
    categoriesType: { type: String },
    oponentsType: { type: String },
    players: [ Player.schema ],
    randomPlayersCount: { type: Number }
});

gameSchema.methods.getRound = function getRound(roundId) {
	var round = this.rounds.filter(function (round) {
              return round.roundId == roundId;
            }).pop();
	return round;
 }

gameSchema.methods.getPlayingRound = function getPlayingRound(){
  return this.rounds.filter(function (round) {return round.isPlaying(); }).pop();
}

gameSchema.methods.getNextLetter = function getNextLetter(){
  var letters = ['A','B','C','D','E','F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X','Y', 'Z'];
  var usedLetters = [];

  this.rounds.reduce(function(previousRound, currentRound, index, array){
      return usedLetters.push(currentRound.letter);
  }, usedLetters);
  
  for(var iLetter = letters.length - 1; iLetter >= 0; iLetter--) {
      if( usedLetters.indexOf(letters[iLetter]) != -1 ) {
         letters.splice(iLetter, 1);
      }
  }

  var assignedLetter = letters[Math.floor(Math.random() * letters.length)];
  return assignedLetter;
}

gameSchema.methods.addRound = function addRound(round){
  round.roundId = this.rounds.length + 1;
  this.rounds.push(round);
}

gameSchema.methods.setValues = function setValues(game){
  this.mode = game.mode;
  this.categoriesType = game.categoriesType;
  this.oponentsType = game.oponentsType;
  this.randomPlayersCount = game.randomPlayersCount;
  this.addPlayer(game.player);
}

gameSchema.methods.addPlayer = function addPlayer(registrationId){
  var gameId = this.gameId;
  Player.findOne({ 'registrationId': registrationId}, function (err, foundPlayer){
    if (!foundPlayer){
      foundPlayer = new Player();
    }
    //TODO validate it doesn't already exist in game
    foundPlayer.setValues(registrationId);
    foundPlayer.games.push(gameId);
    foundPlayer.save(function(err) {
          if(!err) {
            console.log('Inserted new player with registrationId '+foundPlayer.registrationId);
          } else {
            console.log('ERROR: ' + err);
          }
        });
  });
}

gameSchema.methods.sendNotifications = function sendNotifications(round, registrationId, callback){
    console.log("entra a send");
  for (var i = this.players.length - 1; i >= 0; i--) {
    var player = this.players[i];
    if (player.registrationId == registrationId){
      console.log("Es el player que corto entonces no lo mando "+player.registrationId);
      continue;
    }
    if (round.hasLineOfPlayer(player)) { // if it has a line of the player it means he has already sent me his line OR i have sent him notification
      console.log("Ya tiene line el player entonces no lo mando "+player.registrationId);
      continue;
    }
    console.log("Voy a mandarle al player "+player.registrationId);

    var notification = new Notification();
    notification.setRegistrationId(player.registrationId);
    notification.setValues({'gameId':this.gameId, 'roundId':round.roundId, 'status' : 'FINISHED'});
    notification.send(function(err){
      if (err){
        console.log("Error when sendNotifications");
        callback(new Error("Error when sendNotifications"));
      } else {
        round.setNotificationSentForPlayer(player);
      }
    });
  }
  callback();
}
gameSchema.methods.getPlayerNames = function(){
  var playerNames = [];
  for (var i = this.players.length - 1; i >= 0; i--) {
    playerNames.push( this.players[i].registrationId.substr(0,4));
  };
  return playerNames;
}
gameSchema.methods.getRoundResults = function(){
  var roundResults = [];
  for (var i = this.rounds.length - 1; i >= 0; i--) {
    var aRound = this.rounds[i];
    roundResults.push( {
                      'roundId' : aRound.roundId, 
                      'scores' : aRound.getScoresForPlayers(this.players)
                    });
  };
  return roundResults;
}
gameSchema.methods.getPlayerResults = function(partialScores){
  var playerPartialResults = [];
  for (var iPartialScore in partialScores){
    var partialScore = partialScores[iPartialScore];
    for (var i = partialScore.scores.length - 1; i >= 0; i--) {
      if (playerPartialResults[i] == undefined){
        playerPartialResults[i] = partialScore.scores[i].score;
      } else {
        playerPartialResults[i] += partialScore.scores[i].score;
      }
    };
  }
  var playerResults = [];
  var bestScore = 0 ;
  for (var i = playerPartialResults.length - 1; i >= 0; i--) {
    var playerPartialResult = playerPartialResults[i];
    if (playerPartialResult > bestScore){
      bestScore = playerPartialResult;
    } 
    playerResults.push({'score': playerPartialResult, "best": false});
  };
  
  for(var iPlayerResult in playerResults){
    if (playerResults[iPlayerResult].score == bestScore){
      playerResults[iPlayerResult].best = true;
    }
  }
  return playerResults;
}

module.exports = mongoose.model('Game', gameSchema);
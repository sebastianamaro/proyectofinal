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
    opponentsType: { type: String },
    players: [ Player.schema ],
    creator: [ Player.schema ],
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
  this.opponentsType = game.opponentsType;
  this.randomPlayersCount = game.randomPlayersCount;
  this.categories = game.selectedCategories;
}

gameSchema.methods.addPlayer = function addPlayer(player){
  var thisGame = this;

  Player.findOne({ 'fbId': player.fbId}, function (err, foundPlayer){
    if (err){
      console.log('ERROR: ' + err);
      return err;
    } 
    if (!foundPlayer){
      console.log('ERROR: user does not exists');
      return "404";
    }

    thisGame.players.push(foundPlayer);
    thisGame.creator.push(foundPlayer);

    thisGame.save(function(err) {
      if(!err) {
        thisGame.sendInvitations(function(errInvitation){
          if (errInvitation){
            console.log('ERROR en sendInvitations: ' + errInvitation);
          }    
        });
        console.log('Added players to game');
      } else {
        console.log('ERROR: ' + err);
      }
    });
    
    foundPlayer.addGame(thisGame.gameId);
    foundPlayer.save(function(err) {
          if(!err) {
            console.log('Inserted new game to player with name '+foundPlayer.getName());
          } else {
            console.log('ERROR: ' + err);
          }
        });
  });  
}

gameSchema.methods.sendNotificationsRoundFinished = function (round, registrationIdStopPlayer, callback){
  for (var i = this.players.length - 1; i >= 0; i--) {
    var player = this.players[i];
    if (player.registrationId == registrationIdStopPlayer){
      console.log("Wont send notification to stop player: "+player.registrationId);
      continue;
    }
    if (round.hasLineOfPlayer(player)) { // if it has a line of the player it means he has already sent me his line OR i have sent him notification
      console.log("Wont send notification to a player that has already been notified or sent line "+player.registrationId);
      continue;
    }
    console.log("Will send to notify this player: "+player.registrationId);

    var gameId =this.gameId;
    var notification = new Notification();
    notification.setRegistrationId(player.registrationId);
    Player.findOne({registrationId: registrationIdStopPlayer }, function (err, foundPlayer){
      if (err) {
        console.log("ERROR: find player failed. "+err);
        return callback("ERROR: find player failed. "+err);
      }
      if (!foundPlayer){
        console.log("ERROR: player not found");
        return callback("ERROR: player not found"); 
      }
      notification.setValues({'game_id':gameId, 'round_id':round.roundId, 'status' : 'FINISHED', 'player': foundPlayer.getName()});
      notification.send(function(err){
        if (err){
          console.log("Error when sendNotifications");
          return callback("Error when sendNotifications");
        } else {
          round.setNotificationSentForPlayer(player);
        }
      });
  });
  }
  return callback();
}
gameSchema.methods.getPlayerNames = function(){
  var playerNames = [];
  for (var i = this.players.length - 1; i >= 0; i--) {
    playerNames.push( this.players[i].getName() );
  };
  return playerNames;
}
gameSchema.methods.getRoundResults = function(){
  var roundResults = [];
  for (var i = this.rounds.length - 1; i >= 0; i--) {
    var aRound = this.rounds[i];
    roundResults.push( {
                      'roundId' : aRound.roundId, 
                      'scores' : aRound.getSummarizedScoresForPlayers(this.players)
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
gameSchema.methods.sendInvitations = function(callback){
  if (this.opponentsType !== 'RANDOM'){
    return callback();
  } else {
    var gameId = this.gameId;
    var creator = this.creator[0];
    console.log("this GAME " + this);
    console.log("this.creator " + this.creator);
    console.log("recien imprimi el creator");
    Player.find({}, function (err, players){
      console.log(players);
      for(var iPlayer in players ){
        var player = players[iPlayer];
        player.sendInvitationToGameIfPossible(gameId, creator);
      }
    });
    return callback();
  }
}
gameSchema.methods.removeAllInvitations = function(){
  var gameId = this.gameId;
  Player.find({ invitations: gameId }, function (err, players){
    for(var iPlayer in players ){
      var player = players[iPlayer];
      player.removeInvitation(gameId);
      player.save(function(err) {
        if(err) {
          console.log("ERROR: save player failed when removeAllInvitations. "+err);
        } 
      });
    }
  });
}
gameSchema.methods.acceptInvitation = function(request, callback){
  var game = this;
  Player.findOne({registrationId: request.player.registrationId }, function (err, player){
    if (err) {
      console.log("ERROR: find player failed. "+err);
      return callback("ERROR: find player failed. "+err);
    }
    if (!player){
      console.log("ERROR: player not found");
      return callback("ERROR: player not found"); 
    }
    if (game.opponentsType == 'RANDOM'){
      game.players.push(player);
      if (game.randomPlayersCount + 1 == game.players.length){
        game.status = "PLAYING";  
        game.removeAllInvitations();        
      }
      player.removeInvitation(game.gameId);
      game.save(function(err) {
        if(err) {
          return callback("ERROR: save game failed. "+err);
        } 
      });
      player.save(function(err) {
        if(err) {
          return callback("ERROR: save player failed after removeInvitation. "+err);
        } 
      });
      console.log("Invitation successfully accepted to player "+player.getName()+" in game "+game.gameId);
      return callback();
    }
  });
}
gameSchema.methods.rejectInvitation = function(request, callback){
  var game = this;
  Player.findOne({registrationId: request.player.registrationId }, function (err, player){
    if (err) {
      console.log("ERROR: find player failed. "+err);
      return callback("ERROR: find player failed. "+err);
    }
    if (!player){
      console.log("ERROR: player not found");
      return callback("ERROR: player not found"); 
    }
    if (game.opponentsType == 'RANDOM'){
      player.removeInvitation(game.gameId);
      player.save(function(err) {
        if(err) {
          return callback("ERROR: save player failed. "+err);
        } 
        console.log("Invitation successfully rejected to player "+player.getName()+" in game "+game.gameId);
      });
      return callback();
    }
  });  
}
gameSchema.methods.asSummarized = function(){
  return {
      'gameId': this.gameId,
      'mode': this.mode,
      'categoriesType': this.categoriesType,
      'opponentsType': this.opponentsType ,
      'owner': this.creator[0],
      'randomPlayersCount': this.randomPlayersCount ,
      'selectedCategories':  this.selectedCategories
  };
}

module.exports = mongoose.model('Game', gameSchema);
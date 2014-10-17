var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;
var Round = require('./round.js');
var Player = require('./player.js');
var NotificationFile = require('./notification.js');
var Category = require('./category.js');

var gameSchema = new Schema({
  	gameId:   { type: Number },
  	startTimestamp: { type: Date }, 
  	rounds: [Round.schema],
  	status: { type: String },
  	categories: [ { id : { type : Number } , name : { type : String } , isFixed : { type : Boolean }} ],
    mode : { type: String },
    categoriesType: { type: String },
    opponentsType: { type: String },
    players: [ Player.schema ],
    creator: [ Player.schema ],
    selectedFriends: [ { fbId:{ type: String }, name:{ type: String } } ],
    randomPlayersCount: { type: Number }
});

gameSchema.methods.getStatus = function () {
      return {
        SHOWING_RESULTS             : 'SHOWINGRESULTS',
        WAITING_FOR_PLAYERS         : 'WAITINGFORPLAYERS',
        WAITING_FOR_NEXT_ROUND      : 'WAITINGFORNEXTROUND',
        ALL_PLAYERS_REJECTED        : 'ALLPLAYERSREJECTED',
        PLAYING                     : 'PLAYING',
        WAITING_FOR_QUALIFICATIONS  : 'WAITINGFORQUALIFICATIONS',
        FINISHED                    : 'FINISHED'
      };
}
gameSchema.methods.getCategoriesType = function () {
      return {
        FIXED      : 'FIXED',
        FREE       : 'FREE'
      };
}
gameSchema.methods.getOpponentsType = function () {
      return {
        RANDOM      : 'RANDOM',
        FRIENDS     : 'FRIENDS'
      };
}
gameSchema.methods.getModes = function () {
      return {
        ONLINE      : 'ONLINE',
        OFFLINE     : 'OFFLINE'
      };
}
gameSchema.methods.moveToWaitingForNextRoundIfPossible = function (round, callback){
  if (!round.isClosed()){
     callback();
  } else {
    if (round.isFullyValidated(this)){
      this.changeToWaitingForNextRound();
      round.calculateScores(this, function(){
        callback();
      });
    } else {
      callback();
    }
  }
}

gameSchema.methods.changeToStatusShowingResults = function () {
  this.status = this.getStatus().SHOWING_RESULTS;
}
gameSchema.methods.changeToWaitingForNextRound = function () {
  this.status = this.getStatus().WAITING_FOR_NEXT_ROUND;
}
gameSchema.methods.moveToShowingResults = function () {
  this.getLastRound().moveToShowingResults(this);
}
gameSchema.methods.isFixedCategoriesType = function () {
  return this.categoriesType == this.getCategoriesType().FIXED;
}
gameSchema.methods.getRound = function (roundId) {
	var round = this.rounds.filter(function (round) {
              return round.roundId == roundId;
            }).pop();
	return round;
 }

gameSchema.methods.getPlayingRound = function getPlayingRound(){
  return this.rounds.filter(function (round) {return round.isPlaying(); }).pop();
}

gameSchema.methods.getLastRound = function getPlayingRound(){
  return this.rounds[this.rounds.length-1];
}

gameSchema.methods.hasStarted = function hasStarted(){
  return this.status != this.getStatus().WAITING_FOR_PLAYERS;
}

gameSchema.methods.getNextLetter = function getNextLetter(){
  var letters = ['A','B','C','D','E','F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V','Y', 'Z'];
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
  this.categories = [];

  for (var i = game.selectedCategories.length - 1; i >= 0; i--) {
    var category= {'id': game.selectedCategories[i].id , 'name': game.selectedCategories[i].name.toUpperCase(), 'isFixed': game.selectedCategories[i].isFixed};
    this.categories.push(category);
  };

  this.selectedFriends = game.selectedFriends;
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
      } else {
        console.log('ERROR: ' + err);
      }
    });
    
    foundPlayer.addGame(thisGame.gameId);
    foundPlayer.save(function(err) {
          if(!err) {
            console.log('Added game to player with name '+foundPlayer.getName());
          } else {
            console.log('ERROR: ' + err);
          }
        });
  });  
}

gameSchema.methods.sendNotificationsRoundFinished = function (round, fbIdStopPlayer, callback){
  if (this.mode == this.getModes().ONLINE)
  {
    for (var i = this.players.length - 1; i >= 0; i--) {
      var player = this.players[i];
      if (player.fbId == fbIdStopPlayer){
        console.log("Wont send notification to stop player: "+player.name);
        continue;
      }
      if (round.hasSentNotificationToPlayer(player.fbId)) {
        console.log("Wont send notification to a player that has already been notified "+player.name);
        continue;
      }
      if (round.hasPlayerSentHisLine(player.fbId)) {
        console.log("Wont send notification to a player that has already sent line "+player.name);
        continue;
      }
      console.log("Will send to notify this player: "+player.name);

      var gameId =this.gameId;
      var notification = new Notification();
      notification.setRegistrationId(player.registrationId);
      Player.findOne({fbId: fbIdStopPlayer }, function (err, foundPlayer){
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
            round.setNotificationSentForPlayer(foundPlayer, callback);
          }
        });
    });
    }
  }
  return callback();
}
gameSchema.methods.getPlayerNames = function(){
  var playerNames = [];
  
  for (var i = this.players.length - 1; i >= 0; i--) {
    playerNames.push(this.players[i].getName());
  };

  return playerNames;
}
gameSchema.methods.getOtherPlayersFirstNames = function(){
  var playersNameArray = [];
  for (var j = this.players.length - 1; j >= 0; j--) {
      if(this.players[j] != this.creator[0]){
        playersNameArray.push({ 'name' : this.players[j].getFirstName()}); //Only first name
      }
  };

  return playersNameArray;
}
gameSchema.methods.getRoundResults = function(){
  var roundResults = [];
  for (var i = this.rounds.length - 1; i >= 0; i--) {
    var aRound = this.rounds[i];
    if (aRound.status == aRound.getStatus().CLOSED)
    {
      if (!(this.status == this.getStatus().WAITING_FOR_QUALIFICATIONS && i == this.rounds.length - 1))
        roundResults.push( {
                          'roundLetter': aRound.letter,
                          'roundId' : aRound.roundId, 
                          'scores' : aRound.getSummarizedScoresForPlayers(this.players)
                        });
    }
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
  for (var i = 0; i < playerPartialResults.length; i++) {
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
  var creator = this.creator[0];
  var gameId = this.gameId;
  
  var selectedFriendsFbsId = [];

  if (this.opponentsType == this.getOpponentsType().FRIENDS){
    
    for (var i = this.selectedFriends.length - 1; i >= 0; i--) {
      selectedFriendsFbsId.push(this.selectedFriends[i].fbId);
    };

    Player.find({ fbId: { $in: selectedFriendsFbsId } }, function (err, players){
      if (err)
        console.log(err);

      for(var iPlayer in players ){
        var player = players[iPlayer];
        player.sendInvitationToGameIfPossible(gameId, creator);
      }
    });
    return callback();
  } else {    
    Player.find({}, function (err, players){
      for(var iPlayer in players ){
        var player = players[iPlayer];
        player.sendInvitationToGameIfPossible(gameId, creator);
      }
    });
    return callback();
  }
}
gameSchema.methods.removeAllInvitations = function(playerWhoResponded){
  var gameId = this.gameId;
  Player.find({ invitations: gameId,  fbId: { $ne: playerWhoResponded } }, function (err, players){
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
  Player.findOne({fbId: request.player.fbId }, function (err, player){
    if (err) {
      console.log("ERROR: find player failed. "+err);
      return callback("ERROR: find player failed. "+err);
    }
    if (!player){
      console.log("ERROR: player not found");
      return callback("ERROR: player not found"); 
    }

    var invitedPlayersCount;
    if (game.opponentsType == game.getOpponentsType().RANDOM)
        invitedPlayersCount = game.randomPlayersCount + 1; //mas el creador
    else
        invitedPlayersCount = game.selectedFriends.length + 1; //mas el creador
      
    //+1 porque todavia no lo guarde (porque necesito guardarlo sin las invitaciones y con el game)
    if (invitedPlayersCount == game.players.length + 1){
      game.status = game.getStatus().WAITING_FOR_NEXT_ROUND;  
      if (game.opponentsType == game.getOpponentsType().RANDOM)
        game.removeAllInvitations(player.fbId);        
    }

    player.removeInvitation(game.gameId);
    player.addGame(game.gameId);

    game.players.push(player);

    game.save(function(err) {
      if(err) {
        return callback("ERROR: save game failed. "+err);
      } 
    });
    console.log('guardo el player');
    player.save(function(err) {

        if(err) {
          return callback("ERROR: save player failed after removeInvitation. "+err);
        } 
      });
    
    console.log("Invitation successfully accepted to player "+player.getName()+" in game "+game.gameId);
    return callback();
    
    
  });
}
gameSchema.methods.rejectInvitation = function(request, callback){
  var game = this;
  Player.findOne({fbId: request.player.fbId }, function (err, player){
    if (err) {
      console.log("ERROR: find player failed. "+err);
      return callback("ERROR: find player failed. "+err);
    }
    if (!player){
      console.log("ERROR: player not found");
      return callback("ERROR: player not found"); 
    }

    console.log("aca 1");
    player.removeInvitation(game.gameId);
    player.save(function(err) {
      if(err) {
        return callback("ERROR: save player failed. "+err);
      } 
      console.log("Invitation successfully rejected to player "+player.getName()+" in game "+game.gameId);
    });
    console.log("aca 2");
    if (game.opponentsType == game.getOpponentsType().FRIENDS){
        console.log("es friends");
        //elimino al que rechazo de los amigos seleccionados, asi cuando los demas contestan q si puedo iniciar el game
        var index = game.selectedFriends.indexOf({ fbId:player.fbId, name:player.name});
        console.log("selected friends antes: " + game.selectedFriends);
        if (index > -1) 
          game.selectedFriends.splice(index, 1);
        console.log("selected frieds despues: " + game.selectedFriends);
        if (game.selectedFriends.length + 1 == game.players.length)
        {
          if (game.players.length == 1)
            game.status = game.getStatus().ALL_PLAYERS_REJECTED;
          else
            game.status = game.getStatus().WAITING_FOR_NEXT_ROUND;  
        }

    }

    game.save(function(err) {
        if(err) {
          return callback("ERROR: save game failed. "+err);
        } 
      });
    return callback();
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
      'selectedCategories':  this.categories,
      'selectedFriends' : this.selectedFriends,
      'players' : this.players
  };
}

module.exports = mongoose.model('Game', gameSchema);
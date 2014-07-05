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
    players: [ Player.schema ]
});

gameSchema.methods.getRound = function getRound(roundId) {
	var round = this.rounds.filter(function (round) {
              return round.roundId === roundId;
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
  this.addPlayer(game.player); 
}

gameSchema.methods.addPlayer = function addPlayer(player){
  var newPlayer = new Player();
  newPlayer.setValues(player);
  //TODO validate it doesn't already exist in game
  this.players.push(newPlayer);
}

gameSchema.methods.sendNotifications = function sendNotifications(callback){
    console.log("entra a send");
  for (var i = this.players.length - 1; i >= 0; i--) {
    var player = this.players[i];
    console.log(player);
    var notification = new Notification();
    notification.setRegistrationId(player.registrationId);
    notification.setValues({'gameId':this.gameId, 'status' : 'FINISHED'});
    notification.send(function(err){
      if (err){
        callback(new Error("algo salio mal"));
      }
    });
  }
}

module.exports = mongoose.model('Game', gameSchema);
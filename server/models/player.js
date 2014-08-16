var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Game = require('./game.js');

var playerSchema = new Schema({
    registrationId: { type: String },
    games: [{ type: Number }],
    invitations: [{ type: Number }]
});

playerSchema.methods.setValues = function (player) {
  this.registrationId = player;
  return this;
}

playerSchema.methods.getGames = function () {
	var games = [];
	for (var i = this.games.length - 1; i >= 0; i--) {
		var gameId = this.games[i];
		var gameToReturn = Game.findOne({ 'gameId': gameId}, function (err, game){
			return { "gameId": game.gameId, "status": game.status };
		});
		games.push(gameToReturn);
	};
	return games;
}
playerSchema.methods.getName = function () {
	if (this.registrationId.length <=3 ) return this.registrationId;
	return this.registrationId.substr(0,4);
}
playerSchema.methods.sendInvitationToGameIfPossible = function(gameId, from){
	if (this.registrationId == from.registrationId){
		console.log("wont send the invitation to the creator");
		return; //wont send the invitation to the creator
	}
	var invitationAlreadySent = this.invitations.filter(function (invitation) {
              return invitation == gameId;
            }).pop();

	if (invitationAlreadySent !== undefined){
		console.log("wont send the invitation to sb already invited");
		return; //wont send the invitation to sb already invited. this validation is just in case
	}
	var notification = new Notification();
    notification.setRegistrationId(this.registrationId);
    notification.setValues({'gameId':gameId, 'reason' : 'INVITATION'});
    var player = this;
    notification.send(function(err){
      if (err){
        console.log("Error when send invitations");
      } else {
        player.setNotificationSentForGame(gameId);
      }
    });
}
playerSchema.methods.setNotificationSentForGame = function(gameId){
	this.invitations.push(gameId);
	this.save(function(err) {
          if(!err) {
            console.log('Saved player with new invitation '+this.registrationId);
          } else {
            console.log('ERROR: ' + err);
          }
        });
}
playerSchema.methods.addGame = function(gameId){
	this.games.push(gameId);
}
playerSchema.methods.removeInvitation = function(gameId){
	var index = this.invitations.indexOf(gameId);
    if (index > -1) {
        this.invitations.splice(index, 1);
    }
}
module.exports = mongoose.model('Player', playerSchema);


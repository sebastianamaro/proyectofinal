var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Game = require('./game.js');

var playerSchema = new Schema({
    registrationId: { type: String },
    games: [{ type: Number }]
});

playerSchema.methods.setValues = function setValues(player) {
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
}
playerSchema.methods.getName = function () {
	if (this.registrationId.length <=3 ) return this.registrationId;
	return this.registrationId.substr(0,4);
}

module.exports = mongoose.model('Player', playerSchema);


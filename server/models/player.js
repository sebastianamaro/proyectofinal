var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

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

module.exports = mongoose.model('Player', playerSchema);


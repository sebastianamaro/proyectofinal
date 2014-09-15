var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var fullRoundSchema = new Schema({
  letter:   { type: String },
  status:   { type: String },
  roundId:   { type: Number },
  gameId:   { type: Number },
  categories: [ {type: String} ],
  gameStatus:   { type: String },
  gameMode: {type: String}
});

fullRoundSchema.methods.fillData = function fillData(game, round) {
  this.letter = round.letter;
  this.status = round.status;
  this.roundId = round.roundId;

  this.gameId = game.gameId;
  
  this.categories=[];

  for (var i = game.categories.length - 1; i >= 0; i--) {
    this.categories.push(game.categories[i].name);
  };

  this.categories = game.categories;
  this.gameStatus = game.status;
  this.gameMode = game.mode;
}

module.exports = mongoose.model('FullRound', fullRoundSchema);



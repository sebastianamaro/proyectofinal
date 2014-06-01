var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var round = new Schema({
  letter:   { type: String },
  gameId:   { type: String },
  status:   { type: String },
  startTimestamp: { type: Date }
});

var game = new Schema({
  gameId:   { type: String },
  startTimestamp: { type: Date }, 
  rounds: [round.schema]
});

module.exports = mongoose.model('Game', game);
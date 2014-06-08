var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;
var Round = require('./round.js');

var game = new Schema({
  	gameId:   { type: Number },
  	startTimestamp: { type: Date }, 
  	rounds: [Round.schema],
  	status: { type: String },
  	categories: [ { type: String } ]
});

module.exports = mongoose.model('Game', game);
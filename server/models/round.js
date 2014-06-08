var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var roundSchema = new Schema({
	roundId : { type: Number },
  	letter:   { type: String },
  	status:   { type: String },
  	startTimestamp: { type: Date },
  	finishTimestamp: { type: Date }
});

roundSchema.methods.start = function start(game,letter) {
	this.letter = letter;
	this.status = 'PLAYING';
	this.roundId = game.rounds.length + 1;
	this.startTimestamp = new Date();
	return this;
 }

roundSchema.methods.finish = function finish() {
  console.log("Finishing round "+this.roundId);
  this.status = "CLOSED";
  this.finishTimestamp = new Date();
  return this;
 }
 
module.exports = mongoose.model('Round', roundSchema);
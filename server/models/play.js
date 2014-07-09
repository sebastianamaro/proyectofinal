var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var playSchema = new Schema({
  category:   { type: String },
  timestamp: { type: Date }, 
  word: { type: String },
  score: { type: Number },
  result: { type: String }
}, { _id : false });

playSchema.methods.setValues = function setValues(play) {
  this.category = play.category;
  this.timestamp = play.timestamp;
  this.word = play.word;
  return this;
}
playSchema.methods.isSimilarTo = function isSimilarTo(play) {
	return play.category == this.category && play.word == this.word;
}

playSchema.methods.setUniqueScore = function setUniqueScore() {
  this.score = 20;
  this.result = 'ONLY';
  console.log(this.result);
}
playSchema.methods.setUniqueScore = function setUniqueScore() {
  this.score = 10;
  this.result = 'UNIQUE';
  console.log(this.result);
}
playSchema.methods.setRepeatedResult = function setRepeatedResult() {
	this.score = 5;
	this.result = 'REPEATED';
}
playSchema.methods.setInvalidResult = function setInvalidResult() {
	this.score = 0;
	this.result = 'INVALID';
}

module.exports = mongoose.model('Play', playSchema);
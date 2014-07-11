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
  console.log("el timestamp de la play es: " + play.timeStamp);
  this.timestamp = play.timeStamp;
  this.word = play.word;
  return this;
}
playSchema.methods.isSameCategoryAs = function isSameCategoryAs(play) {
  return play.category == this.category;
}
playSchema.methods.isSameWordAs = function isSameWordAs(play) {
	return play.word == this.word;
}
playSchema.methods.setOnlyScore = function setOnlyScore() {
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
playSchema.methods.isWordValid = function isWordValid(round, game) {
  if (this.word == undefined){
    this.word = '';
    return false;
  }
  if (this.word.length== 0){
    return false;
  }
  if (this.word.charAt(0) !== round.letter){
    return false;
  } //for now it's almost a dummy
  if (game.categoriesType == "FIXED"){
    var categoryInstance = new Category();
    categoryInstance.setCategory(this.category);
    return categoryInstance.isWordValid(this);
  }
  return true;
}
module.exports = mongoose.model('Play', playSchema);

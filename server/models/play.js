var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Category = require('./category.js');

var playSchema = new Schema({
  category:   { type: String },
  timeStamp: { type: Date }, 
  word:   { type: String },
  score: { type: Number },
  result: { type: String }
}, { _id : false });


playSchema.methods.setValues = function setValues(newPlay) {
  
  this.category = newPlay.category;
  this.timeStamp = newPlay.timeStamp;
  
  if(newPlay.word == undefined) {
  	this.word='';
  } else {
	  this.word = newPlay.word;
  }

  return this;
}

playSchema.methods.setOnlyScore = function setOnlyScore() {
  this.score = 20;
  this.result = 'ONLY';
}

playSchema.methods.setUniqueScore = function setUniqueScore() {
  this.score = 10;
  this.result = 'UNIQUE';
}

playSchema.methods.isSimilarTo = function isSimilarTo(similarPlay) {
	return similarPlay.category == this.category && similarPlay.word == this.word;
}

playSchema.methods.setRepeatedResult = function setRepeatedResult() {
	this.score = 5;
	this.result = 'REPEATED';
}

playSchema.methods.setInvalidResult = function setInvalidResult() {
	this.score = 0;
	this.result = 'INVALID';
}


playSchema.methods.validatePlay = function validatePlay(game, letter) {
  if (letter == undefined){
    return false;
  }
  if (this.word.charAt(0).toUpperCase() !== letter.toUpperCase() ){
    return false;
  } //for now it's almost a dummy
  if (game.categoriesType == "FIXED"){
    var categoryInstance = new Category();
    return categoryInstance.isWordValid(this.word, this.category);
  }
  return true;
}

playSchema.methods.asSummarized = function asSummarized() {
  return {'result': this.result,
           'scoreInfo': {'score': this.score,
                     'best':false},
          'word': this.word,
          'category':this.category
          };
}
module.exports = mongoose.model('Play', playSchema);

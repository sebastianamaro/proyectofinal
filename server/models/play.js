var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Category = require('./category.js');

var playSchema = new Schema({
  category:   { type: String },
  timeStamp: { type: Date }, 
  word:   { type: String },
  score: { type: Number },
  result: { type: String },
  validations: [{ fbId:{ type: String }, isValid:{ type: Boolean }}]
}, { _id : false });

<<<<<<< HEAD
playSchema.methods.hasLateResult = function hasLateResult(){
  return this.result == 'LATE';
}
playSchema.methods.setValues = function setValues(newPlay) {
=======

playSchema.methods.setQualification= function (judge,isValid) {
  var existingValidation = this.validations.filter(function (val) { return val.fbId == judge;}).pop();  
  if (! existingValidation == undefined){
    this.validations.push({'fbId': judge, 'isValid': isValid});
  }
}
playSchema.methods.setValues = function (newPlay) {
>>>>>>> Not tested! Server can receive qualifications
  
  this.category = newPlay.category;
  this.timeStamp = newPlay.timeStamp;
  
  if(newPlay.word == undefined) {
  	this.word='';
  } else {
	  this.word = newPlay.word;
  }

  return this;
}

playSchema.methods.setOnlyResult = function () {
  this.score = 20;
  this.result = 'ONLY';
}

playSchema.methods.setLateResult = function setLateResult() {
  this.score = 0;
  this.result = 'LATE';
}

playSchema.methods.setUniqueResult = function () {
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

playSchema.methods.isValidated = function(playersAmount){
  return this.validations.length == playersAmount -1;
}

playSchema.methods.validatePlay = function (category, letter, callback) {
  if (letter == undefined){
    return callback(false);
  }
  if (this.word.charAt(0).toUpperCase() !== letter.toUpperCase() ){
    return callback(false);
  } 
  if (category.isFixed){
    var play = this;
    Category.findOne({ 'name': category.name }, function (err, foundCategory){
      return callback(foundCategory.isWordValid(play.word, play.category));
    });
  } else {
    var upVotes = this.validations.filter(function (val) {return val.isValid; }).pop();
    var downVotes = this.validations.filter(function (val) {return !val.isValid; }).pop();
    return callback(upVotes >= downVotes);
  }
}

playSchema.methods.asSummarized = function asSummarized(fbId,category) {
  var scoreToSend = this.hasLateResult() ? -1 : this.score;
  var validation = this.validations.filter(function (val) {return val.fbId == fbId; }).pop();
  var validated = false;
  var isValid = false;
  if (validation !== undefined){
    validated = true;
    isValid = validation.isValid;
  }
  return {'result': this.result,
           'scoreInfo': {'score': scoreToSend,
                     'best':false},
          'word': this.word,
          'category': category.name,
          'isFixed': category.isFixed,
          'validated': validated,
          'isValid': isValid
          };
}
module.exports = mongoose.model('Play', playSchema);

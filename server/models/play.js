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

playSchema.methods.hasLateResult = function (){
  return this.result == 'LATE';
}

playSchema.methods.setQualification= function (judge,isValid) {
  var existingValidation = this.validations.filter(function (val) { return val.fbId == judge;}).pop();  
  if ( existingValidation == undefined){
    this.validations.push({'fbId': judge, 'isValid': isValid});
  }
}

playSchema.methods.setValues = function (newPlay) {
  
  this.category = newPlay.category.toUpperCase();
  this.timeStamp = newPlay.timeStamp;
  
  if(newPlay.word == undefined) {
  	this.word='';
  } else {
	  this.word = newPlay.word.toUpperCase();
  }

  return this;
}

playSchema.methods.setOnlyResult = function () {
  this.score = 20;
  this.result = 'ONLY';
}

playSchema.methods.setLateResult = function () {
  this.score = 0;
  this.result = 'LATE';
}

playSchema.methods.setUniqueResult = function () {
  this.score = 10;
  this.result = 'UNIQUE';
}

playSchema.methods.isSimilarTo = function (similarPlay) {
	return similarPlay.category == this.category && similarPlay.word == this.word;
}

playSchema.methods.setRepeatedResult = function () {
	this.score = 5;
	this.result = 'REPEATED';
}

playSchema.methods.setInvalidResult = function () {
	this.score = 0;
	this.result = 'INVALID';
}

playSchema.methods.isValidated = function(playersAmount){
  return this.word == '' || this.validations.length == playersAmount -1;
}

playSchema.methods.setLateResultIfHasWord = function () {
  if (this.hasWord()){
    this.setLateResult();
  }
}

playSchema.methods.hasWord = function () {
  return this.word !== undefined && this.word !== '';
}

playSchema.methods.validatePlay = function (category, letter) {
  if(this.hasLateResult()){
    return false;
  }
  var valid = true;
  if (letter == undefined){
    valid = false;
  } else {
    if (this.word.charAt(0) !== letter ){
      valid = false;
    } else {
      if (category.isFixed){
        valid = category.isWordValid(this.word, this.category);
      } else {
        var upVotes = this.validations.filter(function (val) {return val.isValid; }).length;
        var downVotes = this.validations.filter(function (val) {return !val.isValid; }).length;
        valid = upVotes >= downVotes;
      }
    }
  }
  if (!valid){
    this.setInvalidResult();
  }
  return valid;

}

playSchema.methods.asSummarized = function (fbId,category) {
  var scoreToSend = this.hasLateResult() ? -1 : this.score;
  
  var validated = false;
  var isValid = false;
  if (fbId != -1)
  {
    var validation = this.validations.filter(function (val) {return val.fbId == fbId; }).pop();
    if (validation !== undefined){
      validated = true;
      isValid = validation.isValid;
    }
  }
  else
  {
    validated = true;
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

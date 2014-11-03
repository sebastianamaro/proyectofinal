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

playSchema.methods.formatWord = function(word){
  var find = ['À', 'Á', 'Â', 'Ã', 'Ä', 'Å', 'Æ', 'Ç', 'È', 'É', 'Ê', 'Ë', 'Ì', 'Í', 'Î', 'Ï', 'Ð', 'Ñ', 'Ò', 'Ó', 'Ô', 'Õ', 'Ö', 'Ø', 'Ù', 'Ú', 'Û', 'Ü', 'Ý', 'ß', 'à', 'á', 'â', 'ã', 'ä', 'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', 'ø', 'ù', 'ú', 'û', 'ü', 'ý', 'ÿ', 'Ā', 'ā', 'Ă', 'ă', 'Ą', 'ą', 'Ć', 'ć', 'Ĉ', 'ĉ', 'Ċ', 'ċ', 'Č', 'č', 'Ď', 'ď', 'Đ', 'đ', 'Ē', 'ē', 'Ĕ', 'ĕ', 'Ė', 'ė', 'Ę', 'ę', 'Ě', 'ě', 'Ĝ', 'ĝ', 'Ğ', 'ğ', 'Ġ', 'ġ', 'Ģ', 'ģ', 'Ĥ', 'ĥ', 'Ħ', 'ħ', 'Ĩ', 'ĩ', 'Ī', 'ī', 'Ĭ', 'ĭ', 'Į', 'į', 'İ', 'ı', 'Ĳ', 'ĳ', 'Ĵ', 'ĵ', 'Ķ', 'ķ', 'Ĺ', 'ĺ', 'Ļ', 'ļ', 'Ľ', 'ľ', 'Ŀ', 'ŀ', 'Ł', 'ł', 'Ń', 'ń', 'Ņ', 'ņ', 'Ň', 'ň', 'ŉ', 'Ō', 'ō', 'Ŏ', 'ŏ', 'Ő', 'ő', 'Œ', 'œ', 'Ŕ', 'ŕ', 'Ŗ', 'ŗ', 'Ř', 'ř', 'Ś', 'ś', 'Ŝ', 'ŝ', 'Ş', 'ş', 'Š', 'š', 'Ţ', 'ţ', 'Ť', 'ť', 'Ŧ', 'ŧ', 'Ũ', 'ũ', 'Ū', 'ū', 'Ŭ', 'ŭ', 'Ů', 'ů', 'Ű', 'ű', 'Ų', 'ų', 'Ŵ', 'ŵ', 'Ŷ', 'ŷ', 'Ÿ', 'Ź', 'ź', 'Ż', 'ż', 'Ž', 'ž', 'ſ', 'ƒ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ǎ', 'ǎ', 'Ǐ', 'ǐ', 'Ǒ', 'ǒ', 'Ǔ', 'ǔ', 'Ǖ', 'ǖ', 'Ǘ', 'ǘ', 'Ǚ', 'ǚ', 'Ǜ', 'ǜ', 'Ǻ', 'ǻ', 'Ǽ', 'ǽ', 'Ǿ', 'ǿ'];
  var replace = ['A', 'A', 'A', 'A', 'A', 'A', 'AE', 'C', 'E', 'E', 'E', 'E', 'I', 'I', 'I', 'I', 'D', 'N', 'O', 'O', 'O', 'O', 'O', 'O', 'U', 'U', 'U', 'U', 'Y', 's', 'a', 'a', 'a', 'a', 'a', 'a', 'ae', 'c', 'e', 'e', 'e', 'e', 'i', 'i', 'i', 'i', 'n', 'o', 'o', 'o', 'o', 'o', 'o', 'u', 'u', 'u', 'u', 'y', 'y', 'A', 'a', 'A', 'a', 'A', 'a', 'C', 'c', 'C', 'c', 'C', 'c', 'C', 'c', 'D', 'd', 'D', 'd', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'G', 'g', 'G', 'g', 'G', 'g', 'G', 'g', 'H', 'h', 'H', 'h', 'I', 'i', 'I', 'i', 'I', 'i', 'I', 'i', 'I', 'i', 'IJ', 'ij', 'J', 'j', 'K', 'k', 'L', 'l', 'L', 'l', 'L', 'l', 'L', 'l', 'l', 'l', 'N', 'n', 'N', 'n', 'N', 'n', 'n', 'O', 'o', 'O', 'o', 'O', 'o', 'OE', 'oe', 'R', 'r', 'R', 'r', 'R', 'r', 'S', 's', 'S', 's', 'S', 's', 'S', 's', 'T', 't', 'T', 't', 'T', 't', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'W', 'w', 'Y', 'y', 'Y', 'Z', 'z', 'Z', 'z', 'Z', 'z', 's', 'f', 'O', 'o', 'U', 'u', 'A', 'a', 'I', 'i', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'A', 'a', 'AE', 'ae', 'O', 'o'];
  return this.replaceArray(word,find,replace);
}

playSchema.methods.replaceArray = function(replaceString, find, replace) {
  for (var i = 0; i < find.length; i++) {
    replaceString = replaceString.replace(find[i], replace[i]);
  }
  return replaceString;
};
playSchema.methods.setValues = function (newPlay) {
  
  this.category = newPlay.category.toUpperCase();
  this.timeStamp = newPlay.timeStamp;
  
  if(newPlay.word == undefined) {
  	this.word='';
  } else {
	  this.word = this.formatWord(newPlay.word.toUpperCase());
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

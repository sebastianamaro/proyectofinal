var mongoose = require('mongoose'),
    Schema   = mongoose.Schema,
    moment   = require('moment');

var Play = require('./play.js');
var Player = require('./player.js');
var Game = require('./game.js');

var lineSchema = new Schema({
  player:  { fbId:{ type: String }, name:{ type: String } },
  plays:   [ Play.schema ],
  score: { type: Number },
  startTimestamp: { type: Date },
  finishTimestamp: { type: Date }
}, { _id : false });

lineSchema.methods.setQualification = function (judge, category, isValid) {
  var playToQualify = this.plays.filter(function (play) { return play.category == category;}).pop();  
  playToQualify.setQualification(judge,isValid);
}
lineSchema.methods.hasAllWords = function () {
  var playWithoutWord = this.plays.filter(function (play) { return play.word == '';}).pop();  
  return playWithoutWord == undefined;
}
lineSchema.methods.setValues = function (line, foundPlayer) {
  var thisLine = this;
  thisLine.player = { fbId: foundPlayer.fbId, name: foundPlayer.name };
  thisLine.startTimestamp = line.startTimestamp;
  thisLine.finishTimestamp = line.finishTimestamp;
  thisLine.addPlays(line.plays);
 }

lineSchema.methods.setLateResults = function (bestTime) {
  for (var i = this.plays.length - 1; i >= 0; i--) {
    var play = this.plays[i];
    var startTimestamp = moment(this.startTimestamp);
    var playTimestamp = moment(play.timeStamp);
    var time = playTimestamp.diff(startTimestamp, 'seconds');
    if (time > bestTime){
      play.setLateResultIfHasWord();
    }
  };
}
lineSchema.methods.addPlays = function (playsArray) {
  for (var i = playsArray.length - 1; i >= 0; i--) {
    var newPlay= new Play();
    newPlay.setValues(playsArray[i]);
    this.plays.push(newPlay);
  };
}

lineSchema.methods.isFullyValidated = function (categories, playersAmount) {
  for (var i = this.plays.length - 1; i >= 0; i--) {
    var play = this.plays[i];
    var category = categories.filter(function (cat) {
        return cat.name.toUpperCase() == play.category.toUpperCase(); }).pop();
    if (!category.isFixed){
      if (! play.isValidated(playersAmount)){
        console.log('Category '+category.name+' is not fixed and is not validated');
        return false;
      }  else {
        console.log('Category '+category.name+' is not fixed and is validated');
      }
    }
  }
  return true;
}

lineSchema.methods.setTotalScore = function (gameId, roundId) {
  var totalScore = 0;
  for (var i = this.plays.length - 1; i >= 0; i--) {
    var play = this.plays[i];
    totalScore += play.score;
  };
  this.score = totalScore;
}
lineSchema.methods.getSummarizedPlays = function (fbId,categories){
  var summarizedPlays = [];
  for (var i = this.plays.length - 1; i >= 0; i--) {
    var play = this.plays[i];
    var category = categories.filter(function (cat) {return cat.name.toUpperCase() == play.category.toUpperCase(); }).pop();
    summarizedPlays.push( play.asSummarized(fbId,category) );
  };
  return summarizedPlays;
}
module.exports = mongoose.model('Line', lineSchema);
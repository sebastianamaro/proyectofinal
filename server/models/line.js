var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Play = require('./play.js');
var Player = require('./player.js');
var Game = require('./game.js');

var lineSchema = new Schema({
  player:   {registrationId: {type: String}},
  plays:   [ Play.schema ],
  score: { type: Number },
  startTimestamp: { type: Date }
}, { _id : false });

lineSchema.methods.setValues = function setValues(line) {
  this.player.registrationId = line.player.registrationId;
  this.startTimestamp = line.startTimestamp;
  this.addPlays(line.plays);
 }

lineSchema.methods.addPlays = function addPlays(plays) {
  for (var i = plays.length - 1; i >= 0; i--) {
    var newPlay = new Play();
    newPlay.setValues(plays[i]);
    this.plays.push(newPlay);
  };
}
lineSchema.methods.wasSentByPlayer = function wasSentByPlayer() {
  return this.plays.length > 0;
}
lineSchema.methods.getPlaySimilarTo = function getPlaySimilarTo(searchedPlay) {
  return this.plays.filter(function (play) {
    return play.isSimilarTo(searchedPlay);
  }).pop();
}

lineSchema.methods.getValidPlayForSameCategory = function getValidPlayForSameCategory(searchedPlay,round,game) {
  var foundPlay = this.plays.filter(function (play) {
    return play.isSameCategoryAs(searchedPlay);
  }).pop();
  if (foundPlay !== undefined){
    if (foundPlay.validateWord(round,game)){
      return foundPlay;
    }
  }
  return false;
}

lineSchema.methods.setTotalScore = function setTotalScore(gameId, roundId) {
  var totalScore = 0;
  for (var i = this.plays.length - 1; i >= 0; i--) {
    var play = this.plays[i];
    totalScore += play.score;
  };
  this.score = totalScore;
}

module.exports = mongoose.model('Line', lineSchema);

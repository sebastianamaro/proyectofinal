var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Play = require('./play.js');
var Player = require('./player.js');
var Game = require('./game.js');

var lineSchema = new Schema({
  player:   [ Player.schema ],
  plays:   [ Play.schema ],
  score: { type: Number },
  startTimestamp: { type: Date }
}, { _id : false });

lineSchema.methods.setValues = function setValues(line, foundPlayer) {
  var thisLine = this;
  console.log('line.player.fbId '+line.player.fbId);
  

      thisLine.player.push(foundPlayer);
      thisLine.startTimestamp = line.startTimeStamp;
      thisLine.addPlays(line.plays);
    
  
 }

lineSchema.methods.addPlays = function addPlays(playsArray) {
  for (var i = playsArray.length - 1; i >= 0; i--) {
    var newPlay= new Play();

    newPlay.setValues(playsArray[i]);
    newPlay.setUniqueScore();
    this.plays.push(newPlay);
  };
}

lineSchema.methods.getPlaySimilarTo = function getPlaySimilarTo(searchedPlay) {
  return this.plays.filter(function (play) {
    return play.isSimilarTo(searchedPlay);
  }).pop();
}

lineSchema.methods.setTotalScore = function setTotalScore(gameId, roundId) {
  var totalScore = 0;
  for (var i = this.plays.length - 1; i >= 0; i--) {
    var play = this.plays[i];
    totalScore += play.score;
  };
  this.score = totalScore;
}
lineSchema.methods.getSummarizedPlays = function getSummarizedPlays(){
  var summarizedPlays = [];
  for (var i = this.plays.length - 1; i >= 0; i--) {
    summarizedPlays.push( this.plays[i].asSummarized() );
  };
  return summarizedPlays;
}
module.exports = mongoose.model('Line', lineSchema);
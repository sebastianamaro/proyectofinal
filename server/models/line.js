var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Play = require('./play.js');

var lineSchema = new Schema({
  player:   { type: String },
  plays:   [ Play.schema ],
  score: { type: Number },
  startTimestamp: { type: Date }
});

lineSchema.methods.setValues = function setValues(line) {
  this.player = line.player;
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

lineSchema.methods.getPlaySimilarTo = function getPlaySimilarTo(searchedPlay) {
  return this.plays.filter(function (play) {
    return play.isSimilarTo(searchedPlay);
  }).pop();
}

module.exports = mongoose.model('Line', lineSchema);

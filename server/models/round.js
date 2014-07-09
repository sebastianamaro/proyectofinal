var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Play = require('./play.js');
var Line = require('./line.js');

var roundSchema = new Schema({
  roundId : { type: Number },
  letter:   { type: String },
  status:   { type: String },  
  lines: [Line.schema] 
}, { _id : false });

roundSchema.methods.start = function start(letter) {
  this.letter = letter;
  this.status = 'PLAYING';
}

roundSchema.methods.finish = function finish() {
  this.status = "CLOSED";
}

roundSchema.methods.addLine = function addLine(newLine) {
  var existingLine = this.lines.filter(function (line) {return line.player.registrationId == newLine.player.registrationId; }).pop();
  if (!existingLine){
    var myLine = new Line();
    myLine.setValues(newLine);
    this.lines.push(myLine);
  }
}

roundSchema.methods.isPlaying = function isPlaying() {
  return this.status === 'PLAYING';
}

roundSchema.methods.checkAllPlayersFinished = function checkAllPlayersFinished(game) {
  var playersCount = game.players.length;
  var linesCount = this.lines.length;
  return playersCount == linesCount;
}

roundSchema.methods.validatePlay = function validatePlay(play) {
  return play.word.charAt(0) == this.letter; //for now it's almost a dummy
}
roundSchema.methods.setValidScore = function setValidScore(play, iLineMyLine) {

  for (var iLine = this.lines.length - 1; iLine >= 0; iLine--) {
    if (iLine == iLineMyLine){
      continue;
    }
    var line = this.lines[iLine];
    var repeatedPlay = line.getPlaySimilarTo(play);
    if (repeatedPlay){
      repeatedPlay.setRepeatedResult();
      play.setRepeatedResult();
      continue;
    }
  };
  if (!play.result){ //it's not repeated, neither invalid
    play.setUniqueScore();
  }
}

roundSchema.methods.hasLineOfPlayer = function hasLineOfPlayer(player) {
  var lineOfPlayerExists = this.lines.filter(function (line) {return line.player == player; }).pop();
  if (lineOfPlayerExists === undefined) return false;
  return true;
}
roundSchema.methods.calculateScores = function calculateScores(game) {
  for (var iLine = this.lines.length - 1; iLine >= 0; iLine--) {
    var line = this.lines[iLine];
    for (var iPlay = line.plays.length - 1; iPlay >= 0; iPlay--) {
      var play = line.plays[iPlay];
      if (!play.result === undefined){
        continue;
      }
      if (!this.validatePlay(play)){
        play.setInvalidResult();
        continue;
      }
      this.setValidScore(play, iLine);
    };
  };
}

roundSchema.methods.hasLineOfPlayer = function hasLineOfPlayer(player){
  var existingLine = this.lines.filter(function (line) {
    return line.player.registrationId == player.registrationId; 
  }).pop();  
  return existingLine !== undefined;
}

roundSchema.methods.setNotificationSentForPlayer = function setNotificationSentForPlayer(player){
  var line = new Line();
  line.player = player;
  this.addLine(line);
}

module.exports = mongoose.model('Round', roundSchema);
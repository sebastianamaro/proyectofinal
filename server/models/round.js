var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Play = require('./play.js');
var Line = require('./line.js');

var roundSchema = new Schema({
  roundId : { type: Number },
  letter:   { type: String },
  status:   { type: String },
  lines: [Line.schema] 
});

roundSchema.methods.start = function start(letter) {
  this.letter = letter;
  this.status = 'PLAYING';
  return this;
 }

roundSchema.methods.finish = function finish() {
  this.status = "CLOSED";
  return this;
 }

roundSchema.methods.addLine = function addLine(newLine) {
  var existingLine = this.lines.filter(function (line) {return line.player == newLine.player; }).pop();
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
  return true; //for now it's a dummy
}

roundSchema.methods.setValidScore = function setValidScore(play) {
  var repeatedPlays = this.lines.filter(function (line) 
    {
      return line.getPlaySimilarTo(play);
    }
  ).pop();
  if (repeatedPlay)


  this.lines.reduce(function(previousLine, currentLine, index, array){
      return plays.push(currentLine.plays);
  }, usedLetters);
}

roundSchema.methods.calculateScores = function calculateScores(game) {
  for (var i = this.lines.length - 1; i >= 0; i--) {
    var line = this.lines[i];
    for (var j = line.plays.length - 1; j >= 0; j--) {
      var play = line.plays[j];
      if (play.result){
        continue;
      }
      if (!this.validatePlay(play)){
        play.score = 0;
        play.result = "INVALID";
        continue;
      }
      this.setValidScore(play);
    };
  };
}
 
module.exports = mongoose.model('Round', roundSchema);
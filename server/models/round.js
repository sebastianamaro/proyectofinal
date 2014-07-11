var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Play = require('./play.js');
var Line = require('./line.js');
var Category = require('./category.js');

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


roundSchema.methods.addLine = function addLine(newLine) {
  var existingLine = this.lines.filter(function (line) {return line.player.registrationId == newLine.player.registrationId; }).pop();
  if (!existingLine){
    existingLine = new Line();
    this.lines.push(existingLine);
  } 
  existingLine.setValues(newLine);
}

roundSchema.methods.isPlaying = function isPlaying() {
  return this.status === 'PLAYING';
}

roundSchema.methods.checkAllPlayersFinished = function checkAllPlayersFinished(game) {
  for (var i = this.lines.length - 1; i >= 0; i--) {
    var line = this.lines[i];
    if (!line.wasSentByPlayer()){
      return false;
    }
  };
  return true;
}


roundSchema.methods.setValidScore = function setValidScore(play, iLineMyLine, game) {
  var atLeastAnotherValid =  false;
  for (var iLine = this.lines.length - 1; iLine >= 0; iLine--) {
    if (iLine == iLineMyLine){
      continue;
    }
    var line = this.lines[iLine];
    var anotherValidPlay = line.getValidPlayForSameCategory(play, this, game); 
    if (anotherValidPlay){
      atLeastAnotherValid = true;
      var repeatedPlay = anotherValidPlay.isSameWordAs(play); 
      if (repeatedPlay){
        anotherValidPlay.setRepeatedResult();
        play.setRepeatedResult();
        continue;
      }
    }
  };
  if (!play.result){ //it's not repeated, neither invalid
    if (atLeastAnotherValid){ //is he the only one who answered for this cat?
       play.setUniqueScore(); //10
    } else{
      play.setOnlyScore(); //20
    }
  }
}

roundSchema.methods.hasLineOfPlayer = function hasLineOfPlayer(player) {
  var lineOfPlayerExists = this.lines.filter(function (line) {return line.player == player; }).pop();
  if (lineOfPlayerExists === undefined) return false;
  return true;
}

roundSchema.methods.finish = function finish(game) {
  this.status = "FINISHED";
  //CALCULATES SCORES
  for (var iLine = this.lines.length - 1; iLine >= 0; iLine--) {
    var line = this.lines[iLine];
    for (var iPlay = line.plays.length - 1; iPlay >= 0; iPlay--) {
      var play = line.plays[iPlay];
      if (!play.result === undefined){
        continue;
      }
      if (!play.isWordValid(this, game)){ //checks it's correct and accepted
        play.setInvalidResult();
        continue;
      }
      this.setValidScore(play, iLine, game); // sets either OK or REPEATED
    };
    line.setTotalScore(game.gameId, this.roundId);
  };
}

roundSchema.methods.hasLineOfPlayer = function hasLineOfPlayer(player){
  var existingLine = this.lines.filter(function (line) {
    return line.player.registrationId == player.registrationId; 
  }).pop();  
  var result= !(existingLine == undefined);
  return result;
}

roundSchema.methods.setNotificationSentForPlayer = function setNotificationSentForPlayer(player){
  var line = new Line();
  line.player = player;
  console.log("Setting setNotificationSentForPlayer "+player.registrationId);
  this.addLine(line);
}

module.exports = mongoose.model('Round', roundSchema);
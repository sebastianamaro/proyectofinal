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
  this.status = 'OPENED';
}

roundSchema.methods.addLine = function addLine(newLine, foundPlayer) {
  console.log('newLine.player.fbId ' + newLine.player.fbId);
  var existingLine = this.lines.filter(function (line) {return line.player[0].fbId == newLine.player.fbId; }).pop();
  
  if (existingLine===undefined){
    var myLine = new Line();
    myLine.setValues(newLine, foundPlayer);
    console.log('myline ' + myLine);
    this.lines.push(myLine);
  }
}

roundSchema.methods.isPlaying = function isPlaying() {
  return this.status === 'OPENED';
}

roundSchema.methods.getPlayersWhoHavePlayed = function getPlayersWhoHavePlayed() {
  var players=[];
  for (var i = this.lines.length - 1; i >= 0; i--) {
     players.push(this.lines[i].player);
  };

  return players;
}

roundSchema.methods.checkAllPlayersFinished = function checkAllPlayersFinished(game) {
  var playersCount = game.players.length;
  var linesCount = this.lines.length;
  return playersCount == linesCount;
}

roundSchema.methods.hasLineOfPlayer = function hasLineOfPlayer(player){
  var existingLine = this.lines.filter(function (line) {
    return line.player[0].fbId == player.fbId; 
  }).pop();  
  return existingLine !== undefined;
}

roundSchema.methods.hasPlayerSentHisLine = function hasPlayerSentHisLine(player){
  var existingLine = this.lines.filter(function (line) {
    return line.player[0].fbId == player.fbId && line.plays.length>0; 
  }).pop();  
  return existingLine !== undefined;
}
roundSchema.methods.createScoresMap = function createScoresMap(game){
  var scoresMap = [];
  for (var iLine = this.lines.length - 1; iLine >= 0; iLine--) {
    var line = this.lines[iLine];
    for (var iPlay = line.plays.length - 1; iPlay >= 0; iPlay--) {
      var play = line.plays[iPlay];
      if (!play.validatePlay(game, this.letter)){ //checks it's correct and accepted
        play.setInvalidResult();
        continue;
      }
      this.addToScoresMap(scoresMap, play, iLine, iPlay); 
    };
  };
  return scoresMap;
}
roundSchema.methods.finish = function finish(game) {
  this.status = "CLOSED";
  var scoresMap = this.createScoresMap(game);
  this.calculateAndSetPartialScores(scoresMap);
  this.calculateAndSetTotalScores();
}
roundSchema.methods.calculateAndSetTotalScores = function calculateAndSetTotalScores(){
  for (var i = this.lines.length - 1; i >= 0; i--) {
    var line = this.lines[i];
    var totalScore = 0;
    for (var j = line.plays.length - 1; j >= 0; j--) {
      var play = line.plays[j];
      totalScore += play.score;
    };
    line.score = totalScore;
  };
}

roundSchema.methods.calculateAndSetPartialScores = function calculateAndSetPartialScores(scoresMap){
  for (var category in scoresMap) {
    var words = scoresMap[category];
    if (Object.keys(words).length == 1){ //only one word was set for this category, means it's repeated or only
      var theWord = Object.keys(words)[0]; 
      var dataForPlays = words[theWord]; //array of objects that have iLine and iPlay
      
      if (dataForPlays.length == 1){ //only one player chose this word, it's only
        var playToScore = this.getPlay(dataForPlays, 0);
        playToScore.setOnlyResult();
      } else { //more than a player chose this word, they are all repeated
        this.setAllRepeated(dataForPlays);
      }
    } else { //more than one word, each of them can be either repeated or unique
      for (var word in words){
        var dataForPlays = words[word];
        if (dataForPlays.length == 1){ //only one player chose this word, it's only
          var playToScore = this.getPlay(dataForPlays, 0);
          playToScore.setUniqueResult();
        } else { //more than a player chose this word, they are all repeated
          this.setAllRepeated(dataForPlays);
        }
      }
    } 
  }
}
roundSchema.methods.setAllRepeated = function setAllRepeated(dataForPlays){
  for (var i = dataForPlays.length - 1; i >= 0; i--) {
    var playToScore = this.getPlay(dataForPlays, i);
    playToScore.setRepeatedResult();
  }
}
roundSchema.methods.getPlay = function getPlay(dataForPlays, iData){
  var iLine = dataForPlays[iData].iLine;
  var iPlay = dataForPlays[iData].iPlay;
  return this.lines[iLine].plays[iPlay];
}
roundSchema.methods.addToScoresMap = function addToScoresMap(scoresMap, play, iLine, iPlay){
  if (!(play.category in scoresMap)){
    scoresMap[play.category] = [];
  }
  if (!(play.word in scoresMap[play.category] )){
    scoresMap[play.category][play.word] = [];
  }
  scoresMap[play.category][play.word].push({'iLine':iLine, 'iPlay':iPlay});
}

roundSchema.methods.setNotificationSentForPlayer = function setNotificationSentForPlayer(player){
  var line = new Line();
  line.player.push(player);
  this.addLine(line);
}

roundSchema.methods.getSummarizedScoresForPlayers = function getSummarizedScoresForPlayers(players){
  var scores = [];
  var bestScore = 0;
  
  for (var i = players.length - 1; i >= 0; i--) {
    var aPlayer = players[i];
    var lineForPlayer = this.lines.filter(function (line) 
      {return line.player[0].fbId == aPlayer.fbId; }).pop();
    
    var unScore = { 'score': lineForPlayer.score, 'best' : false };
    scores.push(unScore);

    if (lineForPlayer.score > bestScore){
      bestScore = lineForPlayer.score;
    } 
  }
  for(var scoreI in scores){
    if (scores[scoreI].score == bestScore){
      scores[scoreI].best = true;
    }
  }
  return scores;
}
roundSchema.methods.getScores = function getScores(players, showScores){
  var scores = [];
  var bestLineScore = 0; 
  for (var i = players.length - 1; i >= 0; i--) {
    var aPlayer = players[i];
    var lineForPlayer = this.lines.filter(function (line) 
      {return line.player[0].fbId == aPlayer.fbId; }).pop();
    if (showScores && lineForPlayer.score > bestLineScore){
      bestLineScore = lineForPlayer.score;
    } 
    scores.push({ 'player' : lineForPlayer.player[0],
                  'scoreInfo' : { 'score' : lineForPlayer.score,
                              'best'  : false},
                  'plays' : lineForPlayer.getSummarizedPlays() });
  }
  if (showScores)
  {
    var bestOfCategory = [];
    for(var scoreI in scores){
      var score = scores[scoreI];
      for(var playI in score.plays){
        var play = score.plays[playI];
        if (bestOfCategory[play.category] == undefined){
          bestOfCategory[play.category] = play.scoreInfo.score;
        } else {
          if (play.scoreInfo.score > bestOfCategory[play.category]){
            bestOfCategory[play.category] = play.scoreInfo.score;
          }  
        }
      }
    }
    for(var scoreI in scores){
      var scoreObject = scores[scoreI];
      if (scoreObject.scoreInfo.score == bestLineScore){
        scoreObject.scoreInfo.best = true;
      }
      for(var playI in scoreObject.plays){
        var play = scoreObject.plays[playI];
        if (play.scoreInfo.score == bestOfCategory[play.category]){
          play.scoreInfo.best = true;
        } 
      }
    }
  }

  return scores;
}
module.exports = mongoose.model('Round', roundSchema);
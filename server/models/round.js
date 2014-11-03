var mongoose = require('mongoose'),
    Schema   = mongoose.Schema,
    moment   = require('moment');

var Play = require('./play.js');
var Line = require('./line.js');
var Category = require('./category.js');

var roundSchema = new Schema({
  roundId : { type: Number },
  letter:   { type: String },
  status:   { type: String },  
  lines: [Line.schema], 
  notifications: [ { type: String } ],
  startTimestamp: { type: Date }
}, { _id : false });

roundSchema.methods.getStatus = function () {
  return {
    OPENED   : 'OPENED',
    CLOSED   : 'CLOSED'
  };
}

roundSchema.methods.start = function (letter) {
  this.letter = letter;
  this.status = this.getStatus().OPENED;
  this.startTimestamp = new Date();
}

roundSchema.methods.addLine = function (newLine, foundPlayer) {
  var existingLine = this.lines.filter(function (line) {return line.player.fbId == newLine.player.fbId; }).pop();
  if (existingLine===undefined){
    var myLine = new Line();
    myLine.setValues(newLine, foundPlayer);
    this.lines.push(myLine);
  }
}
roundSchema.methods.isFullyValidated = function(game){
  for (var i = this.lines.length - 1; i >= 0; i--) {
    var line = this.lines[i];
    console.log("a isFullyValidated le paso: " + game.categories);
    if (!line.isFullyValidated(game.categories, game.players.length)){
      return false;
    }
  };
  return true;
}


roundSchema.methods.setQualification = function (judge, category, isValid, lineOwner) {
  var lineToQualify = this.lines.filter(function (line) {return line.player.fbId == lineOwner; }).pop();
  lineToQualify.setQualification(judge, category, isValid);
}
roundSchema.methods.isPlaying = function () {
  return this.status === this.getStatus().OPENED;
}

roundSchema.methods.getPlayersWhoHavePlayed = function () {
  var players=[];
  for (var i = this.lines.length - 1; i >= 0; i--) {
     players.push(this.lines[i].player);
  };
  return players;
}

roundSchema.methods.checkAllPlayersFinished = function (game) {
  var playersCount = game.players.length;
  var linesCount = this.lines.filter(function (line) { return line.plays.length > 0;}).length;
  return playersCount == linesCount;
}

roundSchema.methods.hasPlayerSentHisLine = function (fbId){
  var existingLine = this.lines.filter(function (line) {
    return line.player.fbId == fbId; 
  }).pop();  
  return existingLine !== undefined;
}

roundSchema.methods.createScoresMap = function (categories){
  var scoresMap = [];
  for (var iLine = this.lines.length - 1; iLine >= 0; iLine--) {
    var line = this.lines[iLine];
    for (var iPlay = line.plays.length - 1; iPlay >= 0; iPlay--) {
      var play = line.plays[iPlay];
      var category = categories.filter(function (cat) {return cat.name.toUpperCase() == play.category.toUpperCase(); }).pop();
      var result = play.validatePlay(category, this.letter);
      if (result){
        this.addToScoresMap(scoresMap, play, iLine, iPlay); 
      } 
    };
  };
  return scoresMap;
}

roundSchema.methods.finishIfAllPlayersFinished = function (game) {
  if (this.checkAllPlayersFinished(game)){
    this.finish(game);
  }
}

roundSchema.methods.finish = function (game) {
  this.status = this.getStatus().CLOSED;
}

roundSchema.methods.getEarliestStart = function (){
  var earliestStart = new moment();
  for (var i = this.lines.length - 1; i >= 0; i--) {
    var line = this.lines[i];
    var startTimestamp = moment(line.startTimestamp);
    if (earliestStart !== undefined || startTimestamp <= earliestStart){
      earliestStart = startTimestamp;
    }
  };
  return earliestStart;
}
roundSchema.methods.setLateResults = function (isOnline){
  var bestLine;
  var bestTime = Number.MAX_VALUE;
  var earliestStart = this.getEarliestStart();
  var iLine = -1;
  var delayAllowed = (isOnline) ? 5 : 0;
  for (var i = this.lines.length - 1; i >= 0; i--) {
    var line = this.lines[i];
    if (line.hasAllWords()){
      var startTimestamp = (isOnline) ? moment(earliestStart) : moment(line.startTimestamp);
      var finishTimestamp = moment(line.finishTimestamp);
      var time = finishTimestamp.diff(startTimestamp, 'seconds');
      if (time <= bestTime){
        bestLine = line;
        bestTime = time;
        iLine = i;
      }
    }
  };

  console.log("Best time is "+bestTime);
  bestTime = bestTime + delayAllowed;
  for (var i = this.lines.length - 1; i >= 0; i--) {
    if (iLine !== i){
      var line = this.lines[i];
      line.setLateResults(bestTime);
    }
  };
}

roundSchema.methods.isClosed = function () {
  return this.status == this.getStatus().CLOSED;
}

roundSchema.methods.calculateScores = function (game, callback){
  this.setLateResults(game.getModes().ONLINE==game.mode);
  var round = this;
  var categoriesNames = [];
  for (var i = game.categories.length - 1; i >= 0; i--) {
    categoriesNames.push(game.categories[i].name);
  };
  Category.find({ 'name': { $in : categoriesNames } }, function (err, categories){
    var scoresMap = round.createScoresMap(categories);
    round.calculateAndSetPartialScores(scoresMap);
    round.calculateAndSetTotalScores();
    return callback();
  });

}

roundSchema.methods.calculateAndSetTotalScores = function (){
  console.log("calculateAndSetTotalScores");
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

roundSchema.methods.calculateAndSetPartialScores = function (scoresMap){
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
        if (dataForPlays.length == 1){ //only one player chose this word, it's unique
          var playToScore = this.getPlay(dataForPlays, 0);
          playToScore.setUniqueResult();
        } else { //more than a player chose this word, they are all repeated
          this.setAllRepeated(dataForPlays);
        }
      }
    } 
  }
}
roundSchema.methods.setAllRepeated = function (dataForPlays){
  for (var i = dataForPlays.length - 1; i >= 0; i--) {
    var playToScore = this.getPlay(dataForPlays, i);
    playToScore.setRepeatedResult();
  }
}
roundSchema.methods.getPlay = function (dataForPlays, iData){
  var iLine = dataForPlays[iData].iLine;
  var iPlay = dataForPlays[iData].iPlay;
  return this.lines[iLine].plays[iPlay];
}
roundSchema.methods.addToScoresMap = function (scoresMap, play, iLine, iPlay){
  if (!(play.category in scoresMap)){
    scoresMap[play.category] = [];
  }
  if (!(play.word in scoresMap[play.category] )){
    scoresMap[play.category][play.word] = [];
  }
  scoresMap[play.category][play.word].push({'iLine':iLine, 'iPlay':iPlay});
}

roundSchema.methods.setNotificationSentForPlayer = function (player, callback){
  console.log("Guardo notification al player "+player.name);
  this.notifications.push(player.fbId);
  callback();
}

roundSchema.methods.hasSentNotificationToPlayer = function (fbId){
  var notification = this.notifications.filter(function (notif) 
      {return notif == fbId; }).pop();
  return notification !== undefined;
}

roundSchema.methods.getSummarizedScoresForPlayers = function (players){
  var scores = [];
  var bestScore = 0;
  
  for (var i = players.length - 1; i >= 0; i--) {
    var aPlayer = players[i];
    var lineForPlayer = this.lines.filter(function (line) 
      {return line.player.fbId == aPlayer.fbId; }).pop();
    
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

roundSchema.methods.getScores = function (fbId, categories, players, showScores){
  var scores = [];
  var bestLineScore = 0; 
  for (var i = players.length - 1; i >= 0; i--) {
    var aPlayer = players[i];
    var aPlayerFbId = aPlayer.fbId;
    var lineForPlayer = this.lines.filter(function (line) {
        return line.player.fbId == aPlayerFbId; 
      }).pop();

    if (showScores && lineForPlayer.score > bestLineScore){
      bestLineScore = lineForPlayer.score;
    } 
    scores.push({ 'player' : lineForPlayer.player,
                  'scoreInfo' : { 'score' : lineForPlayer.score,
                              'best'  : false},
                  'plays' : lineForPlayer.getSummarizedPlays(fbId, categories) });
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
roundSchema.methods.getSecondsAgoStarted = function () {
  if (this.lines.length >0){
    return this.lines[0].plays.length * 20 + 1;
  }
  var now = moment(new Date());
  var startTimestamp = this.startTimestamp;
  var diff = now.diff(startTimestamp, 'seconds');
  return diff;
}
roundSchema.methods.asSummarized = function (game) {
  var categories=[];
  for (var i = game.categories.length - 1; i >= 0; i--) {
    categories.push(game.categories[i].name);
  };
  var secondsAgoStarted = (game.mode == game.getModes().ONLINE) ? this.getSecondsAgoStarted() : 0;
  return {'letter': this.letter,
          'status': this.status,
          'roundId': this.roundId,
          'gameId': game.gameId,
          'categories': categories, 
          'gameStatus': game.status,
          'gameMode':game.mode,
          'secondsAgoStarted': secondsAgoStarted
          };
}
module.exports = mongoose.model('Round', roundSchema);
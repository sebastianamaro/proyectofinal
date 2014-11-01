var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Game = require('./game.js');

var playerSchema = new Schema({
    registrationId: { type: String },
    games: [{ type: Number }],
    invitations: [{ type: Number }],
    staredCategories: [{ type: Number }],
    name: { type: String },
    email: { type: String },
    fbId: { type: String },
    dateInserted: { type: Date}

});

playerSchema.methods.setValues = function (player) {
  this.registrationId = player.registrationId;
  this.name = player.name;
  this.email = player.email;
  this.fbId = player.fbId;
  return this;
}

playerSchema.methods.starCategory= function (category)
{
    this.staredCategories.push(category.id);
    this.save(function(err) {
          if(!err) {
            console.log('Saved staredCategory'+category.id);
          } else {
            console.log('ERROR: ' + err);
          }
        });
}


playerSchema.methods.unstarCategory= function (category)
{
   var categoryIndex=this.staredCategories.indexOf(category.id);
    if ( categoryIndex> -1) {
        this.staredCategories.splice(categoryIndex, 1);
        this.save(function(err) {
              if(!err) {
                console.log('removed unstaredCategory'+category.id);
              } else {
                console.log('ERROR: ' + err);
              }
            });
      }  
}

playerSchema.methods.getGames = function () {
  var games = [];
  for (var i = this.games.length - 1; i >= 0; i--) {
    var gameId = this.games[i];
    var gameToReturn = Game.findOne({ 'gameId': gameId}, function (err, game){
      return { "gameId": game.gameId, "status": game.status };
    });
    games.push(gameToReturn);
  };
  console.log(games);
  return games;
}
playerSchema.methods.getName = function () {
  return this.name;
}
playerSchema.methods.getFirstName = function () {
  return this.name.split(' ')[0];
}
playerSchema.methods.sendInvitationToGameIfPossible = function(gameId, from){
  if (this.fbId == from.fbId){
    return; //wont send the invitation to the creator
  }
  var invitationAlreadySent = this.invitations.filter(function (invitation) {
              return invitation == gameId;
            }).pop();

  if (invitationAlreadySent !== undefined){
    return; //wont send the invitation to sb already invited. this validation is just in case
  }
  var notification = new Notification();
  notification.setRegistrationId(this.registrationId);
  notification.setValues({'game_id':gameId, 'player': from.name});
  notification.setMessageType(notification.getMessagesTypes().INVITATION);
  var player = this;
  notification.send(function(err){
    if (err){
      console.log("Error when send invitations");
    } else {
      player.addInvitationToGame(gameId);
    }
  });
}

playerSchema.methods.addInvitationToGame = function(gameId){
  var fbId=this.fbId;
  this.invitations.push(gameId);
  this.save(function(err) {
          if(!err) {
            console.log('Saved player with new invitation '+fbId);
          } else {
            console.log('ERROR: ' + err);
          }
        });
}
playerSchema.methods.addGame = function(gameId){
  this.games.push(gameId);
}

playerSchema.methods.removeInvitation = function(gameId){
  var index = this.invitations.indexOf(gameId);
  if (index > -1) {
    this.invitations.splice(index, 1);
  }
}

module.exports = mongoose.model('Player', playerSchema);


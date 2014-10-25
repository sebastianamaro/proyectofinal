module.exports = function(app) {
 
  var Game = require('../models/game.js');
  var Round = require('../models/round.js');
  var Player = require('../models/player.js');
  var Category = require('../models/category.js');
  var url = require('url');

  hasValue = function(parameter){
    return parameter !== '' && parameter !== undefined && parameter !== '?';
  }
  getPlayersPerDate = function(req,res){
    Player.aggregate(
      { $group : {_id : { month: { $month: "$dateInserted" }, day: { $dayOfMonth: "$dateInserted" }, year: { $year: "$dateInserted" } },amount: { $sum: 1 }}},
      { $project: { _id: 1, amount: 1 }}, 
      function(err, players) {
        res.send(players, 200); 
      });
  }

  getPlayers = function(req, res) {
    var criteria = url.parse(req.url,true).query;
    
    var name = criteria.name;
    var email = criteria.email;
    var fbId = criteria.fbId;

    var criteriaMongo = {};
    if (hasValue(name)){
      var nameLike = new RegExp(name.replace("?",""), 'i');
      criteriaMongo['name'] = nameLike;
    }
    if (hasValue(email)){
      var emailLike = new RegExp(email.replace("?",""), 'i');
      criteriaMongo['email'] = emailLike;
    }
    if (hasValue(fbId)){
      criteriaMongo['fbId'] = fbId;
    }
    Player.find(criteriaMongo, function (err, players){
        if (err) return res.send(err, 500);
        if (!players) return res.send('Players not found', 404);   
        res.send(players, 200); //add error manipulation
      });
  }
  getGamesForPlayer = function(req, res) {
    Player.findOne({ fbId: req.params.id }, function (err, player){
      	if (err) return res.send(err, 500);
      	if (!player) return res.send('Player not found', 404);   
      	
      	Game.find({ gameId: { $in : player.games } }, function(err, games) {
          var statusCodes = {
                      STATUS_NOT_STARTED : -2,
                      NO_PREVIOUS_ROUNDS : -1,
                      RESULTS_AVAILABLE: 1
                    };
	        var gamesToReturn = [];
	        if (games){
		        for (var i = games.length - 1; i >= 0; i--) {
		        	var game = games[i];
              var playersNameArray =game.getOtherPlayersFirstNames();
              var selectedFriendsNameArray =game.getSelectedFriendsFirstNames();
              var currentRound;
              var playerHasPlayed = false;
              var isFirstRound = true;
              var statusCode;
              
              if (!game.hasStarted()){
                statusCode = statusCodes.STATUS_NOT_STARTED;
              } else {
                currentRound = game.getPlayingRound();
                
                //hay ronda abierta?
                if (currentRound!=undefined) {              
                  playerHasPlayed = currentRound.hasPlayerSentHisLine(player.fbId);

                  //la que esta abierta, es la primera?
                  if (game.rounds.length == 1){
                    isFirstRound = true;
                    //el jugador ya jugo?
                    statusCode = (playerHasPlayed) ? statusCodes.RESULTS_AVAILABLE : statusCodes.NO_PREVIOUS_ROUNDS; 
                    //al ser la primera, no tengo anterior
                  } else {
                    isFirstRound = false;
                    statusCode = statusCodes.RESULTS_AVAILABLE;
                  }    
                } else {
                  //hay rondas cerradas?
                  if (game.rounds.length >= 1){
                      statusCode = statusCodes.RESULTS_AVAILABLE;
                      isFirstRound = false;
                  } else {
                      statusCode  = statusCodes.NO_PREVIOUS_ROUNDS;
                      isFirstRound = true;                     
                  }
                }
              }

              gamesToReturn.push({"gameId":game.gameId, 
                    "status": game.status,
                    "mode": game.mode ,
                    "categoriesType": game.categoriesType ,
                    "players": playersNameArray,
                    "isFirstRound":isFirstRound, 
                    "statusCode":statusCode,
                    "playerHasPlayedCurrentRound":playerHasPlayed,
                    "randomPlayersCount" : game.randomPlayersCount,
                    "selectedFriends":selectedFriendsNameArray});
		        };
	        }
	      	res.send(gamesToReturn, 200); //add error manipulation
	    });
    });
  }

deleteFinishedGame = function(req,res){
    console.log("holaaa");
    console.log("req.params.id " + req.params.id);
     Player.findOne({ fbId: req.params.id }, function (err, player){
        if (err) return res.send(err, 500);
        if (!player) return res.send('Player not found', 404);   
        console.log("existe el player");
        var finishedStatus = new Game().getStatus().FINISHED;
        var allPlayersRejectedStatus = new Game().getStatus().ALL_PLAYERS_REJECTED;
        var statuses = [allPlayersRejectedStatus,finishedStatus];

        Game.findOne({ 'gameId': req.params.game, 'status': { $in :  statuses} }, function(err, game) {
            if (err) return res.send(err, 500);
            if (!game) return res.send('Game not found', 404);

            var index = player.games.indexOf(req.params.game);
            player.games.splice(index,1);
            player.save(function(err) {
            if(!err) {
              console.log('Player '+player.getName()+' gameId '+ req.params.game+ ' deleted');
            } else {
              console.log('ERROR en delete game: ' + err);
            }
          });
          res.send('Game deleted', 200); //add error manipulation
      });
    });
  }

  getCategoriesForPlayer= function(req, res)  {

    Player.findOne({ fbId: req.params.id }, function (err, player){
        if (err) return res.send(err, 500);
        if (!player) return res.send('Player not found', 404);   
      
        var summarizedCategories =[];
        Category.find({}).sort('-name').exec(function (err, categories){
            if (err) return res.send(err, 500);
            if (!categories) return res.send('Categories not found', 404);   
            
            for (var i = categories.length - 1; i >= 0; i--) {
               
                  var summarizedCategory = categories[i].asSummarized();

                  
                  if (player.staredCategories.indexOf(categories[i].id) > -1)
                      summarizedCategory['isStared'] = true;     
                  else
                      summarizedCategory['isStared'] = false;

                 summarizedCategories.push(summarizedCategory);
             
            }

             res.send(summarizedCategories, 200); //add error manipulation
          });
     });
  }

  alterStaredCategories = function( req, res){
      Player.findOne({ fbId: req.params.id }, function (err, player){
          if (err) return res.send(err, 500);
          if (!player) return res.send('Player not found', 404);   
        
          Category.findOne( { id: req.params.categoryId }, function (err, category) {
              if (err) return res.send(err, 500);
              if (!category) return res.send('category not found', 404);   

              var categoryIndex=player.staredCategories.indexOf(category.id);
                if ( categoryIndex> -1)
                    player.unstarCategory(category);
                else
                    player.starCategory(category);

              return res.send('alterStaredCategories done!', 200);
          });
    });
  }

  getInvitationsForPlayer = function(req, res) {
      Player.findOne({ fbId: req.params.id }, function (err, player){
          if (err) return res.send(err, 500);
          if (!player) return res.send('Player not found', 404);   
          
          Game.find({ gameId: { $in : player.invitations } }, function(err, games) {
            var gamesToReturn = [];
            if (games){
              for (var i = games.length - 1; i >= 0; i--) {
                var game = games[i];

                gamesToReturn.push(game.asSummarized());
              };
            }
            console.log("Found : "+gamesToReturn.length + " invitations for player " + player.getName());  
            res.send(gamesToReturn, 200); //add error manipulation
          });

      });
    }

    createPlayer = function(req, res)
    {

      Player.findOne({fbId: req.body.fbId }, function (err, player){
        
        if (player)
          player.registrationId = req.body.registrationId;
        else{
          var player = new Player();
          player.setValues(req.body);
          player.dateInserted = new Date();
        }
        
          player.save(function(err) {
            if(!err) {
              console.log('Player '+player.getName()+' inserted');
            } else {
              console.log('ERROR en createPlayer: ' + err);
            }
          });

          return res.send('Player '+player.getName()+' inserted', 200);
      });
    }
}
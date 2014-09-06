module.exports = function(app) {
 
  var Game = require('../models/game.js');
  var Round = require('../models/round.js');
  var FullRound = require('../models/fullRound.js');
  var Player = require('../models/player.js');
 var Category = require('../models/category.js');

  getGamesForPlayer = function(req, res) {
    Player.findOne({ fbId: req.params.id }, function (err, player){
      	if (err) return res.send(err, 500);
      	if (!player) return res.send('Player not found', 404);   
      	
      	Game.find({ gameId: { $in : player.games } }, function(err, games) {
	        var gamesToReturn = [];
	        if (games){
		        for (var i = games.length - 1; i >= 0; i--) {
		        	var game = games[i];
		        	gamesToReturn.push({"gameId":game.gameId, "status": game.status});
		        };
	        }
	      	res.send(gamesToReturn, 200); //add error manipulation
	    });
    });
  }

  getCategoriesForPlayer= function(req, res)  {

    Player.findOne({ fbId: req.params.id }, function (err, player){
        if (err) return res.send(err, 500);
        if (!player) return res.send('Player not found', 404);   
      
        var summarizedCategories =[];
        Category.find({}, function (err, categories){
                if (err) return res.send(err, 500);
                if (!categories) return res.send('Categories not found', 404);   
                
                for (var i = categories.length - 1; i >= 0; i--) {
                   var summarizedCategory = categories[i].asSummarized();

                if (player.categories.indexOf(categories[i].id) > -1)
                    summarizedCategory['isStared'] = true;     
                else
                    summarizedCategory['isStared'] = false;

                   summarizedCategories.push(summarizedCategory);
                };
                
              });


          res.send(summarizedCategories, 200); //add error manipulation
     });
  }

  alterStaredCategories = function( req, res){
      Player.findOne({ fbId: req.params.id }, function (err, player){
          if (err) return res.send(err, 500);
          if (!player) return res.send('Player not found', 404);   
        
          Category.findOne( { id: req.params.categoryId }, function (err, category) {
              if (err) return res.send(err, 500);
              if (!category) return res.send('category not found', 404);   

              var categoryIndex=player.categories.indexOf(categories[i].id);
                if ( categoryIndex> -1)
                    player.categories.splice(categoryIndex, 1);
                else
                    player.categories.push(category.id);

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
          console.log("encontre invitaciones: "+gamesToReturn.length);  
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
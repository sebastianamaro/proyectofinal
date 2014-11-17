var express = require("express"),
    app     = express(),
    http    = require("http"),
    server  = http.createServer(app),
    mongoose = require('mongoose');

routes = require('./routes/game.js')(app);
routes = require('./routes/player.js')(app);
routes = require('./routes/category.js')(app);

app.configure(function () {
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(express.json({limit: '50mb', parameterLimit:'30000'}));
  app.use(express.urlencoded({limit: '50mb', parameterLimit:'30000'}));
  app.use(app.router);
});

app.post('/game', createGame); 
app.put('/game/:id/invitation', respondInvitation); 
app.get('/game/:id', getGame);
app.get('/game/:id/scores', getGameScores);
app.put('/game/:id/round', alterRound); 
app.put('/game/:id/round/qualification/:fbId', setQualification); 
app.get('/game/:id/round', getRound);
app.get('/game/:id/round/scores/for/:fbId', getLatestRoundScores);
app.get('/game/:id/round/:roundId/scores', getRoundScores);
app.post('/player', createPlayer);
app.get('/player?:criteria', getPlayers); 
app.get('/player/createdPerDate', getPlayersPerDate); 
app.get('/player/:id/game', getGamesForPlayer);
app.delete('/player/:id/game/:game', deleteFinishedGame);
app.get('/player/:id/invitations', getInvitationsForPlayer);
app.put('/player/:id/category/:categoryId', alterStaredCategories);
app.get('/player/:id/category', getCategoriesForPlayer);
app.get('/category?:criteria', getCategories); 
app.get('/category/hitsPerType', getCategoryHitsPerType); 
app.get('/category/:id', getCategory); 
app.put('/category/:id', editCategory); 
app.put('/category/:name/:word/reportAsValid', reportWordAsValid); 
app.put('/category/:id?report', reportCategory); 
app.post('/category', createCategory); 
app.delete('/category/:id', removeCategory); 

app.put('/category/:id/word/:word', acceptReportedWordAsValid); 
app.delete('/category/:id/word/:word', removeAcceptedWordFromCategory); 
app.delete('/category/:id/word/:word?reported', removeReportedWordFromCategory); 

var connection = mongoose.connect('mongodb://localhost:30000', function(err, res) {
  if(err) {
    console.log('ERROR: connecting to Database. ' + err);
  } else {
    console.log('Connected to Database');
  }
});

server.listen(8080, function() {
  console.log("Node server running on http://localhost:8080");
});



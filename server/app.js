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
  app.use(app.router);
});

app.post('/game', createGame); 
app.put('/game/:id/invitation', respondInvitation); 
app.get('/game/:id', getGame);
app.get('/game/:id/scores', getGameScores);
app.put('/game/:id/round', alterRound); 
app.get('/game/:id/round', getRound);
app.get('/game/:id/round/:roundId/scores', getRoundScores);
app.get('/player/:id/game', getGamesForPlayer);
app.get('/player/:id/invitations', getInvitationsForPlayer);
app.post('/player', createPlayer);
app.put('/player/:id/category/:categoryId', alterStaredCategories);
app.get('/category?:criteria', getCategories); 
app.get('/category/:id', getCategory); 
app.put('/category/:id', editCategory); 
app.put('/category/:id/:word', addWordToCategory); 
app.put('/category/:id/:word?report', addReportedWordToCategory); 
app.put('/category/:id?report', reportCategory); 
app.delete('/category/:id/:word?reject', removeReportedWordFromCategory); 
app.delete('/category/:id/:word', removeAcceptedWordFromCategory); 
app.delete('/category/:id', removeCategory); 
app.post('/category', createCategory); 

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



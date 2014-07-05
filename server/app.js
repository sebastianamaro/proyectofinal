var express = require("express"),
    app     = express(),
    http    = require("http"),
    server  = http.createServer(app),
    mongoose = require('mongoose'); 

routes = require('./routes/game.js')(app);

app.configure(function () {
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(app.router);
});

app.put('/game/:id/round', alterRound); 
app.get('/game/:id/round', getRound);
app.post('/game', createGame); 
app.get('/test/:id', sendNotifications); 

mongoose.connect('mongodb://localhost:30000', function(err, res) {
  if(err) {
    console.log('ERROR: connecting to Database. ' + err);
  } else {
    console.log('Connected to Database');
  }
});

server.listen(8080, function() {
  console.log("Node server running on http://localhost:3000");
});



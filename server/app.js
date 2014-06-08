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

app.put('/game/:id/round', alterRound); //va a ser un put, dejo get a efectos de debug, el status seguramente venga en el cuerpo
app.get('/game/:id/round', getRound); //va a ser un put, dejo get a efectos de debug, el status seguramente venga en el cuerpo

mongoose.connect('mongodb://localhost:30000', function(err, res) {
  if(err) {
    console.log('ERROR: connecting to Database. ' + err);
  } else {
    console.log('Connected to Database');
  }
});

server.listen(3000, function() {
  console.log("Node server running on http://localhost:3000");
});



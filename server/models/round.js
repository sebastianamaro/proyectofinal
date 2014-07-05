var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var Play = require('./play.js');
var Line = require('./line.js');

var roundSchema = new Schema({
  roundId : { type: Number },
  letter:   { type: String },
  status:   { type: String },
  lines: [Line.schema] 
});

roundSchema.methods.start = function start(letter) {
  this.letter = letter;
  this.status = 'PLAYING';
  return this;
 }

roundSchema.methods.finish = function finish() {
  this.status = "CLOSED";
  return this;
 }

roundSchema.methods.addLine = function addLine(newLine) {
  var existingLine = this.lines.filter(function (line) {return line.player == newLine.player; }).pop();
  if (!existingLine){
    var myLine = new Line();
    myLine.setValues(newLine);
    this.lines.push(myLine);
  }
}

roundSchema.methods.isPlaying = function isPlaying() {
  return this.status === 'PLAYING'
}
 
module.exports = mongoose.model('Round', roundSchema);
var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var playSchema = new Schema({
  category:   { type: String },
  timestamp: { type: Date }, 
  word: { type: String }
});

playSchema.methods.setValues = function setValues(play) {
  this.category = play.category;
  this.timestamp = play.timestamp;
  this.word = play.word;
  return this;
}

module.exports = mongoose.model('Play', playSchema);

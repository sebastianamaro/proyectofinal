var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var playerSchema = new Schema({
    registrationId: { type: String },
    games: [{ type: Number }]
});

playerSchema.methods.setValues = function setValues(player) {
  this.registrationId = player;
  return this;
}
module.exports = mongoose.model('Player', playerSchema);


var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var playerSchema = new Schema({
    registrationId: { type: String }
}); 

playerSchema.methods.setValues = function setValues(player) {
  this.registrationId = player.registrationId;
}

module.exports = mongoose.model('Player', playerSchema);


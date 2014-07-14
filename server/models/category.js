var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var categorySchema = new Schema({
  name : { type: String },
  acceptedWords:   [ { type: String } ],
});

categorySchema.methods.isWordValid = function isWordValid(word, category) {
  return true;//for now just a dummy
}

module.exports = mongoose.model('Category', categorySchema);
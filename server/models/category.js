var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var categorySchema = new Schema({
  name : { type: String },
  acceptedWords:   [ { type: String } ],
});

categorySchema.methods.isWordValid = function isWordValid(play) {
  return true;//for now just a dummy
}
categorySchema.methods.setCategory = function setCategory(category) {
  return this;//for now just a dummy
}
module.exports = mongoose.model('Category', categorySchema);
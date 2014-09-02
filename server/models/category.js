var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var categorySchema = new Schema({
  id:   {type: Number},
  name : { type: String },
  acceptedWords:   [ {type: String} ],
  reportedWords:   [ {type: String} ],
  hits:   {type: Number},
  isFixed:   {type: Boolean},
  isReported:   {type: Boolean}
});

categorySchema.methods.isWordValid = function isWordValid(word, category) {
  return true;//for now just a dummy
}

module.exports = mongoose.model('Category', categorySchema);
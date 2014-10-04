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

categorySchema.methods.isWordValid = function (word, category) {
  return true;//for now just a dummy
}

categorySchema.methods.setValues = function (category) {
	this.name=category.name;
	this.acceptedWords=category.acceptedWords;
	this.isStared=category.isStared;
	this.isReported=category.isReported;
	this.isFixed=category.isFixed;
}

categorySchema.methods.asSummarized = function asSummarized() {
	return {
    	'id': this.id,
		'name': this.name,
		'isReported': this.isReported,
		'isFixed': this.isFixed
	};
}


module.exports = mongoose.model('Category', categorySchema);
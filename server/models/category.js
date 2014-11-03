var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var categorySchema = new Schema({
  id:   {type: Number},
  name : { type: String },
  acceptedWords:   [ {type: String} ],
  reportedWords:   [ { count: { type : Number } , word: { type : String } } ],
  hits:   {type: Number},
  isFixed:   {type: Boolean},
  isReported:   {type: Boolean}
});

categorySchema.methods.isWordValid = function (word, category) {
  var accepted = this.acceptedWords.filter(function (acceptedWord) {
    return acceptedWord == word;
  }).pop();
  
  return accepted !== undefined;
}
categorySchema.methods.setValues = function (category) {
	this.name=category.name;
	this.acceptedWords=category.acceptedWords;
	this.isStared=category.isStared;
	this.isReported=category.isReported;
	this.isFixed=category.isFixed;
}

categorySchema.methods.getReportedWord = function(search)
{
  return this.reportedWords.filter(function (reportedWord) {
    return reportedWord.word == search;
  }).pop();
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
module.exports = function(app) {
 
  var Category = require('../models/category.js');
  var url = require('url');
  hasValue = function(parameter){
    return parameter !== '' && parameter !== undefined && parameter !== '?';
  }
  getCategories = function(req, res) {
    var criteria = url.parse(req.url,true).query;
    var name = criteria.name;
    var fixed = criteria.isFixed;
    var reported = criteria.isReported;

    var criteriaMongo = {};
    if (hasValue(name)){
      var nameLike = new RegExp(name, 'i');
      criteriaMongo['name'] = nameLike;
    }
    if (hasValue(fixed)){
      criteriaMongo['isFixed'] = fixed == '1' ? true : false;
    }
    if (hasValue(reported)){
      criteriaMongo['isReported'] = reported == '1' ? true : false;
    }
    Category.find(criteriaMongo, function (err, categories){
        if (err) return res.send(err, 500);
        if (!categories) return res.send('Categories not found', 404);   
        res.send(categories, 200); //add error manipulation
      });
  }
  getCategory = function(req, res) {
    Category.findOne({id:req.params.id}, function (err, category){
        if (err) return res.send(err, 500);
        if (!category) return res.send('Category not found', 404);   
        res.send(category, 200); //add error manipulation
      });
  }
  arrayContains = function(array, search){
    return array.indexOf(search) >= 0;
  }
  editCategory = function(req, res) {
    Category.findOne({id:req.params.id}, function (err, category){
      if (err) return res.send(err, 500);
      if (!category) return res.send('Category not found', 404);   
      
      category.name = req.body.name;
      category.isFixed = req.body.isFixed;
      category.isReported = req.body.isReported;
      category.hits = req.body.hits;

      category.acceptedWords = [];
      if (req.body.acceptedWords.values !== undefined){
        for (var i = req.body.acceptedWords.values.length - 1; i >= 0; i--) {
          var wordReq = req.body.acceptedWords.values[i];
          var valueWord = wordReq.value.toUpperCase().trim();
          if (!arrayContains(category.acceptedWords,valueWord)){
            category.acceptedWords.push(valueWord);
          }
        };
      }
      category.save(function(err) {
        if(!err) {
          console.log('Saved category with id '+category.id);
          return res.status(204).send();  
        } else {
          console.log('ERROR: Save category failed. ' + err);
          return res.send('Save category failed',500);  
        }
      });
      });
  }
  createCategory = function(req,res){
    Category.findOne({}).sort('-id').exec(function(err, doc) { 
      var largerId;
      if (doc){
        largerId = doc.id + 1;  
      } else {
        largerId = 1;
      }
      var category = new Category();
      category.id = largerId;
      category.name = req.body.name;
      category.isFixed = req.body.isFixed;
      category.isReported = false;
      category.hits = 0;

      category.reportedWords = [];
      category.acceptedWords = [];
      if (req.body.acceptedWords.values !== undefined){
        for (var i = req.body.acceptedWords.values.length - 1; i >= 0; i--) {
          var wordReq = req.body.acceptedWords.values[i];
          var valueWord = wordReq.value.toUpperCase().trim(); 
          if (!arrayContains(category.acceptedWords,valueWord )) {
            category.acceptedWords.push(valueWord);
          }
        };
      }      
      category.save(function(err) {
        if(!err) {
          console.log('Created category with id '+largerId);
        } else {
          console.log('ERROR: ' + err);
        }
      });
      return res.send({'id':largerId}, 203);
     });
  }
}
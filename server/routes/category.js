module.exports = function(app) {
 
  var Category = require('../models/category.js');
  var url = require('url');
  hasValue = function(parameter){
    return parameter !== '' && parameter !== undefined && parameter !== '?';
  }
  removeAcceptedWordFromCategory = function(req, res) {
    Category.findOne({id:req.params.id}, function (err, category){
      if (err) return res.send(err, 500);
      if (!category) return res.send('Category not found', 404);   

      var valueWord = req.params.word.toUpperCase().trim();
      if (!hasValue(valueWord)){
        return res.status(204).send();  
      }
      if (arrayContains(category.acceptedWords,valueWord)){
        var index = category.acceptedWords.indexOf(valueWord);
        category.acceptedWords.splice(index, 1);
      }

      category.save(function(err) {
        if(!err) {
          console.log('Removed accepted word to category with id '+category.id);
          return res.status(204).send();  
        } else {
          console.log('ERROR: Save category failed. ' + err);
          return res.send('Save category failed',500);  
        }
      });
    });
  }
  removeReportedWordFromCategory = function(req, res) {
    Category.findOne({id:req.params.id}, function (err, category){
      if (err) return res.send(err, 500);
      if (!category) return res.send('Category not found', 404);   

      var valueWord = req.params.word.toUpperCase().trim();
      if (!hasValue(valueWord)){
        return res.status(204).send();  
      }
      var reportedWord = category.getReportedWord(valueWord);
      if (reportedWord != undefined){
        var index = category.reportedWords.indexOf(reportedWord);
        category.reportedWords.splice(index, 1);
      }

      category.save(function(err) {
        if(!err) {
          console.log('Removed reported word to category with id '+category.id);
          return res.status(204).send();  
        } else {
          console.log('ERROR: Save category failed. ' + err);
          return res.send('Save category failed',500);  
        }
      });
    });
  }
  reportWordAsValid = function(req, res)
  {
    Category.findOne({name:req.params.name}, function (err, category){
      if (err) return res.send(err, 500);
      if (!category) return res.send('Category not found', 404);   

      var valueWord = req.params.word.toUpperCase().trim();
      if (!hasValue(valueWord)){
        return res.status(204).send();  
      }
      var reportedWord = category.getReportedWord(valueWord);
      if (reportedWord == undefined){
        category.reportedWords.push( { "word":valueWord, "count":1 });
      }
      else
      {
        reportedWord.count = reportedWord.count + 1;
      }

      category.save(function(err) {
        if(!err) {
          console.log('Added word to reported words in category with id '+category.id);
          return res.status(204).send();  
        } else {
          console.log('ERROR: Save category failed. ' + err);
          return res.send('Save category failed',500);  
        }
      });
    });

  }
  acceptReportedWordAsValid = function(req, res){
    Category.findOne({id:req.params.id}, function (err, category){
      if (err) return res.send(err, 500);
      if (!category) return res.send('Category not found', 404);   

      var valueWord = req.params.word.toUpperCase().trim();
      if (!hasValue(valueWord)){
        return res.status(204).send();  
      }
      var reportedWord = category.getReportedWord(valueWord);

      if (reportedWord != undefined){
        if (!arrayContains(category.acceptedWords,valueWord) && hasValue(valueWord)){
          category.acceptedWords.push(valueWord);
        }
        var index = category.reportedWords.indexOf(reportedWord);
        category.reportedWords.splice(index, 1);
      }

      category.save(function(err) {
        if(!err) {
          console.log('Added word to category with id '+category.id);
          return res.status(204).send();  
        } else {
          console.log('ERROR: Save category failed. ' + err);
          return res.send('Save category failed',500);  
        }
      });
    });
  }
  getCategoryHitsPerType = function(req,res){
    Category.aggregate(  
      { $group: { _id: '$isFixed', hits: { $sum: '$hits' }}}, 
      { $project: { _id: 1, hits: 1 }}, 
      function(err, categories) {
        res.send(categories, 200); 
      });

  }
  getCategories = function(req, res) {
    var criteria = url.parse(req.url,true).query;
    var name = criteria.name;
    var fixed = criteria.isFixed;
    console.log("value isFixed:"+fixed);
    var reported = criteria.isReported;

    var criteriaMongo = {};
    if (hasValue(name)){
      var nameLike = new RegExp(name.replace("?",""), 'i');
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
  removeCategory = function(req,res) {
      Category.findOne({id:req.params.id}, function (err, category){
        if (err) return res.send(err, 500);
        if (!category) return res.send('Category not found', 404);   
        Category.remove({id:req.params.id}, function (err){
          if (err){
            console.log('ERROR: Remove category failed');
            return res.send('Remove category failed',500);  
          }
          console.log('Removed category.');
          res.send(category, 200); //add error manipulation
        });
      });
  }

  arrayContains = function(array, search){
    return array.indexOf(search) >= 0;
  }

  reportCategory = function(req, res) {
    Category.findOne({id:req.params.id}, function (err, category){
      if (err) return res.send(err, 500);
      if (!category) return res.send('Category not found', 404);   
      
      category.isReported = true;
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
  editCategory = function(req, res) {
    Category.findOne({id:req.params.id}, function (err, category){
      if (err) return res.send(err, 500);
      if (!category) return res.send('Category not found', 404);   
      
      category.name = req.body.name;
      category.isFixed = req.body.isFixed;
      category.isReported = req.body.isReported;

      category.acceptedWords = [];
      if (req.body.acceptedWords!== undefined){
        for(var wordI in req.body.acceptedWords){
          var wordReq = req.body.acceptedWords[wordI];
          var valueWord = wordReq.toUpperCase().trim();
          if (!arrayContains(category.acceptedWords,valueWord) && hasValue(valueWord)){
            category.acceptedWords.push(valueWord);
          }
        };
      }
      category.save(function(err) {
        if(!err) {
          console.log('Saved category with id '+category.id);
          return res.status(200).send();  
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
      category.name = req.body.name.toUpperCase().trim();
      category.isFixed = req.body.isFixed;
      category.isReported = false;
      category.hits = 0;

      category.reportedWords = [];
      category.acceptedWords = [];
      if (req.body.acceptedWords !== undefined){
        for(var wordI in req.body.acceptedWords){
          var wordReq = req.body.acceptedWords[wordI];
          var valueWord = wordReq.toUpperCase().trim(); 
          if (!arrayContains(category.acceptedWords,valueWord) && hasValue(valueWord)) {
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
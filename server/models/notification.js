Notification = function(){
	this.apiKey = 'AIzaSyDnzPmtbKIvPEPAXF8AsRdqyiDqdT2RDlQ';
	return this;
}

Notification.prototype.send =  function(callback){
	//TODO validate all required attributes are set
	var GCM = require('gcm').GCM;
	var gcm = new GCM(this.apiKey);
	var keys = Object.keys(this.values);
	var body = {};

	for (var i = keys.length - 1; i >= 0; i--) {
		var key = keys[i];
		body[key] = this.values[key];
	};
	var message = {
	    registration_id: this.registrationId,
	    data: JSON.stringify(body)
	};
	console.log(message);	
	gcm.send(message, function(err, messageId){
	    if (err) {
	        console.log("Something has gone wrong!");
	        callback(new Error("Something has gone wrong"));
	    } else {
	        console.log("Sent with message ID: ", messageId);
	        callback(null, messageId);
	    }
	});
}

Notification.prototype.setRegistrationId= function (registrationId){
	this.registrationId = registrationId;
}

Notification.prototype.setValues= function (values){
	this.values = values;
}

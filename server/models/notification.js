Notification = function(){
	this.apiKey = 'AIzaSyDnzPmtbKIvPEPAXF8AsRdqyiDqdT2RDlQ';
	return this;
}

Notification.prototype.send =  function(callback){
	//TODO validate all required attributes are set
	var GCM = require('gcm').GCM;
	var gcm = new GCM(this.apiKey);

	var message = {
	    registration_id: this.registrationId,
	    'data.key1': 'value1',
    	'data.key2': 'value2'
	};
	
	gcm.send(message, function(err, messageId){
	    if (err) {
	        console.log("Something has gone wrong!");
	        //callback("esta mal");
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

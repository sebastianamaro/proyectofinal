function Notification(){
	this.apiKey = 'AIzaSyDnzPmtbKIvPEPAXF8AsRdqyiDqdT2RDlQ';
}

Notification.prototype.send =  function(callback){
	//TODO validate all required attributes are set
	var GCM = require('gcm').GCM;
	var gcm = new GCM(this.apiKey);

	var message = {
	    registration_id: this.registrationId,
	};

	for(var key in this.values){
		message.set(key, this.values[key]);
	}
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

Notification.prototype.setRegistrationId(registrationId){
	this.registrationId = registrationId;
}

Notification.prototype.setValues(values){
	this.values = values;
}

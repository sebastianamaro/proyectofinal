function Notification(){
	this.apiKey = 'AIzaSyDnzPmtbKIvPEPAXF8AsRdqyiDqdT2RDlQ';
}

Notification.prototype.send(){
	//TODO validate all required attributes are set
	var GCM = require('gcm').GCM;
	var gcm = new GCM(this.apiKey);

	var message = {
	    registration_id: this.registrationId,
	    collapse_key: this.collapseKey
	};

	for(var key in this.values){
		message.set(key, this.values[key]);
	}
	gcm.send(message, function(err, messageId){
	    if (err) {
	        console.log("Something has gone wrong!");
	    } else {
	        console.log("Sent with message ID: ", messageId);
	    }
	});
}

Notification.prototype.setRegistrationId(registrationId){
	this.registrationId = registrationId;
}
Notification.prototype.setCollapseKey(collapseKey){
	this.collapseKey = collapseKey;
}
Notification.prototype.setValues(values){
	this.values = values;
}

// import firebase functions modules
const functions = require('firebase-functions');
//import admin module
const admin = require('firebase-admin');

var serviceAccount = require("./service-account.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://lazypizza-a625d.firebaseio.com"
});


// Listens for new messages added to messages/:pushId
exports.pushNotification = functions.database.ref('/marketplace/{pushId}').onWrite( event => {

    console.log('Push notification event triggered');
    //  Grab the current value of what was written to the Realtime Database.


    if (event.data.previous.exists()) {
        return;
      }

    var valueObject = event.data.val();
    console.log(event.data.val());

  // Create a notification
    const payload = {
        notification: {
            title:'New item added to the MarketPlace',
            body: valueObject.src + ' | '+ valueObject.item,
            sound: "default"
        },
    };

  //Create an options object that contains the time to live for the notification and the priority
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24
    };


    return admin.messaging().sendToTopic("pushNotifications", payload, options);
});



exports.acceptedOrder = functions.database.ref('/marketplace/{pushId}').onWrite( event => {

    console.log(event.data.val());
    if(event.data.previous.val().accepted === '0') {
        console.log("0");
        if(event.data.previous.val().accepted === event.data.val().accepted){
            return;

        }
        else{
            

            console.log("1");
            console.log(event.data.val().accepted);


            var email=event.data.val().email.replace(/ /g,'');
            email1=email.replace(/,/g, '.')
            console.log(email);
            var db = admin.database();
            var ref = db.ref("users/"+email+"/token");

            // Attach an asynchronous callback to read the data at our posts reference
            ref.on("value", function(snapshot) {
               customToken=snapshot.val();
                   console.log(customToken);
                        var Token=[customToken];
                        console.log(Token);
                        console.log(Token);
                        let payload = {
                            notification: {
                              title: 'Your order has been picked Up.',
                              body: `Your order will be delivered in ${event.data.val().expected} minutes!`,
                              sound: "default"
                            }
                          };
                        admin.messaging().sendToDevice(Token, payload)
                          .then(function(response) {
                            // See the MessagingDevicesResponse reference documentation for
                            // the contents of response.
                            console.log("Successfully sent message:", response);
                          })
                          .catch(function(error) {
                            console.log("Error sending message:", error);
                          });

            }, function (errorObject) {
              console.log("The read failed: " + errorObject.code);
            });

            // customToken=adaRef.key
            // console.log(customToken);          

        }
    }
    else{

      if(event.data.previous.val().accepted === event.data.val().accepted){
            return;

        }
        else{
            

            console.log("1");
            console.log(event.data.val().accepted);


            var email=event.data.val().email.replace(/ /g,'');
            email1=email.replace(/,/g, '.')
            console.log(email);
            var db = admin.database();
            var ref = db.ref("users/"+email+"/token");

            // Attach an asynchronous callback to read the data at our posts reference
            ref.on("value", function(snapshot) {
               customToken=snapshot.val();
                   console.log(customToken);
                        var Token=[customToken];
                        console.log(Token);
                        console.log(Token);
                        let payload = {
                            notification: {
                              title: 'Your order was cancelled.',
                              body: `Added back to the MarketPlace.`,
                              sound: "default"
                            }
                          };
                        admin.messaging().sendToDevice(Token, payload)
                          .then(function(response) {
                            // See the MessagingDevicesResponse reference documentation for
                            // the contents of response.
                            console.log("Successfully sent message:", response);
                          })
                          .catch(function(error) {
                            console.log("Error sending message:", error);
                          });

            }, function (errorObject) {
              console.log("The read failed: " + errorObject.code);
            });

            // customToken=adaRef.key
            // console.log(customToken);          

        }


        
    }



});


exports.sctransfer = functions.database.ref('/users/{pushId}').onWrite( event => {



    console.log(event.data.val().SC);

   
        if(event.data.previous.val().SC!=event.data.val().SC)
        {
            if(event.data.previous.val().SC>event.data.val().SC)
            {

			            	const payload = {
			        notification: {
			            title:'Your SC Balance has been deducted.',
			            body: `Your new balance is ${event.data.val().SC}`,
			            sound: "default"
			        },
			    };

			    Token=[event.data.val().token]

            	admin.messaging().sendToDevice(Token, payload)
                          .then(function(response) {
                            // See the MessagingDevicesResponse reference documentation for
                            // the contents of response.
                            console.log("Successfully sent message:", response);
                          })
                          .catch(function(error) {
                            console.log("Error sending message:", error);
                          });
     

            }
            else
            {

            	            	const payload = {
			        notification: {
			            title:'You Have Received Super Coins!.',
			            body: `Your new balance is ${event.data.val().SC}`,
			            sound: "default"
			        },
			    };

			    Token=[event.data.val().token]

            	admin.messaging().sendToDevice(Token, payload)
                          .then(function(response) {
                            // See the MessagingDevicesResponse reference documentation for
                            // the contents of response.
                            console.log("Successfully sent message:", response);
                          })
                          .catch(function(error) {
                            console.log("Error sending message:", error);
                          });

            }
                       }

          else
          {
            return;
          }
  



});


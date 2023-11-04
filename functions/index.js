/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const {onCall} = require("firebase-functions/v2/https");
// const {logger} = require("firebase-functions/v2");
// const {onRequest} = require("firebase-functions/v2/https");

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

const functions = require("firebase-functions");
// Import and initialize the Firebase Admin SDK.
const admin = require("firebase-admin");
admin.initializeApp();

const {messaging} = require("firebase-admin");

exports.sendNotification = onCall(request => {
  sendMessage(request.data.token, request.data.title, request.data.body)
});

exports.sendNotificationToCaretakerOnAssignListener =
  functions.database.ref("/caretakers/{caretakerPhoneNumber}/users/{newUser}")
  .onWrite((change, context) => {
      const userDataAfter = change.after.val();
      const newUser = context.params.newUser;
      if (userDataAfter) {
        sendMessageToCaretaker(newUser)
      }
      return null;
  });

function sendMessageToCaretaker(newUser) {
  const newUserRef = admin.database().ref(`/users/${newUser}`)
  newUserRef.once('value', snapshot => {
    const newUserData = snapshot.val();
    if (newUserData) {
      const title = "New user";
      const newUserName = newUserData["name"];
      const newUserToken = newUserData["firebaseToken"];
      const body = `The user ${newUserName} with phone number ${newUser} ` +
        `has added you as their caretaker!`
      sendMessage(newUserToken, title, body)
    }
  });
}

function sendMessage(token, title, body) {
  const message = {
    notification: {
      title: title,
      body: body,
    },
    token: token,
  };
  messaging().send(message)
    .then((response) => {
      console.log("Successfully sent message:", response);
    })
    .catch((error) => {
      console.log("Error sending message:", error);
    });
}
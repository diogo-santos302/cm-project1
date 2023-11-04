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
  sendNotification(request.data.token, request.data.title, request.data.body)
});

exports.sendDataMessage = onCall(request => {
  sendDataMessage(request.data.token, request.data.data)
});

function sendDataMessage(token, data) {
  const dataObject = JSON.parse(data);
  const message = {
    data: dataObject,
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

exports.sendNotificationToCaretakerOnAssignListener =
  functions.database.ref("/caretakers/{caretakerPhoneNumber}/users/{newUser}")
  .onWrite((change, context) => {
      const userDataAfter = change.after.val();
      if (userDataAfter) {
        sendMessageToCaretaker(context.params.newUser, context.params.caretakerPhoneNumber);
      }
      return null;
  });

async function sendMessageToCaretaker(newUser, caretaker) {
  const title = "New user";
  const newUserName = await getNewUserName(newUser);
  if (!newUserName) {
    return;
  }
  const caretakerToken = await getCaretakerToken(caretaker);
  if (!caretakerToken) {
    return;
  }
  const body = `The user ${newUserName} with phone number ${newUser} ` +
    `has added you as their caretaker!`;
  sendNotification(caretakerToken, title, body);
}

async function getNewUserName(newUser) {
  const newUserData = await getUserData(newUser);
  if (!newUserData) {
    return null;
  }
  return newUserData["name"];
}

async function getCaretakerToken(caretaker) {
  const caretakerData = await getUserData(caretaker);
  if (!caretakerData) {
    return null;
  }
  return caretakerData["firebaseToken"];
}

async function getUserData(phoneNumber) {
  const ref = admin.database().ref(`/users/${phoneNumber}`);
  try {
    const snapshot = await ref.once('value');
    const data = snapshot.val();
    if (!data) {
      return null;
    }
    return data;
  } catch (error) {
    console.error("Error fetching data: ", error);
    return null;
  }
}

function sendNotification(token, title, body) {
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
const {onCall} = require("firebase-functions/v2/https");
const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

const {messaging} = require("firebase-admin");
const {setGlobalOptions} = require("firebase-functions/v2");

setGlobalOptions({maxInstances: 10});

exports.sendNotification = onCall((request) => {
  sendNotification(request.data.token, request.data.title, request.data.body);
});

exports.sendDataMessage = onCall((request) => {
  sendDataMessage(request.data.token, request.data.data);
});

/**
* @param {String} token
* @param {String} data
*/
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
          sendMessageToCaretaker(
              context.params.newUser,
              context.params.caretakerPhoneNumber,
          );
        }
        return null;
      });

/**
* @param {String} newUser
* @param {String} caretaker
*/
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

/**
* @param {String} newUser
*/
async function getNewUserName(newUser) {
  const newUserData = await getUserData(newUser);
  if (!newUserData) {
    return null;
  }
  return newUserData["name"];
}

/**
* @param {String} caretaker
*/
async function getCaretakerToken(caretaker) {
  const caretakerData = await getUserData(caretaker);
  if (!caretakerData) {
    return null;
  }
  return caretakerData["firebaseToken"];
}

/**
* @param {String} phoneNumber
*/
async function getUserData(phoneNumber) {
  const ref = admin.database().ref(`/users/${phoneNumber}`);
  try {
    const snapshot = await ref.once("value");
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

/**
* @param {String} token
* @param {String} title
* @param {String} body
*/
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

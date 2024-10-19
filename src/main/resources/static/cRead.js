

// TWO WAYS OF SENDING A MESSAGE: PRESS ENTER OR CLICK SUBMIT
document.getElementById('submitButton').addEventListener('click', sendMessage);

document.getElementById('messageBox').addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // Prevents default behavior of a line break in the textarea
        sendMessage();
    }
});


// FUNCTION FOR SENDING A MESSAGE
function sendMessage() {
    const username= document.getElementById('username').value.trim();
    const message = document.getElementById('messageBox').value.trim();

    // IF USER TRIES TO SEND A BLANK MESSAGE
    if (!message) {
        alert("Please enter a message.");
        return;
    }

    // CREATE A MESSAGE OBJECT
    const chatMessage = {
        username: username,
        content: message
    };

    // SEND MESSAGE OBJECT TO SERVER
    fetch('/api/messages', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(chatMessage)
    })
    .then(response => response.json())
    .then(data => {
        // UPDATE OUTPUT WITH THE NEW MESSAGE
        displayMessage(data);
        // CLEAR THE TYPE MESSAGE BOX AFTER SUBMISSION
        document.getElementById('messageBox').value = '';
    })
    .catch(error => console.error('Error:', error));
}


// FUNCTION TO DISPLAY A MESSAGE
function displayMessage(message) {
    const output = document.getElementById('output');
    const formattedDate = new Date(message.messageDate).toLocaleString();

    if (!document.querySelector(`.message-${message.id}`)) {
        output.innerHTML += `<p><span class="username">${message.username}  </span><span class="timestamp">(${formattedDate}):</span>  ${message.content}</p>`;
    }
}


// FUNCTION TO FETCH ALL STORED MESSAGES FROM THE SERVER
function fetchMessages() {
    fetch (`/api/messages`)
        .then(response => response.json())
        .then(messages => {
            // CLEAR THE CURRENT OUTPUT AND DISPLAY ALL MESSAGES
            const output = document.getElementById('output');
            output.innerHTML = '';
            messages.forEach(displayMessage);
        })
        .catch(error => console.error('Error fetching messages:', error));
}


// FETCH MESSAGES WHEN THE PAGE LOADS
window.onload = function () {
    fetchMessages();
};


// POLL FOR NEW MESSAGES EVERY 500 MILLISECONDS (NOT IDEAL / CPU INTENSIVE)
setInterval(fetchMessages, 500);









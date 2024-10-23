document.addEventListener('DOMContentLoaded', () => {

//  BACK BUTTON REDIRECT
    document.getElementById('backButton').addEventListener('click', () => {
        window.location.href = `/user/${userId}`;
    });


    const channelId = document.getElementById('channelId').value;
    const channelName = document.getElementById('channelName').value;
    const userId = document.getElementById('userId').value;
    const username = document.getElementById('username').value.trim();
    const name = document.getElementById('user.name').value.trim();
    const messageBox = document.getElementById('messageBox');

// TWO WAYS OF SENDING A MESSAGE: PRESS ENTER OR CLICK SUBMIT
    document.getElementById('submitButton').addEventListener('click', () => {
        sendMessage(channelId, userId, messageBox.value.trim());
    });

    document.getElementById('messageBox').addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault(); // Prevents default behavior of a line break in the textarea
            sendMessage(channelId, userId, messageBox.value.trim());
        }
    });


// FUNCTION FOR SENDING A MESSAGE
    function sendMessage(channelId, userId, messageContent) {
        // CREATE A MESSAGE OBJECT
        const message = {
            content: messageContent,
            sender: {
                username: username,
                userId: userId,
                name: name
            },
            channel: {
                channelName: channelName,
                channelId: channelId
            }
        };

        // IF USER TRIES TO SEND A BLANK MESSAGE
        if (!messageContent) {
            alert("Please enter a message.");
            return;
        }

        // SEND MESSAGE OBJECT TO SERVER
        fetch('/api/messages', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(message)
        })
            .then(response => response.json())
            .then(data => {
                // UPDATE OUTPUT WITH THE NEW MESSAGE
                displayMessage(data);
                // CLEAR THE TYPE MESSAGE BOX AFTER SUBMISSION
                messageBox.value = '';
            })
            .catch(error => console.error('Error:', error));
    }


// FUNCTION TO DISPLAY A MESSAGE
    function displayMessage(message) {
        const output = document.getElementById('output');
        const formattedDate = new Date(message.messageDate).toLocaleString();

        if (message.sender && message.sender.username) {
            if (!document.querySelector(`.message-${message.messageId}`)) {
                output.innerHTML += `
                <p class="message-${message.messageId}">
                    <span class="username">${message.sender.username}</span> 
                    <span class="timestamp">(${formattedDate}): </span>  
                    ${message.content}
                </p>`;
            } else {
                console.error('Invalid message structure:', message);
            }
        } else {
            console.error('Invalid message structure:', message);
        }
    }


// FUNCTION TO FETCH ALL STORED MESSAGES FROM THE SERVER
    function fetchMessages() {
        console.log('Fetching messages for channelId:', channelId);
        fetch(`/api/messages?channelId=${channelId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(messages => {
                console.log('Fetched messages:', messages);
                // CLEAR THE CURRENT OUTPUT AND DISPLAY ALL MESSAGES
                const output = document.getElementById('output');
                output.innerHTML = '';
                messages.forEach(displayMessage);
            })
            .catch(error => console.error('Error fetching messages:', error))
    }


// FETCH MESSAGES WHEN THE PAGE LOADS
    window.onload = function () {
        fetchMessages();
    };


// POLL FOR NEW MESSAGES EVERY 500 MILLISECONDS (NOT IDEAL / CPU INTENSIVE)
// setInterval(fetchMessages, 500);


})
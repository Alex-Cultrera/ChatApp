document.getElementById('submitButton').addEventListener('click', sendMessage);

document.getElementById('messageBox').addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // Prevents default behavior of a line break in the textarea
        sendMessage();
    }
});

function sendMessage() {
    const username= document.getElementById('username').value.trim();
    const message = document.getElementById('messageBox').value.trim();

    if (!message) {
        alert("Please enter a message.");
        return;
    }

    // message object
    const chatMessage = {
        username: username,
        content: message
    };

    // send message object to server
    fetch('/api/messages', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(chatMessage)
    })
    .then(response => response.json())
    .then(data => {
        // update output with the new message
        displayMessage(data);
        // Clear the box after submission
        document.getElementById('messageBox').value = '';
    })
    .catch(error => console.error('Error:', error));
}


// function to display a message
function displayMessage(message) {
    const output = document.getElementById('output');
    output.innerHTML += `<p><strong>${message.username}:</strong> ${message.content}</p>`;
}

function fetchMessages() {
    fetch ('/api/messages')
        .then(response => response.json())
        .then(messages => {
            // clear current output and display all messages
            const output = document.getElementById('output');
            output.innerHTML = '';
            messages.forEach(displayMessage);
        })
        .catch(error => console.error('Error fetching messages:', error));
}

// Poll for new messages every 500 milliseconds
setInterval(fetchMessages, 500);

// initial fetch when the page loads
window.onload = function () {
    fetchMessages();
};
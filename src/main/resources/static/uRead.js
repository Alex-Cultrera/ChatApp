
// REDIRECT USER TO LOGIN PAGE WHEN CLICKING SIGN OUT BUTTON
const button = document.querySelector("#signOutButton")
button.onclick = function() {
    window.location.href = "/login";
};

document.getElementById('createChannelButton').addEventListener('click', createChannel);

function createChannel() {
    const channelName = document.getElementById('channelName').value.trim();

    if (!channelName) {
        alert("Please enter a channel name.");
        return;
    }

    const channelData = {
        channelName: channelName,
    };

    fetch('/api/channels', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(channelData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to create channel');
            }
            return response.json();
        })
        .then(newChannel => {
            addChannelToDropdown(newChannel); // Add the new channel to the dropdown
            document.getElementById('channelName').value = ''; // Clear the input
        })
        .catch(error => console.error('Error creating channel:', error));
}

function addChannelToDropdown(channel) {
    const channelSelect = document.getElementById('channelSelect');
    const option = document.createElement('option');
    option.value = channel.channelId;
    option.textContent = channel.channelName;
    channelSelect.appendChild(option);
}

function fetchChannels() {
    fetch('/api/channels')
        .then(response => response.json())
        .then(channels => {
            const channelSelect = document.getElementById('channelSelect');
            channelSelect.innerHTML = '';

            channels.forEach(channel => {
                const option = document.createElement('option');
                option.value = channel.channelId;
                option.textContent = channel.channelName;
                channelSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching channels:', error));
}

window.onload = function () {
    fetchChannels(); // Fetch and populate channel list
};
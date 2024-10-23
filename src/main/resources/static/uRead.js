
// REDIRECT USER TO LOGIN PAGE WHEN CLICKING SIGN OUT BUTTON
const button = document.querySelector("#signOutButton")
button.onclick = function() {
    window.location.href = "/login";
};


document.getElementById('createChannelButton').addEventListener('click', createChannel);


// FUNCTION TO CREATE NEW CHANNEL
function createChannel() {
    const channelName = document.getElementById('channelName').value.trim();
    const userId = document.getElementById('userId').value;
    const username = document.getElementById('user.username').value;
    const name = document.getElementById('user.name').value;

    if (!channelName) {
        alert("Please enter a channel name.");
        return;
    }

    if (channelName) {
        checkIfChannelNameExists(channelName).then(channelNameExists => {
            if (channelNameExists) {
                window.location.href = `/user/${userId}`;
            }
        })
    }

    const channel = {
        channelName: channelName,
        createdBy: {
            userId: userId,
            username: username,
            name: name
        }
    };


    fetch('/api/channels', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(channel)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to create channel');
            }
            return response.json();
        })
        .then(newChannel => {
            console.log('New Channel:', newChannel); // Log the new channel
            // ADD THE NEW CHANNEL TO THE DROPDOWN
            addChannelToDropdown(newChannel);
            fetchChannels();
            // CLEAR THE OUTPUT
            document.getElementById('channelName').value = '';
        })
        .catch(error => console.error('Error creating channel:', error));
}


// FUNCTION TO CHECK IF CHANNEL NAME ALREADY EXISTS
function checkIfChannelNameExists (channelName) {
    return fetch('/channel/exists', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({channelName: channelName})
    })
        .then((responseEntity) => responseEntity.json())
        .then((data) => {
            if (data === true) {
                alert("Channel Name already exists. Choose a different Channel Name.")
                return true;
            } else {
                return false;
            }
        })
        .catch(error => {
            console.log(error);
            return false;
        })
}



// FUNCTION TO ADD CHANNEL TO DROPDOWN MENU
function addChannelToDropdown(channel) {
    const channelSelect = document.getElementById('channelSelect');
    const option = document.createElement('option');
    option.value = channel.channelId;
    option.textContent = channel.channelName;
    channelSelect.appendChild(option);
}


// FUNCTION TO FETCH ALL CHANNELS FROM SERVER
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


// FETCH AND POPULATE CHANNEL LIST WHEN PAGE RELOADS
window.onload = function () {
    fetchChannels();
};


// FUNCTION TO OPEN SELECTED CHANNEL
function openChannel() {
    const channelSelect = document.getElementById('channelSelect');
    const selectedChannelId = channelSelect.value;
    const userId = document.getElementById('userId').value;

    if (!selectedChannelId) {
        alert("Please select a channel to open.");
        return;
    }

    // REDIRECT TO THE SELECTED CHANNEL
    window.location.href = `/user/${userId}/channel/${selectedChannelId}`;
}
var button = document.querySelector("#goToLogIn")

button.onclick = function() {
    window.location.href = "/login";
};

 var createUserButton = document.querySelector("#createUserButton")
 var users = []

createUserButton.addEventListener('click', () => {
    var username = document.querySelector("#username")
    var password = document.querySelector("#password")
    var name = document.querySelector("#fullName")
    var credentialsComplete = true

    if (username.value === '' || password.value === '' || name.value === '') {
        alert("Please enter username, password, and name.")
        credentialsComplete = false
    }

    if (credentialsComplete) {
        var theUsername = username.value
        checkIfUsernameExists(theUsername).then(usernameExists => {
            if (usernameExists) {
                window.location.href = "/register";
            } else {
                alert("username is unique")
            }
        })
    }
})

function checkIfUsernameExists (theUsername) {
    return fetch('/user/exists', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({username: theUsername})
    })
        .then((responseEntity) => responseEntity.json())
        .then((data) => {
            if (data === true) {
                alert("Username already exists. Choose a different username.")
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
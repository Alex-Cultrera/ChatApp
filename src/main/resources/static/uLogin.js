var button = document.querySelector("#goToRegister")

button.onclick = function() {
    window.location.href = "/register";
};

 var logInButton = document.querySelector("#logInButton")

logInButton.addEventListener('click', () => {
    var username = document.querySelector("#username")
    var password = document.querySelector("#password")

    if (username.value === '' || password.value === '') {
        alert("Please enter a username and a password")
    } else {
        console.log("Inputs look valid, proceed with form submission")
        var user = {
            "username" : username.value,
            "password" : password.value
        }

        // fetch("user/exists", {
        //         method: 'POST',
        //         headers: {
        //             'Content-Type': 'application/json'
        //         },
        //         body: JSON.stringify({username: username.value, password: password.value})
        //     }
        //         .then((response) => response.json())
        //         .then((data) => {
        //             console.log(data)
        //          }),
        // ).then(r =>)

        sessionStorage.setItem("user", JSON.stringify(user));
        const mySessionData = JSON.parse(sessionStorage.getItem("user"));

        console.log(mySessionData);


    }
})


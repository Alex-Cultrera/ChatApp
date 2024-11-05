const mySessionData = JSON.parse(sessionStorage.getItem("user"));
console.log(mySessionData);

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

        sessionStorage.setItem("user", JSON.stringify(user));

        console.log(mySessionData);


    }
})


var button = document.querySelector("#goToRegister")

button.onclick = function() {
    window.location.href = "http://localhost:8080/register";
};

 var logInButton = document.querySelector("#logInButton")
 var users = []

logInButton.addEventListener('click', () => {
    var username = document.querySelector("#username")
    var password = document.querySelector("#password")

    if (username.value == '' || password.value == '') {
        alert("Please enter a username and a password")
    } else {
        console.log("Inputs look valid, proceed with form submission")
        var user = {
            "username" : username.value,
            "password" : password.value
        }
        users.push(user)
    }
})
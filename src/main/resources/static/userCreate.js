var button = document.querySelector("#goToLogIn")

button.onclick = function() {
    window.location.href = "http://localhost:8080/login";
};

 var createUserButton = document.querySelector("#createUserButton")
 var users = []

createUserButton.addEventListener('click', () => {
    var username = document.querySelector("#username")
    var password = document.querySelector("#password")
    var name = document.querySelector("#fullName")

    if (username.value == '' || password.value == '' || name.value == '') {
        alert("Please enter username, password, and name.")
    } else {
        console.log("Inputs look valid, proceed with form submission")
        var user = {
            "username" : username.value,
            "password" : password.value,
            "name" : name.value
        }
        users.push(user)
    }
})

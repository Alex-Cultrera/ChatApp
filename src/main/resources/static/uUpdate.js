const mySessionData = JSON.parse(sessionStorage.getItem("user"));
console.log(mySessionData);

// FUNCTION TO REDIRECT USER TO LOGIN PAGE IF OPENING NEW TAB/WINDOW
function redirectToLogin() {
    if (!mySessionData) {
        alert("Session ended. Please login again.")
        window.location.href = `/login`;
    }
}

redirectToLogin();
function validateLogin() {

    let loginField = document.getElementById("login-field");
    let passField = document.getElementById("pass-field");

    let regex = /^[A-Za-z0-9]{4,}$;

    if (regex.test(loginField.value)) {
        return false;

    }

    if (!regex.test(passField.value)) {
        return false;
    }

    return true;
}
function validateRegister() {

    let loginField = document.getElementById("login-field");
    let passField = document.getElementById("pass-field");

    let regex = /^[A-Za-z0-9]{4,}$;

    // Bramka NOR dla sprawdzenia poprawności loginu i hasła
    // !(!A || !B)
    return !(!regex.test(loginField.value) || !regex.test(passField.value));
}
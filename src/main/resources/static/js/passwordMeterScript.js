// The error message (passwords not matching) should not be shown when loading the view
let p_errorMessage = $('#p_errorMessage');

// The passwordStrengthBar should be displayed with a warning at first
let p_passwordStrengthBar = $('#p_passwordStrengthBar');
p_passwordStrengthBar.addClass('bg-warning');

// Fade icon out at first as the password is initially empty and
// thus doesn't contain any of the necessary components to be safe
let p_iconPasswordIsSafe = $('#p_icon3');
p_iconPasswordIsSafe.fadeOut(0);

// Fade out the icon, which symbolizes that both passwords match (and the password is secure)
let p_iconPasswordsMatchAndIsSafe = $('#p_icon4');
p_iconPasswordsMatchAndIsSafe.fadeOut(0);

// The passwords have to be checked for being identical
let p_passwordInputField = $('#p_password');
let p_repeatedPasswordInputField = $('#p_passwordRepeated');

// Determines whether to show or hide the password
let p_passwordVisible = false;
let p_repeatedPasswordVisible = false;

let p_missingForHigherPasswordStrength = $('#p_missingForHigherPasswordStrength');
let p_listOfMissingPasswordCharacteristics = $('#p_missingPasswordCharacteristics');
let p_passwordCharacteristicsText = $('#p_passwordCharacteristicsText');

// When the first letter is entered for the password, we display a list of characteristics the password has to
// fulfill (length, must contain special characters, must contain numbers, must contain uppercase and lowercase
// letters).
function p_showMissingPasswordCharacteristics() {
    p_listOfMissingPasswordCharacteristics.fadeIn();
}

// Show or hide the password. Is called 'onclick' and thus only changes whenever the user clicks on the button.
// The icon is determined by '#passwordButtonIcon' and as a result we have to change the used class to determine
// what the icon should look like. 'bi-eye-slash' is a closed eye and represents that the password is not shown
// in text, whereas 'bi-eye' means just that.
function p_showOrHidePassword(passwordInputFieldToHide) {
    // We check whether the click happened on the button related to the password-input or the repeated-password-input
    if (passwordInputFieldToHide === "password") {
        if (p_passwordVisible) {
            p_passwordVisible = false;
            p_passwordInputField.attr("type", "password");
            // $('#showHidePassword').removeClass('glyphicon-eye-open').addClass('glyphicon-eye-close');
            p_passwordInputField.attr("type", "password");
            $('#p_passwordButtonIcon').removeClass('bi-eye').addClass('bi-eye-slash');
        } else {
            p_passwordInputField.attr("type", "text");
            //$('#showHidePassword').removeClass('glyphicon-eye-close').addClass('glyphicon-eye-open');
            p_passwordInputField.attr("type", "text");
            $('#p_passwordButtonIcon').removeClass('bi-eye-slash').addClass('bi-eye');
            p_passwordVisible = true;
        }
    } else {
        // We do the same thing for the repeated password:
        if (p_repeatedPasswordVisible) {
            p_repeatedPasswordVisible = false;
            p_repeatedPasswordInputField.attr("type", "password");
            $('#p_repeatedPasswordButtonIcon').removeClass('bi-eye').addClass('bi-eye-slash');
        } else {
            p_repeatedPasswordInputField.attr("type", "text");
            $('#p_repeatedPasswordButtonIcon').removeClass('bi-eye-slash').addClass('bi-eye');
            p_repeatedPasswordVisible = true;
        }
    }
}

// Setup variables used to determine the password strength, hide the alert, which is displayed if the password
// is too shor and set the progress bar's (passwordStrengthBar) width to 0, so that the bar isn't visible.
let p_passwordStrength = 0
let p_alertIfPasswordIsTooShort = $('#p_passwordTooShort');
p_alertIfPasswordIsTooShort.fadeOut(0);
p_passwordStrengthBar.css('width', 0+'%').attr('aria-valuenow', 0);
let p_isLongEnough = false;
let p_containsUppercaseAndLowercase = false;
let p_containsNumbers = false;
let p_containsSpecialCharacters = false;
let p_list = document.getElementById("p_missingPasswordCharacteristics");

// Use RegEx patterns to determine the password strength
// We check whether the password is at least 8 characters long, contains uppercase and
// lowercase letters, contains numbers and special characters. If any of those properties
// are fulfilled, the progress bar (passwordStrengthBar) increases in value and changes its
// color to suggest a safer password.
function p_determinePasswordStrength() {
    let p_password = p_passwordInputField.val();

    // Reset these values. Potentially, a character was deleted, which means that even if the variable was previously
    // true, we have to check again whether this still holds up. Thus, the variables are set to false and are
    // changed to true, in case it still holds up.
    p_isLongEnough = false;
    p_containsUppercaseAndLowercase = false;
    p_containsNumbers = false;
    p_containsSpecialCharacters = false;
    p_passwordStrength = 0;

    // Length should be higher than or equal to 8
    if (p_password.length >= 8) {
        p_isLongEnough = true;
        p_alertIfPasswordIsTooShort.fadeOut(200);
        p_removeElementFromList("Password is too short.");
    } else {
        p_alertIfPasswordIsTooShort.fadeIn(200);
        p_addElementToList("Password is too short.", 1);
    }

    // The password should contain lowercase and uppercase letters
    if (p_password.match(/(([a-zA-Z0-9]|[!.:;_&()?@/{}'#*+,^°<>-])*)/) && (p_password.match(/[a-z]+/) && p_password.match(/[A-Z]+/))) {
        p_containsUppercaseAndLowercase = true;
        p_removeElementFromList("Must contain upper- and lowercase letters.");
    } else {
        p_addElementToList("Must contain upper- and lowercase letters.", 2);
    }

    // The password should contain at least one number
    if (p_password.match(/(([a-zA-Z0-9]|[!.:;_&()?@/{}'#*+,^°<>-])*)/) && p_password.match(/[0-9]+/)) {
        p_containsNumbers = true;
        p_removeElementFromList("Must contain numbers.");
    } else {
        p_addElementToList("Must contain numbers.", 3);
    }

    // The password should contain a special character
    if (p_password.match(/(([a-zA-Z0-9]|[!.:;_&()?@/{}'#*+,^°<>-])*)/) && p_password.match(/[!.:;_&()?@/{}'#*+,^°<>-]+/)) {//(password.match(/[!+*.:,;_"§$%&/()=?{}@]/)) {
        p_containsSpecialCharacters = true;
        p_removeElementFromList("Must contain special characters.");
    }  else {
        p_addElementToList("Must contain special characters.", 4);
    }


    //TODO: If password doesn't match with [a-zA-Z0-9!.:;_&()?@/{}'#*+,^°<>-], it contains illegal characters.

    //TODO: Here, we will later check whether the entered password contains the username, first name or last name
    //if (password.match(/[!+*.:,;_"§$%&/()=?{}@a-zA-Z0-9]*(firstName|lastName|userName)+[!+*.:,;_"§$%&/()=?{}@a-zA-Z0-9]*/)) {
    //  alert("contains User name");
    //}

    // Determine the overall password-strength
    p_passwordStrength = p_isLongEnough + p_containsUppercaseAndLowercase + p_containsNumbers + p_containsSpecialCharacters;
    p_adjustPasswordStrengthBar();

}

function p_adjustPasswordStrengthBar() {
    // Depending on the password strength, the progress bar, which represents the password strength, changes its color
    // and increases/decreases in width.
    if (p_passwordStrength === 1) {
        p_passwordStrengthBar.removeClass('bg-warning').removeClass('bg-success').addClass('bg-danger'); // color: red
        p_passwordStrengthBar.css('width', 20 + '%').attr('aria-valuenow', 20); // progress bar is almost empty
        p_iconPasswordIsSafe.fadeOut(100);
        p_iconPasswordsMatchAndIsSafe.fadeOut(100);
    } else if (p_passwordStrength >= 2 && p_passwordStrength < 3) {
        p_passwordStrengthBar.removeClass('bg-danger').removeClass('bg-success').addClass('bg-warning'); // color: orange/yellow
        p_passwordStrengthBar.css('width', 35 + '%').attr('aria-valuenow', 35);
        p_iconPasswordIsSafe.fadeOut(100);
        p_iconPasswordsMatchAndIsSafe.fadeOut(100);
    } else if (p_passwordStrength >= 3 && p_passwordStrength < 4) {
        p_passwordStrengthBar.removeClass('bg-danger').removeClass('bg-success').addClass('bg-warning'); // color: orange/yellow
        p_passwordStrengthBar.css('width', 65 + '%').attr('aria-valuenow', 65);
        p_iconPasswordIsSafe.fadeOut(100);
        p_iconPasswordsMatchAndIsSafe.fadeOut(100);
    } else if (p_passwordStrength >= 4) {
        p_passwordStrengthBar.removeClass('bg-warning').removeClass('bg-danger').addClass('bg-success'); // color: green
        p_passwordStrengthBar.css('width', 100 + '%').attr('aria-valuenow', 100); // progress bar is full
        p_iconPasswordIsSafe.fadeIn(200);

        // Fade in the second icon if both passwords also match
        if (p_passwordInputField.val() === p_repeatedPasswordInputField.val()) {
            p_iconPasswordsMatchAndIsSafe.fadeIn(200);
        } else  {
            p_iconPasswordsMatchAndIsSafe.fadeOut(100);
        }
    }

    // Fade in/out the text for the list which describes the missing criteria for a secure password
    if (p_passwordStrength < 4) {
        p_passwordCharacteristicsText.fadeIn(200);
    } else {
        p_passwordCharacteristicsText.fadeOut(100);
    }

    // In case the password length is shorter than 8 characters, the passwordStrengthBar is covered in red no matter
    // the security determined above.
    if (p_password.length < 8) {
        p_passwordStrengthBar.removeClass('bg-warning').removeClass('bg-success').addClass('bg-danger');
    }
}

function p_addElementToList(elementText, idNr) {
    let nodeExists = false;
    for (i = 0; i < p_list.childNodes.length; i++) {
        if (p_list.childNodes[i].textContent === elementText) {
            nodeExists = true;
        }
    }
    if (!nodeExists) {
        let listElement = document.createElement('li');
        listElement.setAttribute('class','item');
        listElement.innerHTML = elementText;
        listElement.setAttribute("id", "element" + idNr);
        list.appendChild(listElement);
    }
}

function p_removeElementFromList(elementText) {
    for (i = 0; i < p_list.childNodes.length; i++) {
        if (p_list.childNodes[i].textContent === elementText) {
            p_list.removeChild(p_list.childNodes[i]);
        }
    }
}

// When clicking the login button, we check whether both entered passwords are identical
function p_validate() {
    // Get the passwords as Strings
    let p_password = String(p_passwordInputField.val());
    let p_repeatedPassword = String(p_repeatedPasswordInputField.val());

    // If the passwords don't match, stop the submission process, delete the input in the repeatedPasswordInputField
    // and fade in the error message.
    if (p_password !== p_repeatedPassword) {
        event.preventDefault();
        event.stopPropagation();
        p_errorMessage.fadeIn(200);
        p_repeatedPasswordInputField.val("");
        return false;
    } else if (p_password.length < 8 || p_passwordStrength < 4) {
        event.preventDefault();
        event.stopPropagation();
        p_repeatedPasswordInputField.val("");

        // If the passwordStrength is lower tha 4, which means the progress bar is not colored in green
        // and filled up entirely, we display an alert. The content of the alert changes, depending on what
        // component for a secure password needs is still missing.
        if (p_passwordStrength < 4) {
            p_missingForHigherPasswordStrength.fadeIn(200);
            if (!p_containsUppercaseAndLowercase) {
                p_missingForHigherPasswordStrength.text("Your password must contain upper- and lowercase letters.");
            } else if (!p_containsNumbers) {
                p_missingForHigherPasswordStrength.text("Your password must contain numbers.");
            } else if (!p_containsSpecialCharacters) {
                p_missingForHigherPasswordStrength.text("Your password must contain special characters.");
            }
        }

        return false;
    }
    return true;
}

window.onload = function() {
    p_listOfMissingPasswordCharacteristics.fadeOut(0);
    p_passwordCharacteristicsText.fadeOut(0);
    p_missingForHigherPasswordStrength.fadeOut(0);
    p_errorMessage.fadeOut(0);
};
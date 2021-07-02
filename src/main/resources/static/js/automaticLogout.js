// Log out the user after two minutes of inactivity
// Activity: key-press, moving the mouse or pressing a mouse-button

// When the page is loaded, we start a timer
// Loading a page indicates activity, so it's fair to start a timer.

// After 115 seconds, we call startLogOutTimer(), which displays
// a countdown for the last five seconds. startLogOutTimer() then
// sets up a new timer and calls itself again after one second.
// This happens until the timer reaches zero. Then, the actual
// logout is performed by calling logout().
let standardCounterTime = 1000 * 115;
let counter = standardCounterTime; // (re)set the counter to its default value. Stores the milliseconds.
let interval; // Here, we store the interval so we can stop it in case the user performs an action.
let text = $('#timer'); // Used update the timer's text.
let lastFiveSecondsCounter = 5; // Keeps track of how often we need to call startLogOutTimer(). != milliseconds!

// When the page is loaded, we start the timer
window.onload = function() {
    counter = standardCounterTime;
    interval = setInterval(startLogOutTimer, counter)
}

// Reset Timer on keyup:
window.onkeyup = function() {
    resetTimer()
}

// Reset Timer on keydown:
window.onkeydown = function() {
    resetTimer()
}

// Reset Timer on keydown:
window.onkeypress = function() {
    resetTimer()
}

// Reset Timer on mouse-wheel:
window.onmousewheel = function() {
    resetTimer()
}

// Reset Timer on mouse-move:
window.onmousemove = function() {
    resetTimer()
}

// Reset Timer on mousedown:
window.onmousedown = function() {
    resetTimer()
}

// Reset Timer on mouseup:
window.onmouseup = function() {
    resetTimer()
}

// Here, we reset the timer if the user has performed an action and thus shows that they are active.
// For this, we need to restore the initial values, stop the current timer (clearInterval) and start a
// new one.
function resetTimer() {
    lastFiveSecondsCounter = 5
    counter = standardCounterTime
    text.text("");
    clearInterval(interval);
    interval = setInterval(startLogOutTimer, counter)
}

// If 115 seconds have passed, startLogOoutTimer is called for the first time.
// In case lastFiveSecondsCounter, which stores how often we have called startLogOutTimer(),
// is greater than zero, we update the timer's text, clear the current interval,
// reduce the number of times we still need to call startLogOutTimer() and set a new
// interval. This ensures that after another second has passed, startLogOutTimer() is called again.
// This happens until lastFiveSecondsCounter reaches zero. Then, we know that we need to perform a logout.
// We set a new timer and log the user out after one second, which is done by calling logout().
// Note: If the user is on a page containing "/login" ("/login", "/login?error", "/login?logout"), no
// logout should be performed.
function startLogOutTimer() {
    if (!(containsSubstring(document.URL, "/login"))) {
        if (lastFiveSecondsCounter > 0) {
            text.text("Timer: " + lastFiveSecondsCounter);
            clearInterval(interval);
            interval = setInterval(startLogOutTimer, 1000);
            lastFiveSecondsCounter--;
        }

        if (lastFiveSecondsCounter <= 0) {
            interval = setInterval(logout, 1000);
        }
    }
}

// Logout the user.
function logout() {
    lastFiveSecondsCounter = 5
    counter = standardCounterTime
    clearInterval(interval);
    if (!(containsSubstring(document.URL, "/login"))) {
        window.location = "/login?logout";
    }
}


// Checks, whether substring is found within mainString.
// Used to check whether the URL which the user is currently on contains "login".
// If it does, there is no need to perform a logout as the user hasn't even been logged in.
// Logging the user out on the login page would lead to the logout being performed every
// two minutes even if nobody uses the terminal.

function containsSubstring(mainString, substring) {
    let substringCharPos = 0

    for (let i = 0; i < mainString.length; i++) {
        if (mainString.charAt(i) === substring.charAt(substringCharPos)) {
            substringCharPos++;
            if (substringCharPos >= substring.length) {
                return true;
            }
        } else {
            substringCharPos = 0;
        }
    }

    return substringCharPos === substring.length;
}
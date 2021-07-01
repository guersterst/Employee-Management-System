// Log out the user after two minutes of inactivity
// Activity: key-press, moving the mouse or pressing a mouse-button

// When the page is loaded, we start a timer
// Loading a page indicates activity, so it's fair to start a timer.

let standardCounterTime = 1000 * 4;
let counter = standardCounterTime;
let interval;
let text = $('#timer');
let lastFiveSecondsCounter = 5;

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

function resetTimer() {
    lastFiveSecondsCounter = 5
    counter = standardCounterTime
    text.text("");
    clearInterval(interval);
    interval = setInterval(startLogOutTimer, counter)
}

function startLogOutTimer() {
    if (document.URL !== "/login" && document.URL !== "/login?logout" && document.URL !== "/login?error") {
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

function logout() {
    lastFiveSecondsCounter = 5
    counter = standardCounterTime
    clearInterval(interval);

    if (document.URL !== "/login" && document.URL !== "/login?logout" && document.URL !== "/login?error") {
        window.location = "/login?logout";
    }
}
// Log out the user after two minutes of inactivity
// Activity: key-press, moving the mouse or pressing a mouse-button

// When the page is loaded, we start a timer
// Loading a page indicates activity, so it's fair to start a timer.

let standardCounterTime = 1000 * 120;
let counter = standardCounterTime;
let interval;
let loggingOut = false;

window.onload = function() {
    counter = standardCounterTime;
    interval = setInterval(logout, counter)
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
    if (!loggingOut) {
        counter = standardCounterTime
        clearInterval(interval);
        setInterval(logout, counter)
    }
}

function logout() {
    loggingOut = true;
    window.location = "/login?logout";
}
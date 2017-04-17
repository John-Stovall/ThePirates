//This function is called when the page is first loaded.
//Load any necessary information here.
onload = function() {
    updateHTML();
};

/**
 * This function takes the input from the name and email textboxes and saves it.
 */
function submitEmail() {
    localStorage["name"] = JSON.stringify(document.getElementById("name").value);
    localStorage["email"] = JSON.stringify(document.getElementById("email").value);
    updateHTML();
}

/**
 * This function updates places in the html file so that it draws the proper information.
 */
function updateHTML() {
    document.getElementById("displayName").innerHTML = localStorage["name"];
    document.getElementById("displayEmail").innerHTML = localStorage["email"];
}
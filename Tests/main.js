/**
 * Created by Robert on 4/5/17.
 */

var sum = JSON.parse(localStorage["data"]); //Load the sum

//This function is called when the page is first loaded.
//Load any nessessary information here.
onload = function() {
    updateHTML();
};

/**
 * This function is the "Some Button" button. It was just
 * made to learn how to make buttons do stuff.
 */
function testFunction() {
    alert("Test Thing");
    sum = NaN;
    saveData();
    updateHTML();
}

/**
 * This function handles the code for the "Submit" button. It gets
 * the values from "testBox" and "testBox2" parses them into strings
 * and adds the results together.
 */
function testFunction2() {
    var number1 = document.getElementById("testBox").value; //Apparently these start off as strings.
    var number2 = document.getElementById("testBox2").value;
    sum = parseInt(number1) + parseInt(number2);

    if (!sum) { //Sum will only return false if it's NaN.
        alert("There was an error.");
    } else {
        updateHTML();
        saveData();
    }
}

/**
 * This function saves and important information to local storage.
 */
function saveData() {
    localStorage["data"] = JSON.stringify(sum); //Save the sum locally.
}

/**
 * This function updates places in the html file so that it draws the proper information.
 */
function updateHTML() {
    if (!sum) {
        document.getElementById("displaySum").innerHTML = "0";
    } else {
        document.getElementById("displaySum").innerHTML = sum;
    }
}
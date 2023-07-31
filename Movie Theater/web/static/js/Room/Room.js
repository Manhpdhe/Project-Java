
// Get the modal
var modal = document.getElementById("room-modal");

// Get the button that opens the modal
var btn = document.getElementById("add-cinema");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

var open = document.getElementsByClassName("open-select");
var branch = document.getElementsByClassName("branch-name");
// When the user clicks on the button, open the modal
btn.onclick = function () {
    modal.style.display = "block";
};

// When the user clicks on <span> (x), close the modal
span.onclick = function () {
    modal.style.display = "none";
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
};

// Add a click event listener to each element
for (let i = 0; i < branch.length; i++) {
  branch[i].addEventListener('click', function() {
    // Get the index of the clicked element
    const index = Array.prototype.indexOf.call(branch, this);
    open[index].hidden = false;
    
  });
  branch[i].addEventListener('dblclick', function() {
    // Get the index of the clicked element
    const index = Array.prototype.indexOf.call(branch, this);
    open[index].hidden = true;
    
  });
};

//branch.onclick = function () {
//    console.log(indexOf(this));
//    open.hidden = false;
//    
//};
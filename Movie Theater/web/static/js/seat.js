// Get the modal
var modal = document.getElementById("create-modal");


var updateModal = document.getElementById("update-modal");
// Get the button that opens the modal
var btn = document.getElementById("add-btn");
// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

var update = document.getElementsByClassName("update-link");

var updateBtn = document.getElementsByClassName("update-btn");

var updateForm = document.getElementsByClassName("update-form");

var updateArray = Array.from(updateBtn);
var formArray = Array.from(updateForm);

Array.from(updateArray).forEach(function(element) {
  element.addEventListener('click', function() {
    // Find the index of the clicked element in the array
    let index = updateArray.indexOf(this);
    formArray[index].submit();
  });
});


// When the user clicks on the button, open the modal
btn.onclick = function() {
  modal.style.display = "block";
};

updateBtn.onclick = function() {
  updateModal.style.display = "block";
};

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target === modal) {
    modal.style.display = "none";
  }
};

updateBtn.onClick = function (event) {
    event.preventDefault();
};


function validRoomID() {
    var updateForm = document.getElementsByClassName("update-form");

}




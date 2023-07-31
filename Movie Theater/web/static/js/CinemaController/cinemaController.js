const cinemaInfor = document.getElementsByClassName("cinema-infor");
const cinemaView = document.getElementsByClassName('cinema-view');
var close = document.getElementsByClassName("close");

const openViewInfor = (modal, button, closeIndex) => {
  // When the user clicks on the button, open the modal
  button.onclick = function () {
    modal.style.display = "block";
  };

  // When the user clicks on <span> (x), close the modal
  closeIndex.onclick = function () {
    modal.style.display = "none";
  };

  // When the user clicks anywhere outside of the modal, close it
  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  };
};

for (let index = 0; index < cinemaInfor.length; index++) {
  cinemaInfor[index].addEventListener('click', () => {
    openViewInfor(cinemaView[index], cinemaInfor[index], close[index]);
  });
  
}



// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementById("close");

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
  if (event.target == modal) {
    modal.style.display = "none";
  }
};
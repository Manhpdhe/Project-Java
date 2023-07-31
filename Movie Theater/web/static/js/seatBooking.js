const container = document.querySelector('.container');
const seats = document.querySelectorAll('.row .seat:not(.occupied)');
const count = document.getElementById('count');
const total = document.getElementById('total');
const movieSelect = document.getElementById('movie');

populateUI();

let ticketPrice = +movieSelect.value;

// Save selected movie index and price
function setMovieData(movieIndex, moviePrice) {
  localStorage.setItem('selectedMovieIndex', movieIndex);
  localStorage.setItem('selectedMoviePrice', moviePrice);
}

// Update total and count
function updateSelectedCount() {
  const selectedSeats = document.querySelectorAll('.row .seat.selected');

  const seatsIndex = [...selectedSeats].map(seat => [...seats].indexOf(seat));

  localStorage.setItem('selectedSeats', JSON.stringify(seatsIndex));

  const selectedSeatsCount = selectedSeats.length;
  
  var bookedSeatIds = [];
  var bookedSeatNames = [];
  var bookDiv = document.createElement('div');
  count.innerText = selectedSeatsCount;
  var sum = 0;
  for (var i = 0; i < selectedSeatsCount; i++) {
      console.log(selectedSeats[i]);
      var inp = document.createElement('input');
      inp.type = "hidden";
      inp.name = "seatList";
      inp.value = selectedSeats[i].dataset.seatid;
      bookDiv.appendChild(inp);
      var inp2 = document.createElement('input');
      inp2.type = "hidden";
      inp2.name = "priceList";
      inp2.value = selectedSeats[i].dataset.tickettype;
      bookDiv.appendChild(inp2);
      var inp3 = document.createElement('input');
      inp3.type = "hidden";
      inp3.name = "seatPosList";
      inp3.value = selectedSeats[i].dataset.row + selectedSeats[i].dataset.col;
      bookDiv.appendChild(inp3);
      sum += parseInt(selectedSeats[i].dataset.price);
  }
  total.value = sum;
  document.getElementById("seatList").innerHTML = bookDiv.innerHTML;
/*
  count.innerText = selectedSeatsCount;
  total.innerText = selectedSeatsCount * ticketPrice;
     
 */

  setMovieData(movieSelect.selectedIndex, movieSelect.value);
}

// Get data from localstorage and populate UI
function populateUI() {
  const selectedSeats = JSON.parse(localStorage.getItem('selectedSeats'));

  if (selectedSeats !== null && selectedSeats.length > 0) {
    seats.forEach((seat, index) => {
      if (selectedSeats.indexOf(index) > -1) {
        seat.classList.add('selected');
      }
    });
  }

  const selectedMovieIndex = localStorage.getItem('selectedMovieIndex');

  if (selectedMovieIndex !== null) {
    movieSelect.selectedIndex = selectedMovieIndex;
  }
}

// Movie select event
movieSelect.addEventListener('change', e => {
  ticketPrice = +e.target.value;
  setMovieData(e.target.selectedIndex, e.target.value);
  updateSelectedCount();
});

// Seat click event
container.addEventListener('click', e => {
  if (
    e.target.classList.contains('seat') &&
    !e.target.classList.contains('occupied')
    && !e.target.classList.contains('vip')
  ) {
    e.target.classList.toggle('selected');

    updateSelectedCount();
  } 
  // elseif(e.target.classList.contains('vip')){

  // }
});

// container.addEventListener('click', e => {
//   if (
//     e.target.classList.contains('seat') &&
//     !e.target.classList.contains('vip')
//   ) {
//     e.target.classList.toggle('selected');

//     updateSelectedCount();
//   }
// });

const vip = document.getElementsByClassName('vip');
for (let index = 0; index < vip.length; index++) {
  vip[index].addEventListener('click', () => {
 
      vip[index].classList.toggle('selected');
      // vip[index].style.backgroundColor = '#619cbc';
      updateSelectedCount();
    
  })


}


updateSelectedCount();
function openForm() {
  document.getElementById("myForm").style.display = "block";
}

function closeForm() {
  document.getElementById("myForm").style.display = "none";
}

function open2ndForm() {
  document.getElementById("my2ndForm").style.display = "block";
  document.getElementById("myForm").style.display = "none";

}

function close2ndForm() {
  document.getElementById("my2ndForm").style.display = "none";
}

function alphabet(i) {
    return i < 0 ? "" : alphabet(Math.floor(i / 26) - 1) + String.fromCharCode(65 + i % 26);
}
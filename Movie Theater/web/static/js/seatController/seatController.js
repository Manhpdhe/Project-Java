const rowMark = document.getElementsByClassName('row-mark');
const colMark = document.getElementsByClassName('col-mark');
const deleteSeat = document.getElementById('disable');
const normalSeat = document.getElementById('normal');
const goldSeat = document.getElementById('gold');
const defaults = document.getElementsByClassName('default');
const seatID = document.getElementsByClassName('seatID');
const colNo = document.getElementsByClassName('colNo');
const rowNo = document.getElementsByClassName('rowNo');
const color = document.getElementsByClassName('color');
const form = document.getElementsByClassName('form')[0];
const submitButton = document.getElementById('submit-button');
const realRowMark = document.getElementsByClassName('real-row-mark');
const mainFrm = document.getElementsByClassName("form")[0];
const seatList = mainFrm.getElementsByClassName('seat');

var mode = -1;

function bindMode(elem, modeNum) {
    elem.addEventListener('click',()=> {
        mode = modeNum;
    });
}

bindMode(deleteSeat, 0);
bindMode(normalSeat, 1);
bindMode(goldSeat, 2);

function modifySeatType(seat, seatType) {
    seat.querySelector('input[name="seatType"]').setAttribute("value",seatType);
}

function initiateSeatsEvent(seatLs) {
    for (const seat of seatLs) {
        seat.addEventListener('click',()=> {
            seat.classList.remove('default');
            seat.classList.remove('vip');
            seat.classList.remove('normal');
            seat.classList.remove('seat-blank');
            switch(mode) {
                case 0:
                    {
                        modifySeatType(seat, -1);
                        seat.classList.add('seat-blank');
                        break;
                    }
                case 1:
                    {
                        modifySeatType(seat, 1);
                        seat.classList.add('default');
                        seat.classList.add('normal');
                        break;
                    }
                case 2:
                    {
                        modifySeatType(seat, 2);
                        seat.classList.add('default');
                        seat.classList.add('vip');
                        break;
                    }
            }
        });
    }
}

function initiateRowClickEvent(markList) {
    for (const mark of markList) {
        mark.addEventListener('click',()=> {
           for (var i = 0; i < parseInt(mainFrm.dataset.numcol); i++) {
               seatList[parseInt(mark.dataset.row)*parseInt(mainFrm.dataset.numcol)+i].click();
           }
        });
    }
}

function initiateColClickEvent(markList) {
    for (const mark of markList) {
        mark.addEventListener('click',()=> {
           for (var i = 0; i < parseInt(mainFrm.dataset.numrow); i++) {
               seatList[i*parseInt(mainFrm.dataset.numcol)+parseInt(mark.dataset.col)].click();
           }
        });
    } 
}

initiateSeatsEvent(seatList);
initiateRowClickEvent(rowMark);
initiateColClickEvent(colMark);

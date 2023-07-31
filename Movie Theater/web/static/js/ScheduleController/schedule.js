const movie = document.querySelectorAll('.movie-name');
const create = document.querySelector('#create-btn')
let scheduleArea = document.querySelectorAll('.custom-div');
const createSchedule = document.querySelector('#create-schedule');
const select = document.querySelector('#cinema-select');
const form = document.querySelector('#select-form');
let current = null;
let currentTime = null;

// Submit form
const selectRoom = document.querySelector('#outer-room-id');
const formRoom = document.querySelector('#room-form');
selectRoom.addEventListener('change', () => {
    formRoom.submit();
});



movie.forEach(m => {
    m.addEventListener('dragstart', function (e) {
        current = this;
    });
});

// Xử lí thêm slot chiếu

//const day = document.querySelector('#day');

select.addEventListener('change', function () {
    form.submit();
});


const week = document.querySelector('#week');
const schedule = document.querySelector('#week-schedule');
week.addEventListener('change', function () {
    schedule.submit();
});


// Lấy tất cả các thẻ td trong bảng
var tds = document.querySelectorAll('.td-container');
for (let i = 0; i < tds.length; i++) {
    // Duyệt qua từng thẻ td và thêm Event Listener
//tds.forEach(function (td) {
    tds[i].addEventListener('click', function () {
        // Kiểm tra xem td đã có div hay chưa
        var div = tds[i].querySelector('.custom-div');

        // Nếu td chưa có div, thì tạo và chèn div vào trong td
        if (!div) {
            div = document.createElement('div');
            div.classList.add('custom-div');
            div.classList.add('resizeable-div');
            div.setAttribute('draggable', 'true');
            tds[i].appendChild(div);
            // Find Node Class and define start time
            const startTime = tds[i].parentNode.classList;
            let startInput = document.createElement('input');
            startInput.setAttribute('type', 'text');
            startInput.setAttribute('name', 'start-time');
            startInput.setAttribute('value', startTime);
            startInput.setAttribute('hidden', true);
            // Find Node Class and define end time
            const endTime = parseInt(startTime) + 1;
            let endInput = document.createElement('input');
            endInput.setAttribute('type', 'text');
            endInput.setAttribute('name', 'end-time');
            endInput.setAttribute('value', endTime);
            endInput.setAttribute('hidden', true);
            endInput.setAttribute('class', 'endTime');
            // Define Slot
            let data = document.createElement('input');
            data.setAttribute('type', 'text');
            data.setAttribute('name', 'state');
            data.setAttribute('value', 'slot');
            data.setAttribute('class', 'state');
            data.setAttribute('hidden', true);
            // Defind div
            let innerMovie = document.createElement('div');
            innerMovie.setAttribute('class', 'inner-movie-name');

            let innerTime = document.createElement('input');
            let outerTime = document.querySelectorAll('.formal-date');
            innerTime.setAttribute('value', outerTime[i].value);
            innerTime.setAttribute('name', 'date-time');
            innerTime.setAttribute('hidden', 'true');
            innerTime.setAttribute('type', 'text');
            // Define ID
            let showID = document.createElement('input');
            showID.setAttribute('type', 'text');
            showID.setAttribute('name', 'delete-id');
            showID.setAttribute('value', '');
            showID.setAttribute('hidden', true);
            showID.setAttribute('class', 'ShowID');
            // Define delay
            let delay = document.createElement('input');
            delay.setAttribute('type', 'number');
            delay.setAttribute('name', 'delay');
            delay.setAttribute('value', '');
            delay.setAttribute('hidden', true);
            delay.setAttribute('class', 'movie-delay');
            // Add to div
            div.appendChild(startInput);
            div.appendChild(endInput);
            div.appendChild(data);
            div.appendChild(innerMovie);
            div.appendChild(innerTime);
            div.appendChild(showID);
            div.appendChild(delay);
        }


// Resize div
        const resize = document.querySelectorAll('.resizeable-div');
//        console.log(resize);
        for (let index = 0; index < resize.length; index++) {

            let startY;
            let startHeight;

            resize[index].addEventListener('mouseover', function () {
                resize[index].addEventListener('mousedown', function (event) {
                    event.stopPropagation();
                    startY = event.clientY;
                    startHeight = parseInt(document.defaultView.getComputedStyle(div).height, 10);
                    document.documentElement.addEventListener('mousemove', resizeDiv(event, index));
                    document.documentElement.addEventListener('mouseup', stopResize);

                });
            });


            function resizeDiv(e, index) {
//              var newHeight = startHeight + (e.clientY - startY);
                var newHeight = startHeight + 35;
                div.style.height = newHeight + 'px';
                const endTime = document.getElementsByClassName('endTime')[index];
                const value = parseInt(endTime.value);
                endTime.setAttribute('value', value + 1);

            }

            function stopResize() {
                document.documentElement.removeEventListener('mousemove', resizeDiv);
                document.documentElement.removeEventListener('mouseup', stopResize);
            }

        }
        // Drag and Drop
        let area = document.querySelectorAll('.resizeable-div');
        const innerMovieName = document.querySelectorAll('.inner-movie-name');
        for (let index = 0; index < area.length; index++) {
            area[index].addEventListener('dragover', function (e) {
                e.preventDefault();
            });

            area[index].addEventListener('drop', function (e) {
                const delayId = Array.from(document.getElementsByClassName('movie-name')).indexOf(current);
                innerMovieName[index].innerText = current.innerHTML;
                this.style.width = 'fit-content';
                this.classList.add('change-bg');
                const state = document.getElementsByClassName('state')[index];
                const movieDelay = document.getElementsByClassName('movie-delay')[index];
                const delay = document.getElementsByClassName('delay')[delayId];
                state.setAttribute('value', current.innerHTML);
                movieDelay.setAttribute('value', delay.value);
            });
        }
    });


}
;


const createScheduleButton = document.querySelector('#create-btn');
const innerRoomID = document.querySelector('#inner-room-id');
const outerRoomID = document.querySelector('#outer-room-id');
const createScheduleForm = document.querySelector('#create-schedule-form');
createScheduleButton.addEventListener('click', function () {
    innerRoomID.setAttribute('value', outerRoomID.value);
    createScheduleForm.submit();
});

// Drag and Drop
let area = document.querySelectorAll('.resizeable-div');
const innerMovieName = document.querySelectorAll('.inner-movie-name');
for (let index = 0; index < area.length; index++) {
    area[index].addEventListener('dragover', function (e) {
        e.preventDefault();
    });

    area[index].addEventListener('drop', function (e) {
        innerMovieName[index].innerText = current.innerHTML;
        this.style.width = 'fit-content';
        this.classList.add('change-bg');
        const state = document.getElementsByClassName('state')[index];
        state.setAttribute('value', current.innerHTML);
    });
}

// Xóa lịch đã được xếp
let currentFilm = null;
scheduleArea.forEach(a => {
    a.addEventListener('dragstart', function (e) {
        currentFilm = this;
    });
});

const deleteArea = document.querySelector('#delete-area');
const state = document.querySelectorAll('.state');
const showID = document.querySelectorAll('.ShowID');
const scheduleID = document.querySelectorAll('.scheduleId');
deleteArea.addEventListener('dragover', function (e) {
    e.preventDefault();
});

deleteArea.addEventListener('drop', function (e) {
    const index = Array.prototype.indexOf.call(scheduleArea, currentFilm);
    if (state[index] === null) {
        currentFilm.remove();
    } else if (state[index].value.includes('update')) {
        currentFilm.setAttribute('hidden', true);
        showID[index].setAttribute('name', 'delete-id');
        scheduleID[index].setAttribute('name', 'slot-id');
    }
    currentFilm.setAttribute('hidden', true);

});
function updateCardNumber() {
    const input1 = document.querySelector('.card-number-input-1').value;
    const input2 = document.querySelector('.card-number-input-2').value;
    const input3 = document.querySelector('.card-number-input-3').value;
    const input4 = document.querySelector('.card-number-input-4').value;

    const cardNumber = (input1 || '####') + ' ' + (input2 || '####') + ' ' + (input3 || '####') + ' ' + (input4 || '####');
    document.querySelector('.card-number-box').innerText = cardNumber;
}

function updateCardConfirm() {
    const inputa = document.querySelector('.card-holder-input-1').value;
    const inputb = document.querySelector('.card-holder-input-2').value;
    const inputc = document.querySelector('.card-holder-input-3').value;
    const inputd = document.querySelector('.card-holder-input-4').value;

    const cardHolder = (inputa || '####') + ' ' + (inputb || '####') + ' ' + (inputc || '####') + ' ' + (inputd || '####');
    document.querySelector('.card-holder-name').innerText = cardHolder;
}

function validateNumber(input) {
    var errorMessage = document.getElementById("card-number-error");
    if (input.value.trim() === "" || isNaN(input.value)) {
        errorMessage.innerText = "Vui lòng nhập một số hợp lệ";
        errorMessage.style.color = "red";
    } else {
        errorMessage.innerText = "";
    }
}

function addNumericValidation(inputBlock, msg) {
    var inputBox = inputBlock.getElementsByTagName("input")[0];
    var errmsg = inputBlock.getElementsByTagName("p")[0];

    ['focusout', 'input'].forEach(evt =>
        inputBox.addEventListener(evt, () => {
            document.getElementById("register-error-msg").innerText = "";
            if (isNaN(inputBox.value)) {
                errmsg.innerText = msg;
                inputBlock.classList.add("error");
            } else {
                inputBlock.classList.remove("error");
                errmsg.innerText = "";
            }
        })
    );
}

document.querySelector('.card-number-input-1').oninput = () => {
    validateNumber(document.querySelector('.card-number-input-1'));
    updateCardNumber();
};
document.querySelector('.card-number-input-2').oninput = () => {
    validateNumber(document.querySelector('.card-number-input-2'));
    updateCardNumber();
};
document.querySelector('.card-number-input-3').oninput = () => {
    validateNumber(document.querySelector('.card-number-input-3'));
    updateCardNumber();
};
document.querySelector('.card-number-input-4').oninput = () => {
    validateNumber(document.querySelector('.card-number-input-4'));
    updateCardNumber();
};

document.querySelector('.card-holder-input-1').oninput = () => {
    updateCardConfirm();
};
document.querySelector('.card-holder-input-2').oninput = () => {
    updateCardConfirm();
};
document.querySelector('.card-holder-input-3').oninput = () => {
    updateCardConfirm();
};
document.querySelector('.card-holder-input-4').oninput = () => {
    updateCardConfirm();
};

// phone number validation
addValidation(document.getElementsByClassName('input-box')[9], /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/, "Định dạng số điện thoại chưa đúng!");

// numeric validation for all input fields
document.querySelectorAll('.inputBox input').forEach(input => {
    addNumericValidation(input.parentNode, "Chỉ được nhập số!");
});

addFormValidation(document.getElementById('register-form'));

function addValidation(inputBlock, regex, msg) {
    console.log(inputBlock);
	var inputBox = inputBlock.getElementsByTagName("input")[0];
    console.log(inputBox);
    var errmsg = inputBlock.getElementsByTagName("p")[0];

    ['focusout','input'].forEach( evt => 
        inputBox.addEventListener(evt, () => {
            document.getElementById("register-error-msg").innerText = "";
            console.log("LMAO");
            if (!regex.test(inputBox.value)) {
                if (inputBox.value == "") {
                    if (inputBox.type == "date") {
                        errmsg.innerText = "Ngày tháng năm sinh chưa hợp lệ!";
                    } else {
                        errmsg.innerText = "Ô không được để trống!";
                    }
                    
                } else {
                errmsg.innerText = msg;
                    }
                    inputBlock.classList.add("error");
                    
                } else {
                    inputBlock.classList.remove("error");
                    errmsg.innerText = "";
                }
            
        })
    );
    
;

}

function addFormValidation(form) {
    form.addEventListener("submit", (e) => {
        var errobj = document.getElementById("register-error-msg");
        var tcheck = document.getElementById("tac-check");
        if (!tcheck.checked) {
            errobj.innerText = "Hãy đồng ý Điều Khoản trước khi tiếp tục";
            e.preventDefault();
        } else {
            var errs = form.getElementsByClassName("error");
            console.log(errs.length);
            if (errs.length > 0) {
                errobj.innerText = "Bạn phải sửa hết các thông tin lỗi trước!";
                e.preventDefault();
            }
        }
        
    });
    
}

// first name && last name validation
addValidation(document.getElementsByClassName('input-box')[0],/^[^\d!"#$%&'()*+\/:,;<=>?@[\]^_`{|}~]+$/,"Tên không được chứa các ký tự đặc biệt hoặc số!");
addValidation(document.getElementsByClassName('input-box')[1],/^[^\d!"#$%&'()*+\/:,;<=>?@[\]^_`{|}~]+$/,"Họ không được chứa các ký tự đặc biệt hoặc số!");

// username & address name validation
addValidation(document.getElementsByClassName('input-box')[2],/^[a-zA-Z0-9]+$/,"Tên đăng nhập chỉ được chứa chữ và số");
addValidation(document.getElementsByClassName('input-box')[8],/^[^!"#$%&'()*+\/:;<=>?@[\]^_`{|}~]+$/,"Địa chỉ không được chưa các ký tự đặc biệt!");

// password validation
addValidation(document.getElementsByClassName('input-box')[3],/^.{8,}$/,"Mật khảu phải chứa ít nhất 8 ký tự!");

// email name validation
addValidation(document.getElementsByClassName('input-box')[4],/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/,"Định dạng email chưa đúng!");

// dob validation
addValidation(document.getElementsByClassName('input-box')[7],/^(?:19|20)\d\d-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1\d|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)$|^((?:19|20)(?:04|08|[2468][048]|[13579][26]))-02-29$/,"Ngày tháng năm sinh chưa hợp lệ!");

// phone number validation
addValidation(document.getElementsByClassName('input-box')[9],/^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/,"Định dạng số điện thoại chưa đúng!");

addFormValidation(document.getElementById('register-form'));
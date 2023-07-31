<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
       
        <style>
             
            <%@include file="/static/css/CustomerCSS/MemberShip/membership-register.css" %>
        </style>
        <%@include file="/views/util/imports.jsp" %>
    </head>
    <body>
        <div class="container">
            <div class="side-bar-1">
                <a href="${pageContext.request.contextPath}/home">
                    <img src="${pageContext.request.contextPath}/static/images/groovy_cine_full_logo.svg">
                </a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/profile">Profile</a><br></li>
                    <li><a href="${pageContext.request.contextPath}/profile/membership">Membership</a><br></li>
                    <li><a href="${pageContext.request.contextPath}/profile/history">Booking History</a><br></li>
                </ul>
            </div>
            <div class="flex-card-container">
                <div>
                    <div class="card-container">
                        <div class="front" style="margin: 0 25%">
                            <div class="image">
                                <img src="${pageContext.request.contextPath}/static/images/chip.png">
                                <img src="${pageContext.request.contextPath}/static/images/logo.png">
                            </div>
                            <div class="card-number-box">#### #### #### ####</div>
                            <div class="flexbox">
                                <div></div>
                                <div class="box">
                                    <span>CONFIRM CARD NUMBER</span>
                                    <div class="card-holder-name">#### #### #### ####</div>
                                </div>
                            </div>
                        </div>
                        <div class="back">
                            <div class="stripe"></div>
                            <div class="box">
                                <span>cvv</span>
                                <div class="cvv-box"></div>
                                <img src="image/logo.png" alt="">
                            </div>
                        </div>
                    </div>
                    <form action="${pageContext.request.contextPath}/profile/membership/register" method="post" id="register-form">
                        <h2>REGISTER MEMBERSHIP CARD</h2>
                        <div id="cardnumber">
                            <div class="inputBox" id="cardnumber">
                                <span>CARD NUMBER</span>
                                <input id="cardnumber" name="cardnumber" type="text" placeholder="4 numbers" maxlength="4" class="card-number-input-1"
                                       size="10" oninput="validateNumber(this); updateCardNumber()">
                                -
                                <input id="cardnumber" name="cardnumber" type="text" placeholder="4 numbers" maxlength="4" class="card-number-input-2"
                                       size="10" oninput="validateNumber(this); updateCardNumber()">
                                -
                                <input id="cardnumber" name="cardnumber" type="text" placeholder="4 numbers" maxlength="4" class="card-number-input-3"
                                       size="10" oninput="validateNumber(this); updateCardNumber()">
                                -
                                <input id="cardnumber" name="cardnumber" type="text" placeholder="4 numbers" maxlength="4" class="card-number-input-4"
                                       size="10" oninput="validateNumber(this); updateCardNumber()">
                                <p id="card-number-error"></p>
                            </div>
                            <div class="inputBox" id="card-number-confirm">
                                <span>CONFIRM CARD NUMBER</span>
                                <input id="card_number_confirm_1" name="card_number_confirm" type="text" maxlength="4" class="card-holder-input-1" size="10"
                                       oninput="validateNumber(this); validateConfirmNumber(this); updateCardConfirm()">
                                -
                                <input id="card_number_confirm_2" name="card_number_confirm" type="text" maxlength="4" class="card-holder-input-2" size="10"
                                       oninput="validateNumber(this); validateConfirmNumber(this); updateCardConfirm()">
                                -
                                <input id="card_number_confirm_3" name="card_number_confirm" type="text" maxlength="4" class="card-holder-input-3" size="10"
                                       oninput="validateNumber(this); validateConfirmNumber(this); updateCardConfirm()">
                                -
                                <input id="card_number_confirm_4" name="card_number_confirm" type="text" maxlength="4" class="card-holder-input-4" size="10"
                                       oninput="validateNumber(this); validateConfirmNumber(this); updateCardConfirm()">

                                <p id="card-number-confirm-error"></p>
                                <span id="wrong_pass_alert"></span>
                            </div>
                            <c:if test="${requestScope.errmsg ne null}">
                                <p style="color:red">${requestScope.errmsg}</p>
                            </c:if>
                        </div>
                        <input id="create" type="submit" onclick="wrong_pass_alert()" value="Register" name="register" class="inputBox-1">
                    </form>
                    <p id="register-error-msg"></p>
                </div>
            </div>
        </div>
        <script>
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
                    errorMessage.innerText = "☒ Please enter a valid number";
                    errorMessage.style.color = "red";
                } else {
                    errorMessage.innerText = "🗹 Number correct";
                    errorMessage.style.color = "green";
                }
            }

            function validateConfirmNumber(input) {
                var cardnumber = document.querySelector('[name="cardnumber"]').value;
                var cardnumberconfirm = document.querySelector('[name="card_number_confirm"]').value;
                if (cardnumber !== cardnumberconfirm) {
                    document.getElementById('wrong_pass_alert').style.color = 'red';
                    document.getElementById('wrong_pass_alert').innerHTML = '☒ Card Number Confirm not matched';
                    document.getElementById('create').disabled = true;
                    document.getElementById('create').style.opacity = 0.4;
                } else {
                    document.getElementById('wrong_pass_alert').style.color = 'green';
                    document.getElementById('wrong_pass_alert').innerHTML = '🗹 Card Number Confirm Matched';
                    document.getElementById('create').disabled = false;
                    document.getElementById('create').style.opacity = 1;
                }
            }

            function wrong_pass_alert() {
                if (document.getElementById('cardnumber').value !== "" &&
                        document.getElementById('card_number_confirm').value !== "") {
                    alert("Your response is submitted");
                } else {
                    alert("Please fill all the fields");
                }
            }

            function addNumericValidation(inputBlock, msg) {
                var inputBox = inputBlock.querySelector("input");
                var errmsg = inputBlock.querySelector("p");

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

            document.querySelectorAll('.card-number-input').forEach(input => {
                input.oninput = () => {
                    validateNumber(input);
                    updateCardNumber();
                };
            });

            document.querySelectorAll('.card-holder-input').forEach(input => {
                input.oninput = () => {
                    validateNumber(input);
                    validateConfirmNumber(input);
                    updateCardConfirm();
                };
            });

            function addFormValidation(form) {
                form.addEventListener("submit", (e) => {
                    var errobj = document.getElementById("register-error-msg");
                    var tcheck = document.getElementById("tac-check");
                    if (!tcheck.checked) {
                        errobj.innerText = "Please agree to the Terms and Conditions before continuing";
                        e.preventDefault();
                    } else {
                        var errs = form.getElementsByClassName("error");
                        if (errs.length > 0) {
                            errobj.innerText = "Please fix all the error fields before submitting!";
                            e.preventDefault();
                        } else {
                            var errorMessage = document.getElementById("card-number-confirm-error");
                            var cardNumberInputs = document.querySelectorAll('.card-number-input');
                            var cardNumber = "";
                            cardNumberInputs.forEach(input => {
                                cardNumber += input.value;
                            });

                            var confirmInputs = document.querySelectorAll('.card-holder-input');
                            var confirmNumber = "";
                            confirmInputs.forEach(input => {
                                confirmNumber += input.value;
                            });

                            if (confirmNumber !== cardNumber) {
                                errorMessage.innerText = "Confirm Number must match the CARD NUMBER!";
                                errorMessage.style.color = "red";
                                e.preventDefault();
                            } else {
                                errorMessage.innerText = "";
                            }
                        }
                    }
                });
            }

            // numeric validation for all input fields
            document.querySelectorAll('.inputBox input').forEach(input => {
                addNumericValidation(input.parentNode, "Only numbers are allowed!");
            });

            // Add event listener to the form
            addFormValidation(document.getElementById('register-form'));
        </script>
    </body>
</html>

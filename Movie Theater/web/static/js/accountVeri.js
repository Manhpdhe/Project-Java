/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const codes = document.querySelectorAll('.code');

codes[0].focus();

codes.forEach((code, idx) => {
    code.addEventListener('keydown', (e) => {
        if(e.key >= 0 && e.key <=9) {
            codes[idx].value = '';
            setTimeout(() => codes[idx + 1].focus(), 10);
        } else if(e.key === 'Backspace') {
            setTimeout(() => codes[idx - 1].focus(), 10);
        }
    });
});


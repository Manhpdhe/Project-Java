/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
//<![CDATA[
var dataForm = new VarienForm('form-validate', true);
$j('button[type=submit]').on('click', function () {
    var value = '';
    value = $j('.validate-cfcard').filter(function () {
        return this.value === '';
    });
    if (value.length === 8) {//alert('Everything has a value.');
    } else if (value.length > 0) {
        alert('Phải nhập thông tin.');
        return false;
    }
});
//]]>


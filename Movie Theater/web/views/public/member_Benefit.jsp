<%-- 
    Document   : member_Benefits
    Created on : Jul 2, 2023, 12:18:39 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <style><%@include file="/static/css/memberBenefits.css" %></style>
    </head>
    <body>
        <div>
            <%@include file="/views/util/header.jsp" %>
        </div>
        <!-- Wrap the code block in a div and add the "code-frame" class -->
        <div class="col-right-content-member">
            <div class="text-tilte-cinema">
                <h1>Groovy Cineplex Membership</h1>
            </div>
            <div class="image-container">
                <!--<img src="${pageContext.request.contextPath}/static/images/member-star.png">-->           
            </div>
        </div>
        <div class="code-frame">
            <div class="full-container">

                <div class="container">
                    <div class="text-tilte-cinema">

                    </div>
                    <div class="lyt-history-content ui-tabs-panel ui-widget-content ui-corner-bottom"
                         id="customer-points" aria-labelledby="ui-id-1" role="tabpanel" aria-expanded="true"
                         aria-hidden="false">
                        <div class="outer">
                            <div class="inner">
                                <div class="lyt-heading"
                                     style="display: flex; justify-content: center; padding: 10px; margin-bottom: 20px; font-size: 1.2em;">
                                    CHƯƠNG
                                    TRÌNH ĐIỂM THƯỞNG</div>
                                <div class="lyt-details-content">
                                    <p>Chương trình bao gồm 4 đối tượng thành viên <strong>U22 | Groovy Cineplex
                                            Member |
                                            Groovy Cineplex VIP và Groovy Cineplex VVIP</strong>, với những quyền lợi
                                        và mức ưu đãi khác nhau. Mỗi khi thực hiện giao dịch tại hệ thống rạp Groovy
                                        Cineplex, bạn sẽ nhận được một số điểm thưởng tương ứng với cấp độ thành
                                        viên:</p>

                                    <form
                                        style="display: flex; justify-content: center; text-align: left; height: inherit; padding: 70px;">
                                        <table style="width: 45%; border: 1px solid;">
                                            <tbody style="border: 1px solid;">
                                                <tr>
                                                    <th  style="width: 30%; background-color: #61a4bc;">Điểm thưởng</th>
                                                    <th style="width: 30%; background-color: #61a4bc;">U22 | Member</th>
                                                    <th style="width: 21%; background-color: #61a4bc;">VIP</th>
                                                    <th style="width: 21%; background-color: #61a4bc;">VVIP</th>
                                                </tr>
                                                <tr class="odd">
                                                    <td style="background-color: #b0ddf5;"><strong>Tại Quầy Vé</strong></td>
                                                    <td style="background-color: #b0ddf5;"><strong>5%</strong></td>
                                                    <td style="background-color: #b0ddf5;"><strong>7%</strong></td>
                                                    <td style="background-color: #b0ddf5;"><strong>10%</strong></td>
                                                </tr>
                                                <tr>
                                                    <td style="background-color: #ffffff;">VD: 100.000 VND</td>
                                                    <td style="background-color: #ffffff;">5 Điểm</td>
                                                    <td style="background-color: #ffffff;">7 Điểm</td>
                                                    <td style="background-color: #ffffff;">10 Điểm</td>
                                                </tr>
                                                <tr class="odd">
                                                    <td style="background-color: #b0ddf5;"><strong>Quầy Bắp Nước</strong></td>
                                                    <td style="background-color: #b0ddf5;"><strong>3%</strong></td>
                                                    <td style="background-color: #b0ddf5;"><strong>4%</strong></td>
                                                    <td style="background-color: #b0ddf5;"><strong>5%</strong></td>
                                                </tr>
                                                <tr>
                                                    <td style="background-color: #ffffff;">VD: 100.000 VND</td>
                                                    <td style="background-color: #ffffff;">3 Điểm</td>
                                                    <td style="background-color: #ffffff;">4 Điểm</td>
                                                    <td style="background-color: #ffffff;">5 Điểm</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                    <p><strong>1 điểm = 1.000 VND</strong>, có giá trị như tiền mặt, được dùng để
                                        mua vé xem
                                        phim, thức uống hoặc combo tương ứng tại Groovy Cineplex. Ví dụ: Với giao
                                        dịch mua
                                        vé giá
                                        100.000 VND bạn có thể:</p>
                                    <p>- Thanh toán 80.000 VND + 20 điểm thưởng</p>
                                    <p>- Thanh toán với 10.000 VND + 90 điểm thưởng</p>
                                    <p><strong>Cách làm tròn điểm thưởng:</strong></p>
                                    <p>- Từ 0.1 đến 0.4: làm tròn xuống (Ví dụ: 3.2 điểm sẽ được tích vào tài khoản 3
                                        điểm)
                                    </p>
                                    <p>- Từ 0.5 đến 0.9: làm tròn lên (Ví dụ: 3.6 điểm sẽ được tích vào tài khoản 4
                                        điểm)
                                    </p>
                                    <p><strong>Lưu ý:</strong></p>
                                    <p>1. Điểm thưởng có thời hạn sử dụng là 01 năm. Ví dụ: Điểm của bạn được nhận
                                        vào 8h00
                                        ngày 05/01/2023 sẽ hết hạn sử dụng vào lúc 8h00 ngày 05/01/2024.</p>
                                    <p>2. Điểm thưởng có giá trị sử dụng tại tất cả các rạp CGV trên toàn quốc.</p>
                                    <p>3. Sau khi suất chiếu diễn ra, điểm thưởng sẽ được ghi nhận vào tài khoản của
                                        bạn vào
                                        8:00 sáng ngày tiếp theo. Ví dụ: suất chiếu của bạn diễn ra vào ngày
                                        05/01/2023,
                                        điểm thưởng sẽ được ghi nhận vào tài khoản của bạn vào 8:00 sáng ngày
                                        06/01/2023.
                                    </p>
                                    <p>4. Bạn có thể dễ dàng kiểm tra điểm thưởng của mình trên Groovy Cineplex
                                        Website hoặc Ứng dụng
                                        Groovy Cineplex trên điện thoại (Mobile App).</p>
                                    <p>5. Điểm thưởng tối thiểu được sử dụng cho mỗi giao dịch là 20 điểm trở
                                        lên.</p>
                                    <p>6. Thành viên khi thanh toán trực tuyến trên Website/APP, trong 1 giao
                                        dịch, điểm
                                        thưởng chỉ được sử dụng thanh toán tối đa 90% giá trị đơn hàng.</p>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            <div>
            <%@include file="/views/util/footer.jsp" %>
        </div>
    </body>
</html>

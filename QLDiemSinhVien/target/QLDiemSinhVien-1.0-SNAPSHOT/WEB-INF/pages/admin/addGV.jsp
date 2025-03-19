<%-- 
    Document   : addGV
    Created on : Aug 17, 2023, 2:41:15 PM
    Author     : FPTSHOP
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:url value="/giaovu/giangvien/add" var="action"/>   
<div class="roww">
    <div class="margin-auto form-login  form-addSV">
        <h1 class="text-center">
            <c:choose>
                <c:when test="${giangvienn.idGiangVien != null}">
                    Cập nhật giảng viên 
                </c:when>
                <c:otherwise>
                    Thêm giảng viên
                </c:otherwise>
            </c:choose>
        </h1>
        <div class="text-form-addSV">
            <form:form  modelAttribute="giangvienn" method="post" action="${action}" accept-charset="UTF-8">
                <form:hidden path="idGiangVien" />
                <form:hidden path="idTaiKhoan" />
                <div class="form-floating mb-3 mt-3">
                    <form:input type="text" class="form-control" path="hoTen" id="name" placeholder="Họ và tên" name="name" required="required"/>
                    <label for="name">Họ và tên</label>
                </div>
                <div class="form-floating mt-3 mb-3">
                    <form:input type="date" class="form-control" path="ngaySinh" id="dateofbirth" placeholder="Ngày sinh" name="dateofbirth" required="required"/>
                    <label for="dateofbirth">Ngày sinh</label>
                </div>
                <div class="form-floating mt-3 mb-3">
                    <form:input type="text" class="form-control" path="diaChi" id="address" placeholder="Địa chỉ " name="address" required="required"/>
                    <label for="address">Địa chỉ</label>
                </div>
                <div class="form-floating mt-3 mb-3">
                    <form:select path="gioiTinh" id="sex" class="form-select" cssErrorClass="is-invalid" >
                        <form:option value="-1" label="Chọn giới tính" />
                        <form:option value="1" label="Nam" />
                        <form:option value="0" label="Nữ" />
                    </form:select>
                    <label for="sex">Giới tính</label>
                </div>
                <div class="form-floating mt-3 mb-3">
                    <form:input type="text" class="form-control" path="soDienThoai" id="phonenumber" placeholder="Số điện thoại" name="phonenumber"/>
                    <label for="phonenumber">Số điện thoại</label>
                </div>
                <div class="form-floating mt-3 mb-3">
                    <form:input type="email" class="form-control" path="email" id="email" placeholder="Email" name="email" required="required"/>
                    <label for="email">Email</label>
                </div>
                <div class="btn-form-addsv">
                    <button class="btn input-form-addsv" type="submit">
                        <c:choose>
                            <c:when test="${giangvienn.idGiangVien != null}">
                                Cập nhật giảng viên 
                            </c:when>
                            <c:otherwise>
                                Thêm giảng viên
                            </c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.querySelector("form");

        form.addEventListener("submit", function (event) {
            event.preventDefault(); // Ngăn form gửi ngay lập tức

            let hoTen = document.getElementById("name").value.trim();
            let ngaySinh = document.getElementById("dateofbirth").value.trim();
            let diaChi = document.getElementById("address").value.trim();
            let gioiTinh = document.getElementById("sex").value;
            let soDienThoai = document.getElementById("phonenumber").value.trim();
            let email = document.getElementById("email").value.trim();

            // Kiểm tra các trường bắt buộc
            if (!hoTen || !ngaySinh || !diaChi || gioiTinh === "-1" || !email) {
                alert("Vui lòng điền đầy đủ thông tin bắt buộc.");
                return;
            }

            // Kiểm tra số điện thoại (nếu có)
            if (soDienThoai && !/^\d{10}$/.test(soDienThoai)) {
                alert("Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số.");
                return;
            }

            // Kiểm tra email hợp lệ
            if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
                alert("Email không hợp lệ.");
                return;
            }

            // Mã hóa URL để tránh lỗi font
            hoTen = encodeURIComponent(hoTen);
            diaChi = encodeURIComponent(diaChi);
            soDienThoai = soDienThoai ? encodeURIComponent(soDienThoai) : "";
            email = encodeURIComponent(email);

            // Gán lại vào input
            document.getElementById("name").value = hoTen;
            document.getElementById("address").value = diaChi;
            if (soDienThoai) document.getElementById("phonenumber").value = soDienThoai;
            document.getElementById("email").value = email;

            // Gửi form sau khi kiểm tra và mã hóa
            this.submit();
        });
    });
</script>

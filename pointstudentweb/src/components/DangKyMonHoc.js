import dayjs from 'dayjs';
import { useContext, useEffect, useState } from "react";
import { Button, Form, InputGroup } from "react-bootstrap";
import { useSearchParams } from "react-router-dom";
import { MyUserConText } from "../App";
import { AuthApis, endpoints } from "../configs/Apis";
import HeaderSV from "../layout/sinhvien/HeaderSV";

function formatDate(date) {
  return dayjs(date).format('DD/MM/YYYY');
}

const DangKyMonHoc = () => {
  const [user, dispatch] = useContext(MyUserConText);
  const [sinhvien, setSinhvien] = useState(null);
  const [DSmonHocHocKy, setMonHocHocKy] = useState([]);
  const [DSmonHocDangKy, setMonHocDangKy] = useState([]);
  const [DSLopHoc, setDSLopHoc] = useState([]);
  // const [xacNhanThanhToan, setXacNhanThanhToan] = useState([]);
  // const [monHoc, setMonHoc] = useState();
  const [success, setSuccess] = useState(false);
  // const [successThanhToan, setSuccessThanhToan] = useState(true);
  const [q] = useSearchParams();
  // let idThanhToan = q.get("vnp_TxnRef");
  // let transactionNo = q.get("vnp_TransactionNo");
  // let trans_date = q.get("vnp_PayDate");
  const [kw, setKw] = useState("");
  const hocphiTotal = DSmonHocDangKy.reduce((acc, c) => {
    return c.thanhToan !== 1 ? acc + c.idMonHoc.idMonHoc.soTinChi * 700000 : acc;
}, 0);

  useEffect(() => {
    async function getInformationSinhVien() {
      try {
        const response = await AuthApis().get(endpoints["current-sinhvien"]);
        setSinhvien(response.data);
      } catch (e) {
        console.error(e);
      }
    }
    getInformationSinhVien();
  }, []);
  useEffect(() => {
    setSuccess(false);
    loadDSMonHocDangKy();
  }, [success, kw]);
  useEffect(() => {
    loadDSMonHocHocKy();
    loadDSLopHoc();
  }, []);
  async function loadDSMonHocHocKy() {
    try {
      let e = endpoints["listMonHocDangKi"];
      let formData = new FormData();
      formData.append("tenTaiKhoan", user.tenTaiKhoan);
      formData.append("idTaiKhoan", user.idTaiKhoan);
      if (kw !== "") {
        formData.append("tenMonHoc", kw);
      }
      let res1 = await AuthApis().post(e, formData);
      setMonHocHocKy(res1.data);
    } catch (e) {
      console.error(e);
    }
  }
  const loadDSMonHocDangKy = async () => {
    try {
      //láy danh sách môn học sinh viene đangư ký
      let a = endpoints["listMonHocSVDangKy"];
      a = `${a}?idSinhVien=${user.idTaiKhoan}`;
      let res2 = await AuthApis().get(a);
      setMonHocDangKy(res2.data);
    } catch (ex) {
      console.error(ex);
    }
  };

  async function loadDSLopHoc() {
    try {
      const response = await AuthApis().get(endpoints["DSLopHoc"]);
      setDSLopHoc(response.data);
    } catch (e) {
      console.error(e);
    }
  }

  const dangKy = (evt, idMonHoc) => {
    evt.preventDefault();
    const process = async () => {
      let e = endpoints["dangKyMonHoc"];
      e = `${e}?IdSinhVien=${sinhvien.idSinhVien}&IdMonHoc=${idMonHoc}`;
      let res = await AuthApis().post(e, {
        idMonHoc: idMonHoc,
        idSinhVien: sinhvien.idSinhVien,
      });
      e = endpoints["getMonHocById"] + `?idMonHocHocKy=${idMonHoc}`;
      const response = await AuthApis().get(e);
      console.log(res.data);
      if (res.data === "Success") {
        setSuccess(true);
      } else {
        alert(`Đăng ký môn thất bại!!!`);
      }
    };
    process();
  };
  const huyDangKy = (evt, idMonHoc) => {
    evt.preventDefault();
    const process = async () => {
      let e = endpoints["huyDangKyMonHoc"];
      e = `${e}?IdSinhVien=${sinhvien.idSinhVien}&IdMonHoc=${idMonHoc}`;
      let res = await AuthApis().delete(e);

      setSuccess(true);
      if (res.status === 201) {
        alert("Đã hết thời gian đăng ký!!!");
      }
    };
    process();
  };

  const search = (evt) => {
    evt.preventDefault();
  };
  const thanhToan = (evt, hocphi) => {
    evt.preventDefault();
    const process = async () => {
      let e = endpoints["thanhToanHocPhi"];
      e = `${e}?hocPhi=${hocphi}`;
      let res = await AuthApis().get(e);
      if (/^https?:\/\//.test(res.data)) {
        // Open a new window with the URL
        window.open(res.data, "_blank");
      }
    };
    if (hocphi !== 0) {
      process();
    }
  };
  // const xacNhanThanhToann = async () => { // khác nhau ở đâu
  //     setSuccessThanhToan(true);
  //     if (idThanhToan !== null) {
  //         let thanhToan = endpoints['xacnhanthanhtoan'];
  //         let formData = new FormData();
  //         formData.append("idSinhVien", sinhvien.idSinhVien);
  //         formData.append("order_id", idThanhToan);
  //         formData.append("transactionNo", transactionNo);
  //         formData.append("trans_date", trans_date);
  //         let res2 = await AuthApis().post(thanhToan, formData);
  //         setXacNhanThanhToan(res2.data);
  //         if(res2.status === 200){
  //             alert("Thanh toán thành công.")
  //         }
  //         else{
  //             alert("Thanh toán thất bại.")
  //         }
  //         window.close();

  //     }
  // }

  console.log(DSmonHocDangKy);
  console.log(DSmonHocHocKy);

  return (
    <>
      <div class="contend">
        <HeaderSV />
        <div class="point">
          <h4 class="text-bold">Đăng Ký Môn Học Hoc Kỳ</h4>
          <div class="mt-4 mb-3 ">
            <div class="form-dangky-control">
              {/* <h6>Chọn lớp môn học </h6> */}
              <div class="d-flex">
                {/* <Form.Select
                  aria-label="Default select example select-class-dangky"
                  onChange={(e) => setSelectedLop(e.target.value)}
                  value={selectedLop}
                >
                  <option>Tất Cả</option>
                  {DSLopHoc.map((c) => {
                    return (
                      <option key={c.idLopHoc} value={c.idLopHoc}>
                        {c.tenLopHoc}
                      </option>
                    );
                  })}
                </Form.Select> */}

                <div class="form-input-search-monhoc">
                  <Form
                    onSubmit={search}
                    className="d-flex form-search-monhoc-dangky "
                  >
                    <Form.Control
                      type="text"
                      required
                      value={kw}
                      name="kw"
                      onChange={(e) => setKw(e.target.value)}
                      placeholder="Nhập tên môn học"
                      className="mr-2"
                    />
                    <Button type="submit" className="btn-search-student">
                      Tìm
                    </Button>
                  </Form>
                </div>
              </div>
            </div>

            <div class="container-dangky-monhoc mt-3">
              <h5 class="text-bold">Đăng Ký Môn</h5>
              <p class="text-header-tong">Danh sách môn học mở cho đăng ký</p>
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Tên Môn Học</th>
                    <th>Lớp</th>
                    <th>Giảng Viên</th>
                    <th>Số Lượng</th>
                    <th>Còn Lại</th>
                    <th>Thời Gian</th>
                    <th>Đăng Ký</th>
                  </tr>
                </thead>
                <tbody>
                  {DSmonHocHocKy.map((c) => {
                    var test = 0;
                    var ngayBatDauDate = new Date(c.ngayBatDau);
                    var nagyKetThucDate = new Date(c.ngayKetThuc);
                    var options = {
                      year: "numeric",
                      month: "2-digit",
                      day: "2-digit",
                    };
                    var ngayBatDauFormatted = ngayBatDauDate.toLocaleString(
                      "en-GB",
                      options
                    );
                    var ngayKetThucFormatted = nagyKetThucDate.toLocaleString(
                      "en-GB",
                      options
                    );
                    const isChecked = DSmonHocDangKy.some(
                      (b) => b.idMonHoc.idMonHocHocKy === c.idMonHocHocKy 
                    );
                    return (
                      <tr key={c.idMonHocHocKy}>
                        <td>{c.idMonHoc.tenMonHoc}</td>
                        <td>{c.idHocky.idLop.tenLopHoc}</td>
                        <td>{c.idGiangVien.hoTen}</td>
                        <td>{c.soLuong}</td>
                        <td>{c.soLuongConLai}</td>
                        <td>
                          {ngayBatDauFormatted} - {ngayKetThucFormatted}
                        </td>

                        <td>
                          {DSmonHocDangKy.map((b) => {
                            if (b.idMonHoc.idMonHocHocKy === c.idMonHocHocKy) {
                              test = test + 1;
                              return (
                                <>
                                  <InputGroup className="mb-1 form-check-input-dangky">
                                    <InputGroup.Checkbox
                                      disabled={b.thanhToan === 1}
                                      checked={isChecked}
                                      onChange={(e) => {
                                        if (e.target.checked) {
                                          dangKy(e, c.idMonHocHocKy);
                                        } else {
                                          huyDangKy(e, c.idMonHocHocKy);
                                        }
                                      }}
                                    />
                                  </InputGroup>
                                </>
                              );
                            }
                          })}
                            {test === 0?<InputGroup className="mb-1 form-check-input-dangky">
                              <InputGroup.Checkbox
                                disabled={c.soLuongConLai === 0}
                                checked={isChecked} // 🔥 Gán checked theo trạng thái dữ liệu
                                onChange={(e) => {
                                  if (e.target.checked) {
                                    dangKy(e, c.idMonHocHocKy);
                                  } else {
                                    huyDangKy(e, c.idMonHocHocKy);
                                  }
                                }}
                              />
                            </InputGroup> : <></>}
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
            <div class="container-dangky-monhoc mt-5 ">
              <h5 class="text-bold">Môn Học Đăng Ký</h5>
              <p class="text-header-tong">Danh sách môn học đã đăng ký</p>
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Tên Môn Học</th>
                    <th>Lớp</th>
                    <th>Giảng Viên</th>
                    <th>Ngày Bắt Đầu</th>
                    <th>Hủy</th>
                  </tr>
                </thead>
                <tbody>
                  {DSmonHocDangKy.map((c) => {
                    return (
                      <tr key={c.idMonHoc.idMonHocHocKy}>
                        <td>{c.idMonHoc.idMonHoc.tenMonHoc}</td>
                        <td>{c.idMonHoc.idHocky.idLop.tenLopHoc}</td>
                        <td>{c.idMonHoc.idGiangVien.hoTen}</td>
                        <td>{formatDate(c.idMonHoc.idHocky.ngayBatDau)}</td>
                        <td>
                          {c.thanhToan !== 1 ? (
                            <Button
                              onClick={(e) => {
                                huyDangKy(e, c.idMonHoc.idMonHocHocKy);
                              }}
                              className="btn-search-student"
                            >
                              Xóa
                            </Button>
                          ) : (
                            <></>
                          )}
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
            <div class="container-dangky-monhoc mt-5 ">
              <h5 class="text-bold">Học Phí</h5>
              <p class="text-header-tong">
                Danh Sách Học Phí Môn Học Đã Đăng Ký Trong Học Kì
              </p>
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Tên Môn Học</th>
                    <th>Tín Chỉ</th>
                    <th>Học Phí</th>
                    <th>Tình Trạng</th>
                  </tr>
                </thead>
                <tbody>
                  {DSmonHocDangKy.map((c) => {
                    var value = c.idMonHoc.idMonHoc.soTinChi * 700000;
                    var formattedValue = new Intl.NumberFormat("vi-VN").format(
                      value
                    );

                    return (
                      <tr key={c.idMonHoc.idMonHocHocKy}>
                        <td>{c.idMonHoc.idMonHoc.tenMonHoc}</td>
                        <td>{c.idMonHoc.idMonHoc.soTinChi}</td>
                        <td>{formattedValue}</td>
                        {c.thanhToan === 1 ? (
                          <td>Đã thanh toán</td>
                        ) : (
                          <td>Chưa Thanh Toán</td>
                        )}
                      </tr>
                    );
                  })}
                </tbody>
              </table>
              <div class="hocphi-container">
                <div class="d-flex hocphi-div">
                  <h5 class="text-bold">Tổng tiền: </h5>
                  <p class="hocphi-tong text-bold">
                    {new Intl.NumberFormat("vi-VN").format(hocphiTotal)} VNĐ
                  </p>
                </div>
                <div class="hocphi-div">
                  <Button
                    type="button"
                    onClick={(e) => thanhToan(e, hocphiTotal)}
                    className="btn btn-success hocphi-tong mt-2"
                  >
                    Thanh Toán
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default DangKyMonHoc;

import { useEffect, useState } from "react";
import { api } from "../../api";
import { useNavigate, useParams } from "react-router-dom";

const ThemSuaGiangVien = () => {

    const { id } = useParams('id');
    const [student, setStudent] = useState(null);
    const navigate = useNavigate();

    async function getSinhVien(){
        try{
            const response = await api().get(`/giaovu/giangvien/${id}`);
            console.log(response.data);
            setStudent(response.data);
            setFormData(prev => ({
                ...prev,
                ...response.data
            }))
        }catch(e){
            console.error(e);
        }
    }

    useEffect(() => {
        getSinhVien();
    },[])

    const [formData, setFormData] = useState({
        idGiangVien: student?.idGiangVien || "",
        hoTen: student?.hoTen || "",
        ngaySinh: student?.ngaySinh || "",
        diaChi: student?.diaChi || "",
        gioiTinh: student?.gioiTinh ?? "-1",
        soDienThoai: student?.soDienThoai || "",
        email: student?.email || ""
    });

    console.log(formData);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            await api().post('/giaovu/giangvien/add', formData);
            navigate('/giaovu/giangvien');
        }catch(e){
            console.error(e);
        }
    };

    return(
        <>
            <div className="row">
            <div className="margin-auto form-login form-addSV">
                <h1 className="text-center">
                    {formData.idGiangVien ? "Cập nhật giảng viên" : "Thêm giảng viên"}
                </h1>
                <div className="text-form-addSV">
                    <form onSubmit={handleSubmit}>
                        <input type="hidden" name="idGiangVien" value={formData.idGiangVien} />
                        <div className="form-floating mb-3 mt-3">
                            <input type="text" className="form-control" name="hoTen" id="name" placeholder="Họ và tên" value={formData.hoTen} onChange={handleChange} required />
                            <label htmlFor="name">Họ và tên</label>
                        </div>
                        <div className="form-floating mt-3 mb-3">
                            <input type="date" className="form-control" name="ngaySinh" id="dateofbirth" placeholder="Ngày sinh" value={formData.ngaySinh} onChange={handleChange} required />
                            <label htmlFor="dateofbirth">Ngày sinh</label>
                        </div>
                        <div className="form-floating mt-3 mb-3">
                            <input type="text" className="form-control" name="diaChi" id="address" placeholder="Địa chỉ" value={formData.diaChi} onChange={handleChange} required />
                            <label htmlFor="address">Địa chỉ</label>
                        </div>
                        <div className="form-floating mt-3 mb-3">
                            <select className="form-select" name="gioiTinh" id="sex" value={formData.gioiTinh} onChange={handleChange} required>
                                <option value="-1">Chọn giới tính</option>
                                <option value="1">Nam</option>
                                <option value="0">Nữ</option>
                            </select>
                            <label htmlFor="sex">Giới tính</label>
                        </div>
                        <div className="form-floating mt-3 mb-3">
                            <input type="text" className="form-control" name="soDienThoai" id="phonenumber" placeholder="Số điện thoại" value={formData.soDienThoai} onChange={handleChange} required />
                            <label htmlFor="phonenumber">Số điện thoại</label>
                        </div>
                        <div className="form-floating mt-3 mb-3">
                            <input type="email" className="form-control" name="email" id="email" placeholder="Email" value={formData.email} onChange={handleChange} required />
                            <label htmlFor="email">Email</label>
                        </div>
                        <div className="btn-form-addsv">
                            <button className="btn input-form-addsv" style={{background: 'green', color: 'white'}} type="submit">
                                {formData.idGiangVien ? "Cập nhật giảng viên" : "Thêm giảng viên"}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        </>
    );
}

export default ThemSuaGiangVien;
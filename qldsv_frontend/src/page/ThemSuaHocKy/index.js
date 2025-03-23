import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../../api";

const ThemSuaHocKy = () => {

    const { id } = useParams('id');
    const [student, setStudent] = useState(null);
    const [classList, setClassList] = useState([]);
    const [loaiHocKyList, setLoaiHocKyList] = useState([]);
    const navigate = useNavigate();

    async function getSinhVien(){
        try{
            const response = await api().get(`/giaovu/hocky/${id}`);
            setStudent(response.data);
            setFormData(prev => ({
                ...prev,
                ...response.data
            }))
        }catch(e){
            console.error(e);
        }
    }

    async function getLopHocList(){
        try{
            const response = await api().get("/api/dslophoc/");
            setClassList(response.data.content);
        }catch(e){
            console.error(e);
        }
    }

    async function getLoaiHocKyList(){
        try{
            const response = await api().get("/giaovu/loaihocky");
            setLoaiHocKyList(response.data);
        }catch(e){
            console.error(e);
        }
    }

    useEffect(() => {
        setFormData(prev => ({
            ...prev,
            idHocKy: student?.idHocKy || "",
            tenHocKy: student?.tenHocKy?.idLoaiHocKy || "",
            ngayBatDau: student?.ngayBatDau || "",
            ngayKetThuc: student?.ngayKetThuc || "",
            ngayDangKy: student?.ngayDangKy || "",
            ngayHetHan: student?.ngayHetHan || "",
            idLop: student?.idLop?.idLopHoc || "",
        }))
    }, [student])

    useEffect(() => {
        getSinhVien();
        getLopHocList();
        getLoaiHocKyList();
    },[])

    const [formData, setFormData] = useState({
        idHocKy: student?.idHocKy || "",
        tenHocKy: student?.tenHocKy?.idLoaiHocKy || "",
        ngayBatDau: student?.ngayBatDau || "",
        ngayKetThuc: student?.ngayKetThuc || "",
        ngayDangKy: student?.ngayDangKy || "",
        ngayHetHan: student?.ngayHetHan || "",
        idLop: student?.idLop?.idLopHoc || "",
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try{
            await api().post('/giaovu/hocky/add', formData);
            navigate('/giaovu/hocky');
        }catch(e){
            console.error(e);
        }
    };

    return(
        <>
            <div className="row">
            <div className="margin-auto form-login form-addSV">
                <h1 className="text-center">
                    {formData.idHocKy ? "Cập nhật học kỳ" : "Thêm học kỳ"}
                </h1>
                <div className="text-form-addSV">
                    <form onSubmit={handleSubmit}>
                        <input type="hidden" name="idHocKy" value={formData.idHocKy} />
                        <div className="form-floating mt-3 mb-3">
                            <select className="form-select" name="tenHocKy" id="class" value={formData.tenHocKy} onChange={handleChange} required>
                                <option value="">Chọn loại học kỳ</option>
                                {loaiHocKyList.map((lh) => (
                                    <option key={lh.idLoaiHocKy} value={lh.idLoaiHocKy}>
                                        {lh.tenHocKy}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="class">Danh sách loại học kỳ</label>
                        </div>
                        <div className="form-floating mt-3 mb-3">
                            <input type="date" className="form-control" name="ngayBatDau" id="dateofbirth" placeholder="Ngày bắt đầu" value={formData.ngayBatDau} onChange={handleChange} required />
                            <label htmlFor="dateofbirth">Ngày bắt đầu</label>
                        </div>
                        <div className="form-floating mt-3 mb-3">
                            <input type="date" className="form-control" name="ngayKetThuc" id="dateofbirth" placeholder="Ngày kết thúc" value={formData.ngayKetThuc} onChange={handleChange} required />
                            <label htmlFor="dateofbirth">Ngày kết thúc</label>
                        </div>
                        <div className="form-floating mt-3 mb-3">
                            <input type="date" className="form-control" name="ngayDangKy" id="dateofbirth" placeholder="Ngày đăng ký" value={formData.ngayDangKy} onChange={handleChange} required />
                            <label htmlFor="dateofbirth">Ngày đăng ký</label>
                        </div>
                        <div className="form-floating mt-3 mb-3">
                            <input type="date" className="form-control" name="ngayHetHan" id="dateofbirth" placeholder="Ngày hết hạn" value={formData.ngayHetHan} onChange={handleChange} required />
                            <label htmlFor="dateofbirth">Ngày hêt hạn</label>
                        </div>
                        <div className="form-floating mt-3 mb-3">
                            <select className="form-select" name="idLop" id="class" value={formData.idLop} onChange={handleChange} required>
                                <option value="">Chọn lớp học</option>
                                {classList.map((lh) => (
                                    <option key={lh.idLopHoc} value={lh.idLopHoc}>
                                        {lh.tenLopHoc}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="class">Danh sách lớp học</label>
                        </div>
                        <div className="btn-form-addsv">
                            <button className="btn input-form-addsv" style={{background: 'green', color: 'white'}} type="submit">
                                {formData.idMonHoc ? "Cập nhật học kỳ" : "Thêm học kỳ"}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        </>
    );
}

export default ThemSuaHocKy;
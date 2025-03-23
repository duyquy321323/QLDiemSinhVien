import {
    Pagination, Paper,
    styled,
    Table,
    TableBody,
    TableCell,
    tableCellClasses,
    TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import "bootstrap/dist/css/bootstrap.min.css";
import { useEffect, useState } from "react";
import { FaEdit, FaTrashAlt } from "react-icons/fa";
import { Link } from "react-router-dom";
import { api } from "../../api";
  
  const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
      backgroundColor: theme.palette.common.black,
      color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
      fontSize: 14,
    },
  }));
  
  const StyledTableRow = styled(TableRow)(({ theme }) => ({
    "&:nth-of-type(odd)": {
      backgroundColor: theme.palette.action.hover,
    },
    "&:last-child td, &:last-child th": {
      border: 0,
    },
  }));
  
  const PhongHocAdmin = () => {
    const [students, setStudents] = useState([]);
    const [search, setSearch] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
  
    useEffect(() => {
      fetchStudents();
    }, []);
  
    const fetchStudents = async () => {
      try {
        const response = await api().get("/giaovu/phonghoc");
        setStudents(response.data);
      } catch (error) {
        console.error("Error fetching students:", error);
      }
    };
  
    const handleDelete = async (id) => {
      if (window.confirm("Bạn có chắc muốn xóa phòng học này?")) {
        try {
          await api().delete(`/giaovu/phonghoc/add/${id}`);
          setStudents(students.filter((sv) => sv.idPhongHoc !== id));
        } catch (error) {
          console.error("Error deleting student:", error);
        }
      }
    };
  
    const filteredStudents = students.filter((sv) =>
      sv?.tenPhongHoc?.toLowerCase().includes(search.toLowerCase())
    );
  
    const totalPages = Math.ceil(filteredStudents.length / itemsPerPage);
    const paginatedStudents = filteredStudents.slice(
      (currentPage - 1) * itemsPerPage,
      currentPage * itemsPerPage
    );
  
    return (
      <>
        <div className="container mt-4">
          <div className="nav-tk">
            <form className="search">
              <input
                className="form-control"
                type="text"
                placeholder="Search..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </form>
          </div>
  
          <div className="title-gv d-flex justify-content-between align-items-center my-3">
            <h1 className="text-center">Danh sách phòng học</h1>
            <Link to="/giaovu/them-suaphonghoc" className="btn btn-success">
              Thêm phòng học
            </Link>
          </div>
  
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 700 }} aria-label="customized table">
              <TableHead>
                <TableRow>
                  <StyledTableCell>Mã phòng học</StyledTableCell>
                  <StyledTableCell align="right">Tên phòng học</StyledTableCell>
                  <StyledTableCell align="center">Hành động</StyledTableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {paginatedStudents.map((row) => (
                  <StyledTableRow key={row.idPhongHoc}>
                    <StyledTableCell component="th" scope="row">
                      {row.idPhongHoc}
                    </StyledTableCell>
                    <StyledTableCell align="right">
                    {row.tenPhongHoc}
                    </StyledTableCell>
                    <StyledTableCell align="center">
                      <Link
                        to={`/giaovu/them-suaphonghoc/${row.idPhongHoc}`}
                        className="btn btn-warning me-2"
                      >
                        <FaEdit size={20} />
                      </Link>
                      <button
                        onClick={() => handleDelete(row.idPhongHoc)}
                        className="btn btn-danger"
                      >
                        <FaTrashAlt size={20} />
                      </button>
                    </StyledTableCell>
                  </StyledTableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          
          <div className="d-flex justify-content-center mt-3">
            <Pagination
              count={totalPages}
              page={currentPage}
              onChange={(event, value) => setCurrentPage(value)}
              color="primary"
            />
          </div>
        </div>
      </>
    );
  };
  
  export default PhongHocAdmin;
  
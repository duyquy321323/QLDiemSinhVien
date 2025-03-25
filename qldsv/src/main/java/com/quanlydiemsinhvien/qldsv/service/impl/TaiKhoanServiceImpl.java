/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanlydiemsinhvien.qldsv.components.VerificationCodeStore;
import com.quanlydiemsinhvien.qldsv.converter.GiangVienConverter;
import com.quanlydiemsinhvien.qldsv.converter.SinhVienConverter;
import com.quanlydiemsinhvien.qldsv.converter.TaiKhoanConverter;
import com.quanlydiemsinhvien.qldsv.dto.TaiKhoanDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Giangvien;
import com.quanlydiemsinhvien.qldsv.pojo.Sinhvien;
import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;
import com.quanlydiemsinhvien.qldsv.repository.GiangvienRepository;
import com.quanlydiemsinhvien.qldsv.repository.SinhVienRepository;
import com.quanlydiemsinhvien.qldsv.repository.TaiKhoanRepository;
import com.quanlydiemsinhvien.qldsv.request.LoginRequest;
import com.quanlydiemsinhvien.qldsv.request.TaiKhoanGiangVienRequest;
import com.quanlydiemsinhvien.qldsv.request.TaikhoanCreateRequest;
import com.quanlydiemsinhvien.qldsv.service.KeycloakUserService;
import com.quanlydiemsinhvien.qldsv.service.TaiKhoanService;

/**
 *
 * @author Admin
 */
@Service
@Transactional
public class TaiKhoanServiceImpl implements TaiKhoanService {

    @Autowired
    private Cloudinary cloudinary;

    // @Autowired
    // private LoaitaikhoanRepository loaitaikhoanRepository;

    @Autowired
    private GiangvienRepository giangvienRepository;

    @Autowired
    private TaiKhoanRepository taikhoanRepository;

    @Autowired
    private TaiKhoanConverter taiKhoanConverter;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private KeycloakUserService keycloakUserService;

    @Autowired
    private VerificationCodeStore verificationCodeStore;

    @Value("${keycloak.url}")
    private String keycloakLink;
    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private GiangVienConverter giangVienConverter;

    @Autowired
    private SinhVienConverter sinhVienConverter;

    @Override
    public Taikhoan updateImg(Map<String, String> params, MultipartFile avatar) {
        Taikhoan tk = taikhoanRepository.findById(params.get("idTaiKhoan"))
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));
        if (!avatar.isEmpty()) {
            try {
                Map r = this.cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                tk.setImage(r.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(TaiKhoanService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return taikhoanRepository.save(tk);
    }

    // @Override
    // public boolean addAcount(Taikhoan t) {
    //     String pass = t.getMatKhau();
    //     t.setMatKhau(this.passwordEncoder.encode(pass));
    //     Loaitaikhoan chucvu = loaitaikhoanRepository.findByTenloaitaikhoan("ROLE_SV")
    //             .orElseThrow(() -> new RuntimeException("Loại tài khoản không tồn tại!"));
    //     t.setChucVu(chucvu);

    //     if (t.getXacNhanMk() == null || t.getTenTaiKhoan() == null || t.getMatKhau() == null) {
    //         return false;
    //     }
    //     try {
    //         Sinhvien p = sinhVienRepository.findByEmail(t.getTenTaiKhoan()).orElse(null);
    //         if (!p.getMaXacNhan().equals(t.getMaXacNhan()))
    //             return false;
    //         if (p.getIdTaiKhoan() == null && p.getEmail() != null) {
    //             p.setIdTaiKhoan(t);
    //             sinhVienRepository.save(p);
    //             return true;
    //         }
    //     } catch (Exception ex) {
    //         // Không tìm thấy kết quả
    //         ex.printStackTrace();
    //     }
    //     return false;
    // }

    @Override
    public boolean addAcountGV(TaiKhoanGiangVienRequest t) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Kiểm tra xem tên tài khoản có bị trùng không
        // long duplicateCount = taikhoanRepository.countByTenTaiKhoan(t.getTenTaiKhoan());

        // Lấy giảng viên từ repository
        Optional<Giangvien> gvOptional = giangvienRepository.findById(t.getIdGiangVien());

        try {
            if (gvOptional.isPresent()) {

                Giangvien gv = gvOptional.get();
                if (gv.getIdTaiKhoan() == null) {// Tạo tài khoản trên Keycloak trước khi lưu sinh viên
                    String keycloakUserId = keycloakUserService
                            .createUserInKeycloak(giangVienConverter.giangVienToGiangVienDTO(gv));
                    keycloakUserService.updateUserPassword(keycloakUserId, t.getMatKhau());
                    if (keycloakUserId == null) {
                        return false; // Nếu tạo tài khoản trên Keycloak thất bại thì không lưu vào database
                    }
                    gv.setIdTaiKhoan(Taikhoan.builder()
                    .idTaiKhoan(keycloakUserId).build());
                    giangvienRepository.save(gv);
                }
                // Gửi email thông báo
                message.setTo(gv.getEmail());
                message.setSubject("Thông báo tài khoản");
                message.setText("Tên tài khoản: " + t.getTenTaiKhoan() + "\nMật khẩu: "
                        + t.getXacNhanMk());
                emailSender.send(message);
                return true;
            } else {
                return false; // Tên tài khoản đã bị trùng
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Taikhoan> getTaiKhoans(Map<String, String> params) {
        PageRequest pageRequest = PageRequest.of(0, 100);
        return taikhoanRepository.findAll(pageRequest).toList();
    }

    // @Override
    // public Taikhoan getUserByUsername(String username) {
    //     return taikhoanRepository.findByTenTaiKhoan(username).orElse(null);
    // }

    // // Dang Nhap
    // @Override
    // public UserDetails loadUserByUsername(String tenTK) throws UsernameNotFoundException {
    //     Taikhoan taikhoans = this.getUserByUsername(tenTK);
    //     if (taikhoans == null) {
    //         throw new UsernameNotFoundException("Tài khoản không tồn tại!!!");
    //     }

    //     Set<GrantedAuthority> auth = new HashSet<>();
    //     auth.add(new SimpleGrantedAuthority(taikhoans.getChucVu().getTenloaitaikhoan()));
    //     return new org.springframework.security.core.userdetails.User(taikhoans.getTenTaiKhoan(),
    //             taikhoans.getMatKhau(), auth);
    // }

    @Override
    public boolean createTKSinhVien(Map<String, String> params) {
        String maXN = params.get("maXacNhan");
        String email = params.get("tenTaiKhoan");
        // Loaitaikhoan loaitaikhoan = loaitaikhoanRepository.findByTenloaitaikhoan("ROLE_SV")
        //         .orElseThrow(() -> new RuntimeException("Loại tài khoản không tồn tại"));
        Sinhvien sinhvien = sinhVienRepository.findByEmail(params.get("tenTaiKhoan"))
                .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại"));
        String mk = params.get("matKhau");
        Integer storedCode = verificationCodeStore.getCode(email);
        try {
            if (sinhvien.getIdTaiKhoan() == null) {// Tạo tài khoản trên Keycloak trước khi lưu
                // sinh viên
                String keycloakUserId = keycloakUserService
                        .createUserInKeycloak(sinhVienConverter.sinhVienToSinhVienDTO(sinhvien));
                if (keycloakUserId == null) {
                    return false; // Nếu tạo tài khoản trên Keycloak thất bại thì không lưu vào database
                }
                keycloakUserService.updateUserPassword(keycloakUserId, mk);
                Taikhoan u = Taikhoan.builder()
                        .maXacNhan(Integer.parseInt(maXN))
                        // .matKhau(mk)
                        // .chucVu(loaitaikhoan)
                        .idTaiKhoan(keycloakUserId)
                        .build();
                if (sinhvien.getIdTaiKhoan() == null && sinhvien.getEmail() != null
                        && Objects.equals(storedCode, u.getMaXacNhan())) {
                    sinhvien.setIdTaiKhoan(u);
                    sinhVienRepository.save(sinhvien);
                    return true;
                }
            }
            return false;

        } catch (NoResultException ex) {
            // Không tìm thấy kết quả
            return false;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public UserDetails getLoggedInUserDetails(Authentication authentication) {
        // Trả về thông tin UserDetails của người dùng đã đăng nhập thành công
        return (UserDetails) authentication.getPrincipal();
    }

    // @Override
    // public String GetIdTaiKhoan(UserDetails userDetails) {
    //     Taikhoan taikhoans = this.getUserByUsername(userDetails.getUsername());

    //     return taikhoans.getIdTaiKhoan();
    // }

    @Override
    public ResponseEntity<?> thayDoiMatKhau(Map<String, String> params) {
        String userId = keycloakUserService.getUserIdByUsername(params.get("tenTaiKhoan"));
        if(!keycloakUserService.checkOldPassword(params.get("tenTaiKhoan"), params.get("matKhau"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(!keycloakUserService.updateUserPassword(userId, params.get("matKhauMoi"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public void thayDoiMatKhauAD(TaikhoanCreateRequest user) {
        Taikhoan taikhoan = user.getIdTaiKhoan() == null? null : taikhoanRepository.findById(user.getIdTaiKhoan()).orElse(null);
        if(taikhoan == null || !keycloakUserService.checkOldPassword(keycloakUserService.getUsernameByUserId(taikhoan.getIdTaiKhoan()), user.getMatKhau())){
            throw new RuntimeException("Thay đổi mật khẩu thất bại!");
        }
        if (keycloakUserService.updateUserPassword(user.getIdTaiKhoan(), user.getMkMoi())) {
            return;
        }
        throw new RuntimeException("Thay đổi mật khẩu thất bại!");
    }

    @Override
    public Map<String, String> login(LoginRequest user) {
        try {
            String keycloakUrl = keycloakLink + "/protocol/openid-connect/token";

            // Cấu hình tham số
            String credentials = "client_id=" + clientId + "&client_secret=" + clientSecret
                    + "&grant_type=password&username=" + user.getTenTaiKhoan()
                    + "&password=" + user.getMatKhau();

            // Tạo RestTemplate để gửi request
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> entity = new HttpEntity<>(credentials, headers);

            // Gửi request POST đến Keycloak
            ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.POST, entity, String.class);

            // Chuyển đổi JSON response thành Map
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> tokenResponse = objectMapper.readValue(response.getBody(),
                    new TypeReference<Map<String, String>>() {
                    });

            // Trả về cả access_token và refresh_token
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", tokenResponse.get("access_token"));
            tokens.put("refresh_token", tokenResponse.get("refresh_token"));

            return tokens;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // @Override
    // public boolean authUser(String username, String password) {
    //     Taikhoan taikhoan = taikhoanRepository.findByTenTaiKhoan(username)
    //             .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));
    //     return this.passwordEncoder.matches(password, taikhoan.getMatKhau());
    // }

    @Override
    public TaiKhoanDTO getUserById(String id) {
        return taiKhoanConverter.taiKhoanToTaiKhoanDTO(
                taikhoanRepository.findById(id).get());
    }

    @Override
    public boolean sendCode(String email) {
        try {
            Sinhvien p = sinhVienRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại!"));
            SimpleMailMessage message = new SimpleMailMessage();
            if (p.getIdTaiKhoan() == null && p.getEmail() != null) {
                Random random = new Random();
                int confirmationCode = 100_000 + random.nextInt(900_000);
                verificationCodeStore.saveCode(email, confirmationCode);
                message.setTo(email);
                message.setSubject("Ma Xac Nhan");
                message.setText("Ma xac nhan cua ban la: " + confirmationCode);
                emailSender.send(message);
                return true;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // public List<Loaitaikhoan> getLoaitaikhoanList(Map<String, String> params) {
    //     try {
    //         String tenSV = params.get("tenLTK");
    //         return loaitaikhoanRepository.findByTenloaitaikhoanContaining(tenSV);
    //     } catch (Exception ex) {
    //         ex.printStackTrace();
    //         return null;
    //     }
    // }

    // @Override
    // public Loaitaikhoan getLoaiTaiKhoanById(int id) {
    //     try {
    //         return loaitaikhoanRepository.findById(id)
    //                 .orElseThrow(() -> new RuntimeException("Loại tài khoản không tồn tại!"));
    //     } catch (Exception ex) {
    //         ex.printStackTrace();
    //         return null;
    //     }
    // }

    // @Override
    // public boolean addOrUpdateLoaiTK(Loaitaikhoan ltk) {
    //     try {
    //         loaitaikhoanRepository.save(ltk);
    //         return true;
    //     } catch (Exception ex) {
    //         ex.printStackTrace();
    //         return false;
    //     }
    // }

    @Override
    public long countTaiKhoan() {
        return taikhoanRepository.count();
    }

    @Override
    public ResponseEntity<String> logout(String authHeader) {
        try {
            // Lấy Access Token từ Header
            String accessToken = authHeader.replace("Bearer ", "");

            // Gọi API của Keycloak để logout
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("refresh_token", accessToken); // Keycloak yêu cầu refresh token

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            String logoutUrl = keycloakLink + "/protocol/openid-connect/logout";

            restTemplate.postForEntity(logoutUrl, request, String.class);
            return ResponseEntity.ok("Logged out successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed!");
        }
    }
}

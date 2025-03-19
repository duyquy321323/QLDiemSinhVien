/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.av.service.impl;

import com.av.pojo.Giangvien;
import com.av.pojo.Loaitaikhoan;
import com.av.pojo.Sinhvien;
import com.av.pojo.Taikhoan;
import com.av.repository.GiangvienRepository;
import com.av.repository.TaiKhoanRepository;
import com.av.service.TaiKhoanService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@Service("userDetailsService")
public class TaiKhoanServiceImpl implements TaiKhoanService {

    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private TaiKhoanRepository taikhoanRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Value("${keycloak.url}")
    private String keycloakLink;
    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Value("${keycloak.client-id}")
    private String clientId;

    @Override
    public Taikhoan updateImg(Map<String, String> params, MultipartFile avatar) {
        Taikhoan tk = this.getTaiKhoan(params.get("idTaiKhoan"));

        if (!avatar.isEmpty()) {
            try {
                Map r = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                tk.setImage(r.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(TaiKhoanService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Taikhoan user = this.taikhoanRepository.updateImg(tk);
        return user;
    }

    @Override
    public Taikhoan getTaiKhoan(String idTaiKhoan) {
        return taikhoanRepository.getTaiKhoan(idTaiKhoan);
    }

    @Override
    public boolean addAcount(Taikhoan t) {
        String pass = t.getMatKhau();
        t.setMatKhau(this.passwordEncoder.encode(pass));
        t.setChucVu(this.getChucVu(3));

        return taikhoanRepository.addAcount(t);
    }

    @Override
    public boolean addAcountGV(Taikhoan t) {
        String pass = t.getMatKhau();
        t.setMatKhau(this.passwordEncoder.encode(pass));
        t.setChucVu(this.getChucVu(2));
        return taikhoanRepository.addAcountGV(t);
    }

    @Override
    public List<Taikhoan> getTaiKhoans(Map<String, String> params) {
        return this.taikhoanRepository.getTaiKhoan(params);
    }

    @Override
    public Loaitaikhoan getChucVu(int id) {
        return this.taikhoanRepository.getChucVu(id);
    }

    @Override
    public Taikhoan getUserByUsername(String username) {
        return this.taikhoanRepository.getUserByUsername(username);
    }

    // Dang Nhap
    @Override
    public UserDetails loadUserByUsername(String tenTK) throws UsernameNotFoundException {
        Taikhoan taikhoans = this.getUserByUsername(tenTK);
        if (taikhoans == null) {
            throw new UsernameNotFoundException("Tài khoản không tồn tại!!!");
        }

        Set<GrantedAuthority> auth = new HashSet<>();
        auth.add(new SimpleGrantedAuthority(taikhoans.getChucVu().getTenloaitaikhoan()));
        return new org.springframework.security.core.userdetails.User(taikhoans.getTenTaiKhoan(), taikhoans.getMatKhau(), auth);
    }

    @Override
    public boolean addUser(Map<String, String> params) {
        Taikhoan u = new Taikhoan();
        String maXN = params.get("maXacNhan");
        u.setTenTaiKhoan(params.get("tenTaiKhoan"));
        u.setMatKhau(this.passwordEncoder.encode(params.get("matKhau")));
        u.setChucVu(this.taikhoanRepository.getChucVu(3));
        u.setMaXacNhan(Integer.parseInt(maXN));
        
        return this.taikhoanRepository.addUser(u);
    }

    @Override
    public UserDetails getLoggedInUserDetails(Authentication authentication) {
        // Trả về thông tin UserDetails của người dùng đã đăng nhập thành công
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails;
    }

    @Override
    public String GetIdTaiKhoan(UserDetails userDetails) {
        Taikhoan taikhoans = this.getUserByUsername(userDetails.getUsername());

        return taikhoans.getIdTaiKhoan();
    }

    @Override
    public Taikhoan thayDoiMatKhau(Map<String, String> params) {
        Taikhoan tk = this.taikhoanRepository.getUserByUsername(params.get("tenTaiKhoan").toString());
        tk.setMatKhau(this.passwordEncoder.encode(params.get("matKhauMoi")));
        this.taikhoanRepository.thayDoiMatKhau(tk);
        return tk;
    }

    @Override
    public Taikhoan thayDoiMatKhauAD(Taikhoan user) {
        Taikhoan a = this.getTaiKhoan(user.getIdTaiKhoan());
        a.setMatKhau(this.passwordEncoder.encode(user.getXacNhanMk()));
        this.taikhoanRepository.thayDoiMatKhau(a);
        return user;
    }

    @Override
    public String login(Taikhoan user) {
        // URL Keycloak để lấy token
        try {
            String keycloakUrl = keycloakLink + "/protocol/openid-connect/token";

            // Cấu hình tham số cần thiết cho Keycloak
            String credentials = "client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=password&username="
                    + user.getTenTaiKhoan() + "&password=" + user.getMatKhau();

            // Tạo RestTemplate để gửi yêu cầu tới Keycloak
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> entity = new HttpEntity<>(credentials, headers);

            // Gửi yêu cầu POST tới Keycloak
            ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.POST, entity, String.class);

            // Lấy token từ response của Keycloak
            String token = response.getBody().split("\"access_token\":\"")[1].split("\"")[0];

            // Trả lại token cho frontend
            return token;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean authUser(String username, String password) {
        return this.taikhoanRepository.authUser(username, password);
    }

    @Override
    public Taikhoan getUserById(String id) {
        return this.taikhoanRepository.getTaiKhoan(id);
    }

    @Override

    public boolean sendCode(String email) {
        return this.taikhoanRepository.sendCode(email);
    }


    public List<Loaitaikhoan> getLoaitaikhoans(Map<String, String> params) {
        return this.taikhoanRepository.getLoaitaikhoans(params);
    }

    @Override
    public Loaitaikhoan getLoaiTaiKhoanById(int id) {
        return this.taikhoanRepository.getLoaiTaiKhoanById(id);
    }

    @Override
    public boolean addOrUpdateLoaiTK(Loaitaikhoan ltk) {
        return this.taikhoanRepository.addOrUpdateLoaiTK(ltk);
    }

    @Override
    public long countTaiKhoan() {
        return this.taikhoanRepository.countTaiKhoan();
    }

    @Override
    public long countLoaiTK() {
        return this.taikhoanRepository.countLoaiTK();
    }
    

}

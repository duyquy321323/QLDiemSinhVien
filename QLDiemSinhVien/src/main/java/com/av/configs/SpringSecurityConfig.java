/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.av.configs;

import com.av.filters.CustomAccessDeniedHandler;
import com.av.filters.JwtAuthenticationKeycloakConverter;
import com.av.filters.RestAuthenticationEntryPoint;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Properties;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author FPTSHOP
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.av.controllers",
    "com.av.repository",
    "com.av.service",
    "com.av.components"
})
@Order(2)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserDetailsService userDetailsService;
    
    @Autowired
    @Qualifier("customSuccessHandler")
    private CustomSuccessHandler customSuccessHandler;

    @Value("${keycloak.jwk}")
    String jwkSetUri;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }

    @Override   
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/giaovu/**").access("hasRole('ROLE_GVU')")
                .and()
                .formLogin().loginPage("/")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(customSuccessHandler)
                .failureUrl("/?error")
                .and().logout().logoutSuccessUrl("/").and().exceptionHandling().accessDeniedPage("/?accessDenied");
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).jwsAlgorithm(SignatureAlgorithm.RS256).build();
    }


//    private PrivateKey getPrivateKeyFromFile(String path) throws Exception {
//        byte[] keyBytes = Files.readAllBytes(Paths.get(path));  // Đọc byte của khóa
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);  // Tạo đối tượng key spec
//        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);  // Tạo PrivateKey từ byte array
//    }
//
//    // Đọc khóa công khai từ file và tạo PublicKey
//    private PublicKey getPublicKeyFromFile(String path) throws Exception {
//        try {
//            // Lấy đường dẫn từ classpath
//            URL url = getClass().getClassLoader().getResource(path);
//            if (url == null) {
//                throw new FileNotFoundException("Khóa công khai không tìm thấy");
//            }
//            byte[] keyBytes = Files.readAllBytes(Paths.get(url.toURI()));  // Đọc khóa công khai từ classpath
//            String keyString = new String(keyBytes, Charset.defaultCharset());
//
//            // Remove the first and last lines (-----BEGIN PUBLIC KEY----- and -----END PUBLIC KEY-----)
//            keyString = keyString.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
//            keyString = keyString.replaceAll("\\s+", "");
//
//            byte[] decoded = Base64.getDecoder().decode(keyString);
//
//            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return keyFactory.generatePublic(spec);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder() throws Exception {
//        // Đọc khóa công khai từ file (thay đổi đường dẫn đến khóa công khai của bạn)
//        Key publicKey = getPublicKeyFromFile("public_key.pem.pub");
//
//        // Tạo JwtDecoder từ khóa công khai
//        return NimbusJwtDecoder
//                .withPublicKey((RSAPublicKey) publicKey)
//                .build();  // Tạo JwtDecoder sử dụng công khai khóa RSA
//    }
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "dhcvsbuew",
                        "api_key", "127245518483839",
                        "api_secret", "1CExekjHALzqnQGG7Hr-FoOWlk8",
                        "secure", true));
        return cloudinary;
    }
    
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    @Bean
    public CustomDateEditor customDateEditor() {
        return new CustomDateEditor(simpleDateFormat(), true);
    }
    @Bean 
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("nnha.dhti15a2hn@sv.uneti.edu.vn");
        mailSender.setPassword("oqqswqhsufhgexxi");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);

        return mailSender;  
    
    }

}

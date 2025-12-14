package com.thanglong.quanlythuedat.infrastructure.repository.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "NguoiDung")
@Data
public class NguoiDungEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maNguoiDung")
    private Long maNguoiDung; // Sửa lại tên biến cho khớp cột DB để đỡ nhầm

    @Column(name = "tenDangNhap")
    private String tenDangNhap;

    @Column(name = "matKhau")
    private String matKhau;

    @Column(name = "hoTen")
    private String hoTen;

    @Column(name = "soDinhDanh")
    private String soDinhDanh;

    @Column(name = "quocTich")
    private String quocTich;

    @Column(name = "loaiDoiTuong")
    private String loaiDoiTuong;

    private String email;
    private String sdt;
    
    @Column(name = "diaChi")
    private String diaChi;

    @Column(name = "maVaiTro")
    private Integer maVaiTro; // Lưu ID vai trò (1,2,3,4)

    @Column(name = "trangThai")
    private Boolean trangThai;

    @Column(name = "ngayTao")
    private LocalDateTime ngayTao = LocalDateTime.now();
    
    // Helper để code cũ getVaiTro() dạng String không bị lỗi
    @Transient
    public String getVaiTro() {
        if(maVaiTro == null) return "USER";
        return switch (maVaiTro) {
            case 1 -> "ADMIN";
            case 2 -> "CAN_BO";
            case 3 -> "QL_DAT_DAI";
            case 4 -> "CHU_DAT";
            default -> "USER";
        };
    }
    
    public void setVaiTro(String roleName) {
         if ("ADMIN".equalsIgnoreCase(roleName)) this.maVaiTro = 1;
         else if ("CAN_BO".equalsIgnoreCase(roleName)) this.maVaiTro = 2;
         else if ("QL_DAT_DAI".equalsIgnoreCase(roleName)) this.maVaiTro = 3;
         else this.maVaiTro = 4;
    }
}
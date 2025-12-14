package com.thanglong.quanlythuedat.infrastructure.repository.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "HoSoKhaiThue")
@Data
public class HoSoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHoSo")
    private Long maHoSo;

    @Column(name = "maThuaDat")
    private Long maThuaDat;

    @Column(name = "maChuDat")
    private Long maChuDat; // Code cũ có thể gọi là maNguoiKhai, cần map getter

    @Column(name = "dienTichKhaiBao")
    private Double dienTichKhaiBao;

    @Column(name = "dienTichThucTe")
    private Double dienTichThucTe;

    @Column(name = "mucDichSuDungKhaiBao")
    private String mucDichSuDungKhaiBao;

    @Column(name = "tongGiaTriDat")
    private Double tongGiaTriDat;

    @Column(name = "soTienPhaiDong")
    private Double soTienPhaiDong; 

    @Column(name = "dauHieuSaiLech")
    private Boolean dauHieuSaiLech;

    @Column(name = "fileDinhKemGiaoDich")
    private String fileDinhKemGiaoDich;

    @Column(name = "trangThaiXuLy")
    private String trangThaiXuLy;

    @Column(name = "ngayTao")
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "ngayDuyet")
    private LocalDateTime ngayDuyet;
    
    // Alias cho code cũ đỡ sửa nhiều
    public Long getMaNguoiKhai() { return maChuDat; }
    public void setMaNguoiKhai(Long id) { this.maChuDat = id; }
    
    public Double getSoTienPhaiNop() { return soTienPhaiDong; }
    public void setSoTienPhaiNop(Double tien) { this.soTienPhaiDong = tien; }
    
    public String getTrangThai() { return trangThaiXuLy; }
    public void setTrangThai(String st) { this.trangThaiXuLy = st; }
    
    public Boolean getDauHieuGianLan() { return dauHieuSaiLech; }
    public void setDauHieuGianLan(Boolean b) { this.dauHieuSaiLech = b; }
    
    @Transient // Cần cho DTO nhập vào
    private Integer namKhaiThue;
}
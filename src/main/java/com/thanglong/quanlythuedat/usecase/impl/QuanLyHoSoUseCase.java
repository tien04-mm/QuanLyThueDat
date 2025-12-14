package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.*;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.*;
import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.dto.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuanLyHoSoUseCase implements IQuanLyHoSoUseCase {

    @Autowired private JpaThuaDatRepo thuaDatRepo;
    @Autowired private JpaHoSoRepo hoSoRepo;
    @Autowired private JpaBangGiaDatRepo bangGiaDatRepo;
    @Autowired private JpaLoaiDatRepo loaiDatRepo;
    @Autowired private JpaNhatKyXuLyRepo nhatKyRepo; 
    @Autowired private JpaKhuVucRepo khuVucRepo;     

    @Override
    public HoSoOutputDTO nopHoSoKhaiThue(HoSoInputDTO input, MultipartFile file) {
        // 1. Tìm thửa đất
        ThuaDatEntity thuaDat = thuaDatRepo.findById(input.getMaThuaDat())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thửa đất!"));

        boolean gianLan = false;
        StringBuilder msg = new StringBuilder();
        
        // 2. Logic check
        if (input.getDienTichKhaiBao() < thuaDat.getDienTichGoc() * 0.98) {
            gianLan = true;
            msg.append("Diện tích khai báo thấp hơn thực tế. ");
        }

        // 3. Tìm giá đất
        BangGiaDatEntity bangGia = bangGiaDatRepo.findByMaKhuVucAndMaLoaiDatAndTrangThai(
                thuaDat.getMaKhuVuc(), 
                thuaDat.getMaLoaiDat(),
                "Hiệu lực"
        ).orElseThrow(() -> new RuntimeException("Chưa có bảng giá đất cho khu vực/loại đất này!"));

        // 4. Lấy hệ số K
        KhuVucEntity khuVuc = khuVucRepo.findById(thuaDat.getMaKhuVuc())
                .orElseThrow(() -> new RuntimeException("Khu vực không tồn tại"));
        
        // 5. Lấy thuế suất
        LoaiDatEntity loaiDat = loaiDatRepo.findById(thuaDat.getMaLoaiDat())
                .orElseThrow(() -> new RuntimeException("Loại đất không tồn tại"));

        // 6. Tính toán
        double tongGiaTri = input.getDienTichKhaiBao() * bangGia.getDonGiaM2() * khuVuc.getHeSoK();
        double thuePhaiNop = tongGiaTri * loaiDat.getThueSuat();

        // 7. Lưu hồ sơ
        HoSoEntity hoSo = new HoSoEntity();
        hoSo.setMaThuaDat(input.getMaThuaDat());
        hoSo.setMaNguoiKhai(input.getMaNguoiDung()); // Map vào maChuDat
        
        hoSo.setDienTichKhaiBao(input.getDienTichKhaiBao());
        hoSo.setDienTichThucTe(thuaDat.getDienTichGoc());
        hoSo.setMucDichSuDungKhaiBao(input.getMucDichSuDungKhaiBao());
        hoSo.setTongGiaTriDat(tongGiaTri);
        hoSo.setSoTienPhaiNop(thuePhaiNop);
        hoSo.setDauHieuGianLan(gianLan);
        
        String trangThaiBanDau = gianLan ? "CANH_BAO" : "CHO_DUYET";
        hoSo.setTrangThai(trangThaiBanDau);

        if (file != null && !file.isEmpty()) {
            hoSo.setFileDinhKemGiaoDich("FILE_" + System.currentTimeMillis());
        }
        
        hoSo = hoSoRepo.save(hoSo);
        ghiNhatKy(hoSo.getMaHoSo(), null, "KHOI_TAO", trangThaiBanDau, "Nộp hồ sơ: " + msg);

        HoSoOutputDTO out = new HoSoOutputDTO();
        out.setMaHoSo(hoSo.getMaHoSo());
        out.setSoTienPhaiNop(thuePhaiNop);
        out.setTongGiaTriDat(tongGiaTri);
        out.setTrangThai(trangThaiBanDau);
        return out;
    }

    @Override
    public String duyetHoSo(Long maHoSo, boolean dongY, String lyDo) {
        HoSoEntity h = layChiTietHoSo(maHoSo);
        String ttCu = h.getTrangThai();
        String ttMoi = dongY ? "DA_DUYET" : "TU_CHOI";
        h.setTrangThai(ttMoi);
        // Note: Entity mới có trường ngayDuyet, cần set vào
        h.setNgayDuyet(LocalDateTime.now());
        hoSoRepo.save(h);
        ghiNhatKy(maHoSo, 1L, ttCu, ttMoi, lyDo); // 1L là ID Admin tạm
        return "Cập nhật thành công: " + ttMoi;
    }

    @Override
    public void thanhToanThue(Long maHoSo) {
        HoSoEntity h = layChiTietHoSo(maHoSo);
        h.setTrangThai("DA_NOP_TIEN");
        hoSoRepo.save(h);
        ghiNhatKy(maHoSo, h.getMaNguoiKhai(), "DA_DUYET", "DA_NOP_TIEN", "Thanh toán");
    }

    private void ghiNhatKy(Long maHoSo, Long maCanBo, String tu, String den, String ghiChu) {
        NhatKyXuLyEntity nk = new NhatKyXuLyEntity();
        nk.setMaHoSo(maHoSo);
        nk.setMaCanBo(maCanBo);
        nk.setTrangThaiTu(tu);
        nk.setTrangThaiDen(den);
        nk.setGhiChu(ghiChu);
        nk.setThoiGianXuLy(LocalDateTime.now());
        nhatKyRepo.save(nk);
    }
    
    @Override public List<NhatKyXuLyEntity> xemLichSuXuLy(Long maHoSo) { 
        return nhatKyRepo.findByMaHoSo(maHoSo); 
    }
    
    @Override public HoSoEntity layChiTietHoSo(Long id) { 
        return hoSoRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found")); 
    }
    
    @Override public List<HoSoEntity> layDanhSachHoSo() { 
        return hoSoRepo.findAll(); 
    }
    
    // Sửa: Gọi đúng hàm findByMaChuDat trong Repo
    @Override public List<HoSoEntity> layLichSuNopThue(Long maNguoiDung) { 
        return hoSoRepo.findByMaChuDat(maNguoiDung); 
    }

    @Override public BaoCaoThongKeDTO layBaoCaoThongKe(Integer nam, String khuVuc) { 
        // Logic dummy để không lỗi biên dịch, bạn có thể implement sau
        return new BaoCaoThongKeDTO(); 
    }

    // Fix: Cập nhật hàm xuất Excel để map đúng tên getter mới
    @Override public ByteArrayInputStream xuatBaoCaoExcel() { 
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Bao Cao");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Mã Hồ Sơ");
            header.createCell(1).setCellValue("Tiền Phải Nộp");
            header.createCell(2).setCellValue("Trạng Thái");
            
            List<HoSoEntity> list = hoSoRepo.findAll();
            int i = 1;
            for(HoSoEntity h : list){
                Row r = sheet.createRow(i++);
                r.createCell(0).setCellValue(h.getMaHoSo());
                r.createCell(1).setCellValue(h.getSoTienPhaiNop() != null ? h.getSoTienPhaiNop() : 0);
                r.createCell(2).setCellValue(h.getTrangThai());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) { return null; }
    }
}
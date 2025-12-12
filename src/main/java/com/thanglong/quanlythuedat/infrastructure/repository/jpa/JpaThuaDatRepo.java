package com.thanglong.quanlythuedat.infrastructure.repository.jpa;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JpaThuaDatRepo extends JpaRepository<ThuaDatEntity, Long> {
    List<ThuaDatEntity> findByMaChuSoHuu(Long maChuSoHuu);
    Optional<ThuaDatEntity> findBySoToAndSoThua(String soTo, String soThua);
}
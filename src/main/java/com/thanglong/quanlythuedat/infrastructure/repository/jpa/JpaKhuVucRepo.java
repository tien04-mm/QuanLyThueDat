package com.thanglong.quanlythuedat.infrastructure.repository.jpa;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.KhuVucEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaKhuVucRepo extends JpaRepository<KhuVucEntity, Integer> {
}
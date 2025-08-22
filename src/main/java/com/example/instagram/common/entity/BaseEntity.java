package com.example.instagram.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate`
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Soft Delete
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;  // <-- 필드 추가

    public void softDelete() {
        this.deleted = true;
    }

    public void restore() {  // 복원용 메서드
        this.deleted = false;
    }
}

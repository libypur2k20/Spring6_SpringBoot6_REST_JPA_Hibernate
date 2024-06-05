package com.eazybytes.eazyschool.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass   // Fields in this class will also be mapped to table columns in every derived class.
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "createdBy", "updatedAt", "updatedBy"})
public class BaseEntity {

    @CreatedDate
    @Column( updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column( updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column( insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column( insertable = false)
    private String updatedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(createdAt, that.createdAt) && Objects.equals(createdBy, that.createdBy) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, createdBy, updatedAt, updatedBy);
    }
}

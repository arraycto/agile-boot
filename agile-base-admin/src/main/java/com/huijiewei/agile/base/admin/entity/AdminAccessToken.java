package com.huijiewei.agile.base.admin.entity;

import com.huijiewei.agile.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = AdminAccessToken.TABLE_NAME)
@DynamicInsert
@DynamicUpdate
public class AdminAccessToken extends BaseEntity {
    public static final String TABLE_NAME = "ag_admin_access_token";

    private Integer adminId;
    private String clientId;
    private String accessToken;
    private String userAgent;

    @UpdateTimestamp
    private Instant updatedAt;
}

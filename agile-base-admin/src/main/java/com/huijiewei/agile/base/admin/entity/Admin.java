package com.huijiewei.agile.base.admin.entity;

import com.huijiewei.agile.base.entity.TimestampEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Admin.TABLE_NAME)
@DynamicInsert
@DynamicUpdate
public class Admin extends TimestampEntity {
    public static final String TABLE_NAME = "ag_admin";

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String avatar;

    @ManyToOne
    @JoinColumn(name = "adminGroupId")
    private AdminGroup adminGroup;
}

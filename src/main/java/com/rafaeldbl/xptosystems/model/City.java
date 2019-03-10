package com.rafaeldbl.xptosystems.model;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "ibge_id", nullable = false, length = 128)
    private Long ibgeId;

    @Column(name = "uf", nullable = false, length = 3)
    private String uf;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "no_accents", nullable = false, length = 128)
    private String unnaccentName;

    @Column(name = "alternative_names", length = 256)
    private String alternativeNames;

    @Column(name = "microregion", length = 128)
    private String microregion;

    @Column(name = "mesoregion", length = 128)
    private String mesoregion;

    @Column(name = "capital")
    private boolean isCapital;

    @Column(name = "created_at")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedAt;
}

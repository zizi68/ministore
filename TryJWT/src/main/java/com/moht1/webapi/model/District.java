package com.moht1.webapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "district")
@Getter
@Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "prefix", length = 100)
    private String prefix;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @JsonIgnore
    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Ward> wards = new HashSet<>();

}

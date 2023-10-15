package com.moht1.webapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "poster")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Poster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 300)
    private String name;

    private int type;

    @Column(name = "is_active")
    private boolean isActive;
}

package com.trinh.webapi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ward")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ward {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
	
	@Column(name="name",length = 50)
	private String name;
	
	@Column(name="prefix",length = 20)
	private String prefix;
	
	@ManyToOne
    @JoinColumn(name = "district_id")
	private District district;
	
	@JsonIgnore
	@OneToMany(mappedBy = "ward",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

}

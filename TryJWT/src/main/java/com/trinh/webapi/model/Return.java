package com.trinh.webapi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "returns")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Return {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date date;
	
	private String reason;

	@Column(name = "is_approved")
	private Boolean isApproved;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(name = "refund_type")
	private String refundType;
	
	@Column(name = "total_price")
	private float totalPrice;
	
	@JsonIgnore
	@OneToMany(mappedBy = "return0", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ReturnDetail> returnDetails = new ArrayList<ReturnDetail>();
	
}

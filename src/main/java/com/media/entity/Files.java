package com.media.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
public class Files {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
    private Long id;
     
    @Column(name="name", nullable = false)
    @Getter @Setter
    private String name;
    
    @Column(name="type", nullable = false)
    @Getter @Setter
    private String type;
    
    @Column(name="data", nullable = false, length=100000)
    @Getter @Setter
    @Lob
    private byte[] data;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    @Getter @Setter
    private User user;

}

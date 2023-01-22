package com.media.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
    private Long id;
     
    @Column(name="email", nullable = false, unique = true)
    @Getter @Setter
    private String email;
     
    @Column(name="password", nullable = false)
    @Getter @Setter
    private String password;
     
    @Column(name="firstName", nullable = false)
    @Getter @Setter
    private String firstName;
     
    @Column(name="lastName", nullable = false)
    @Getter @Setter
    private String lastName;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    @Getter @Setter
    private List<Role> roles = new ArrayList<>();
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    @Getter @Setter
    private List<Files> files = new ArrayList<>();
}

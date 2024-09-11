package com.foodlink.entity;

import com.foodlink.permissions.UserTypeEnum;
import com.foodlink.roles.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String cnpj;
    @Column
    private String username;
    @Column(nullable = false, unique = true)
    private String password;
    //@Lob
    //@Column(name = "profile_picture", nullable = false, unique = true)
    //private byte[] profile_picture;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserTypeEnum userTypeEnum;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}

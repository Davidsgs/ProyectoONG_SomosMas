package com.restteam.ong.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class User {

    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Column(unique = true)
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String photo;

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id")
    private Role role;

    private Boolean deleted = false;

    private Long createdAt;

    private Long updatedAt;

}

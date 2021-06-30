package com.restteam.ong.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="contacts")
@SQLDelete(sql = "UPDATE contacts SET deleted = true WHERE id=?")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    private Integer phone;
    private String email;
    private String message;
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = Boolean.FALSE;
    private Long deletedAt;
}

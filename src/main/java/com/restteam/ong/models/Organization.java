package com.restteam.ong.models;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;


@Table
@Entity
@SQLDelete(sql = "UPDATE organization SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
@Data
public class Organization {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column
    private String address;

    @Column
    private Integer phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String welcomeText;

    @Column(columnDefinition = "TEXT")
    private String aboutUsText;

    @Column(nullable = false)
    private Long createdAt;

    @Column
    private String facebookUrl;

    @Column
    private String instagramUrl;

    @Column
    private String linkedinUrl;


    @Column(nullable = false)
    private Long updatedAt;

    @Column(nullable = false)
    private Boolean deleted = false;

    @OneToMany(mappedBy = "organizationId", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    private Set<Slide> slides;

}

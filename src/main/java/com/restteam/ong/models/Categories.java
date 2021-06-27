package com.restteam.ong.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Data
@Getter
@Setter
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE id_categories=?")
@Where(clause = "deleted=false")
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "image",nullable = false)
    private String image;

    @CreationTimestamp
    @Column(name = "regDate", updatable=false)
    private Timestamp regDate;
    @Column(name = "upDateDate",nullable = false)
    @UpdateTimestamp
    private Timestamp upDateDate;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = Boolean.FALSE;
    @Column(name = "news",nullable = false)
    @OneToMany(mappedBy = "categories")
    private Set<News> news;
}

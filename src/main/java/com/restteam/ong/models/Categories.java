package com.restteam.ong.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import java.sql.Timestamp;


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

    private String name;

    private String description;

    private String image;

    @CreationTimestamp
    private Timestamp regDate;

    @UpdateTimestamp
    private Timestamp upDateDate;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;

}

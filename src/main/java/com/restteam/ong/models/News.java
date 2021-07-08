package com.restteam.ong.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Data
@SQLDelete(sql = "UPDATE news SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String content;
    @NotBlank
    private String image;

    
    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "categories_id",nullable = false)
    private Categories categories;

    // @CreationTimestamp
    private Long regDate;

    // @UpdateTimestamp
    private Long upDateDate;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = Boolean.FALSE;

}

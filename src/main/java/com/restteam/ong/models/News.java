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
@SQLDelete(sql = "UPDATE news SET deleted = true WHERE id_news=?")
@Where(clause = "deleted=false")
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String content;
    @NonNull
    private String image;

    @ManyToOne
    @JoinColumn(name = "categories_id",nullable = false)
    private Categories categories;

    @CreationTimestamp
    private Timestamp regDate;

    @UpdateTimestamp
    private Timestamp upDateDate;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = Boolean.FALSE;

}

package com.restteam.ong.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "activity")
@SQLDelete(sql = "UPDATE activity SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class Activity {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "createdAt", nullable = false)
    private Long createdAt;

    @Column(name = "updatedAt", nullable = true)
    private Long updatedAt;
    
}

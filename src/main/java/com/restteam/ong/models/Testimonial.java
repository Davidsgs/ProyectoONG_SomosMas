package com.restteam.ong.models;

import com.sun.istack.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="testimonial")
@SQLDelete(sql = "UPDATE testimonial SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Testimonial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String image;
    private String content;
    private boolean deleted = Boolean.FALSE;
    private Long createdAt;
    private Long updatedAt;

}

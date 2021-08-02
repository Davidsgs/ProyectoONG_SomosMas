package com.restteam.ong.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;
    @NotNull
    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    @NotNull
    private String image;
    private String description;
    @Schema(hidden = true)
    private boolean deleted = Boolean.FALSE;
    @Schema(hidden = true)
    private Long createdAt;
    @Schema(hidden = true)
    private Long updatedAt;

    public Member(String name, String facebookUrl, String instagramUrl, String linkedinUrl, String image,
            String description) {
        this.name = name;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.linkedinUrl = linkedinUrl;
        this.image = image;
        this.description = description;
    }
}

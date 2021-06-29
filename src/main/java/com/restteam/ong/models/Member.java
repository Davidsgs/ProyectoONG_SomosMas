package com.restteam.ong.models;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private  Long id;
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

    public Member() { }

    public Member(String name, String facebookUrl, String instagramUrl, String linkedinUrl, String image, String description) {
        this.name = name;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.linkedinUrl = linkedinUrl;
        this.image = image;
        this.description = description;
    }
}

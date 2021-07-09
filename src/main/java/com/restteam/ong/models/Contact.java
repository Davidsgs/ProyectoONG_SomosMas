package com.restteam.ong.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

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
    @Schema(hidden = true)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String message;
    @Column(columnDefinition = "boolean default false")
    @Schema(hidden = true)
    private Boolean deleted = Boolean.FALSE;
    @Schema(hidden = true)
    private Long deletedAt;

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", deleted=" + deleted +
                ", deletedAt=" + deletedAt +
                '}';
    }
}

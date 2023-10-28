package ru.kpfu.itis.belskaya.models;

import lombok.*;
import ru.kpfu.itis.belskaya.helpers.SlugGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Elizaveta Belskaya
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "It can't be empty")
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "It can't be empty")
    private String text;
    private String slug;

    @PrePersist
    @PreUpdate
    @Column(nullable = false, unique = true)
    public void setSlug() {
        this.slug = SlugGenerator.generateSlug(title);
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;



}

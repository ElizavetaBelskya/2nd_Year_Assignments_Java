package ru.kpfu.itis.belskaya.models.forms;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleUpdateDto {

    @NotBlank(message = "It can't be empty")
    private String title;

    @NotBlank(message = "It can't be empty")
    private String text;

}

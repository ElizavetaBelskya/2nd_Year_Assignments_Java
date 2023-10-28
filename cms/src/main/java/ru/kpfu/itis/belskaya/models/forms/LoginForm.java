package ru.kpfu.itis.belskaya.models.forms;

import lombok.*;

/**
 * @author Elizaveta Belskaya
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginForm {

    private String password;
    private String email;

}

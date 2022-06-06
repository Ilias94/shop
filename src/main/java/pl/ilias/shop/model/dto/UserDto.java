package pl.ilias.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.history.RevisionMetadata;
import pl.ilias.shop.validator.PasswordValid;
import pl.ilias.shop.validator.group.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@PasswordValid(groups = Create.class)
public class UserDto {
    @Null
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank(groups = Create.class)
    @Length(min = 5, max = 255, groups = Create.class)
    private String password;
    private String confirmPassword;
    private Integer revisionNumber;
    private RevisionMetadata.RevisionType revisionType;
}

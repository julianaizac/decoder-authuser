package com.izac.ead.authuser.dtos;


import com.fasterxml.jackson.annotation.JsonView;
import com.izac.ead.authuser.validations.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRecordDto(@JsonView(UserView.RegistrationPost.class)
                            @NotBlank(message = "Username is mandatory", groups = UserView.RegistrationPost.class)
                            @Size(min = 4, max = 50, message = "Size must be between 4 and 50", groups = UserView.RegistrationPost.class)
                            String username,

                            @JsonView(UserView.RegistrationPost.class)
                            @NotBlank(message = "Email is mandatory", groups = UserView.RegistrationPost.class)
                            @Email(message = "Email must be in the expected format", groups = UserView.RegistrationPost.class)
                            String email,

                            @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
                            @NotBlank(message = "Password is mandatory", groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
                            @PasswordConstraint(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
                            String password,

                            @JsonView(UserView.PasswordPut.class)
                            @NotBlank(message = "Old Password is mandatory", groups = UserView.PasswordPut.class)
                            @PasswordConstraint(groups = {UserView.PasswordPut.class})String oldPassword,

                            @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
                            @NotBlank(message = "Full Name is mandatory", groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
                            String fullName,

                            @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
                            String phoneNumber,

                            @JsonView(UserView.ImagePut.class)
                            @NotBlank(message = "Image URL is mandatory", groups = UserView.ImagePut.class)
                            String imageUrl) {

    public interface UserView {
        interface RegistrationPost {}
        interface UserPut {}
        interface PasswordPut {}
        interface ImagePut {}
    }

}

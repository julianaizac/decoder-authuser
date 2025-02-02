package com.izac.ead.authuser.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.izac.ead.authuser.dtos.UserRecordDto;
import com.izac.ead.authuser.models.UserModel;
import com.izac.ead.authuser.services.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(Pageable pageable){
        Page<UserModel> userModelPage = userService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable UUID userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(userId).get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID userId){
        userService.delete(userService.findById(userId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID userId,
                                             @RequestBody @Validated(UserRecordDto.UserView.UserPut.class)
                                             @JsonView(UserRecordDto.UserView.UserPut.class)
                                             UserRecordDto userRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userService.findById(userId).get(), userRecordDto));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePasswordUser(@PathVariable UUID userId,
                                                     @RequestBody @Validated(UserRecordDto.UserView.PasswordPut.class)
                                                     @JsonView(UserRecordDto.UserView.PasswordPut.class)
                                                     UserRecordDto userRecordDto){
        Optional<UserModel> userModelOptional = userService.findById(userId);

        if(!userModelOptional.get().getPassword().equals(userRecordDto.oldPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password!");
        }

        userService.updatePasswordUser(userModelOptional.get(), userRecordDto);

        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImageUser(@PathVariable UUID userId,
                                                  @RequestBody @Validated(UserRecordDto.UserView.ImagePut.class)
                                                  @JsonView(UserRecordDto.UserView.ImagePut.class)
                                                  UserRecordDto userRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateImageUser(userService.findById(userId).get(), userRecordDto));
    }


}

package com.izac.ead.authuser.services;

import com.izac.ead.authuser.dtos.UserRecordDto;
import com.izac.ead.authuser.models.UserModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserModel> findAll();

    Optional<UserModel> findById(UUID userId);

    void delete(UserModel userModel);

    UserModel registerUser(UserRecordDto userRecordDto);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserModel updateUser(UserModel userModel, UserRecordDto userRecordDto);

    void updatePasswordUser(UserModel userModel, UserRecordDto userRecordDto);

    UserModel updateImageUser(UserModel userModel, UserRecordDto userRecordDto);
}

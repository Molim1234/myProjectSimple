package com.example.demo.mapper;

import com.example.demo.dto.CreateUser;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.Team;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserMapper {
    public User toUser(CreateUser createUser, Team team) {
        return new User(createUser.getFirstName(), createUser.getLastName(),
                createUser.getEmail(), team);
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getTeam().getId(), user.getTeam().getName(), user.getRole());
    }

    public List<UserResponse> toUserResponseList(List<User> users) {
        return users.stream().map(this::toUserResponse).toList();
    }
}

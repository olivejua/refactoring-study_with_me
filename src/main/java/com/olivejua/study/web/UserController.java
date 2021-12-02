package com.olivejua.study.web;

import static com.olivejua.study.utils.UrlPaths.*;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.UserService;
import com.olivejua.study.web.dto.user.UserSignupRequestDto;
import com.olivejua.study.web.dto.user.UserSignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(USERS)
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(Users.SIGN_UP)
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody UserSignupRequestDto requestDto) {
        UserSignupResponseDto responseDto = userService.signUp(requestDto);
        return ResponseEntity
                .created(Users.createUser(responseDto.getUserId()))
                .body(responseDto);
    }

    @GetMapping(Users.NAME_LIST)
    public ResponseEntity<List<String>> getNames() {
        List<String> names = userService.findAllNames();
        return ResponseEntity.ok(names);
    }

    @PutMapping(Users.NAME)
    public ResponseEntity<Void> changeName(@RequestParam String name, @LoginUser SessionUser user) {
        userService.changeProfile(user.toEntity().getId(), name);
        return ResponseEntity.noContent().build();
    }
}

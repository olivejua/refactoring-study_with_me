package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.UserService;
import com.olivejua.study.web.dto.user.UserSignupRequestDto;
import com.olivejua.study.web.dto.user.UserSignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody UserSignupRequestDto requestDto) {
        UserSignupResponseDto responseDto = userService.signUp(requestDto);
        return ResponseEntity
                .created(URI.create("/user/"+responseDto.getUserId()))
                .body(responseDto);
    }

    @GetMapping("/allTheNames")
    public ResponseEntity<List<String>> getAllTheNames() {
        List<String> names = userService.findAllNames();
        return ResponseEntity.ok(names);
    }

    @PutMapping("/name/{changedName}")
    public ResponseEntity<Void> changeName(@PathVariable String changedName, @LoginUser SessionUser user) {
        userService.changeProfile(user.toEntity().getId(), changedName);
        return ResponseEntity.noContent().build();
    }
}

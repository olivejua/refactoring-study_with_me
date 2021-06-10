package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.PlaceService;
import com.olivejua.study.web.dto.PageDto;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.olivejua.study.web.dto.board.place.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/place")
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/{postId}")
    public PostReadResponseDto read(@PathVariable Long postId, PageDto pageInfo, @LoginUser SessionUser user) {
        PostReadResponseDto responseDto = placeService.read(postId, user.toEntity());
        responseDto.savePageInfo(pageInfo);

        return responseDto;
    }

    @PostMapping
    public Long post(@RequestBody PostSaveRequestDto requestDto, @LoginUser SessionUser user) {
        return placeService.post(requestDto, user.toEntity());
    }

    @PutMapping("/{postId}")
    public Long update(@PathVariable Long postId, @RequestBody PostSaveRequestDto requestDto) {
        return placeService.update(postId, requestDto);
    }

    @DeleteMapping("/{postId}")
    public Long delete(@PathVariable Long postId) {
        placeService.delete(postId);
        return postId;
    }
}

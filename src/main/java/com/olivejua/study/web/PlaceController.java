package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.PlaceService;
import com.olivejua.study.web.dto.PageDto;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.olivejua.study.web.dto.board.place.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/place")
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostReadResponseDto> read(@PathVariable Long postId, PageDto pageInfo
            , @LoginUser SessionUser user, HttpServletRequest httpServletRequest) {
        PostReadResponseDto responseDto = placeService.read(
                postId, user.toEntity(),
                httpServletRequest.getSession().getServletContext().getRealPath("/"));
        responseDto.savePageInfo(pageInfo);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody PostSaveRequestDto requestDto, @LoginUser SessionUser user, MultipartFile imageFile) {
        Long savedPostId = placeService.post(requestDto, user.toEntity());
        return ResponseEntity.created(URI.create("/place/"+savedPostId)).build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> update(@PathVariable Long postId, @RequestBody PostSaveRequestDto requestDto) {
        placeService.update(postId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId) {
        placeService.delete(postId);
        return ResponseEntity.noContent().build();
    }
}

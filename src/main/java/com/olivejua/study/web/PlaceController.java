package com.olivejua.study.web;

import com.olivejua.study.config.auth.LoginUser;
import com.olivejua.study.config.auth.dto.SessionUser;
import com.olivejua.study.service.PlaceService;
import com.olivejua.study.web.dto.PageDto;
import com.olivejua.study.web.dto.board.place.PostListResponseDto;
import com.olivejua.study.web.dto.board.place.PostReadResponseDto;
import com.olivejua.study.web.dto.board.place.PostSaveRequestDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@RequestMapping("/place")
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/list")
    public ResponseEntity<Page<PostListResponseDto>> list(@PageableDefault(sort = "createdDate", direction = DESC) Pageable pageable,
                                                          SearchDto searchInfo) {
        Page<PostListResponseDto> results = searchInfo == null ?
                                                placeService.list(pageable) :
                                                placeService.search(searchInfo, pageable);

        return ResponseEntity.ok(results);
    }

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
    public ResponseEntity<Void> post(@RequestBody PostSaveRequestDto requestDto, @LoginUser SessionUser user, @RequestParam(required = false) MultipartFile thumbnail) {
        Long savedPostId = thumbnail.isEmpty()
                ? placeService.post(requestDto, user.toEntity())
                : placeService.post(requestDto, user.toEntity(), thumbnail);
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

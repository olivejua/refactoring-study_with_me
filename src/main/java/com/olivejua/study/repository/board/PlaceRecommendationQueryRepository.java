package com.olivejua.study.repository.board;

import com.olivejua.study.domain.Comment;
import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.LikeHistory;
import com.olivejua.study.domain.board.Link;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.web.dto.board.place.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.olivejua.study.domain.QComment.comment;
import static com.olivejua.study.domain.QUser.user;
import static com.olivejua.study.domain.board.QLikeHistory.likeHistory;
import static com.olivejua.study.domain.board.QLink.link;
import static com.olivejua.study.domain.board.QPlaceRecommendation.placeRecommendation;

@RequiredArgsConstructor
@Repository
public class PlaceRecommendationQueryRepository {

    private final JPAQueryFactory queryFactory;


    public Optional<PlaceRecommendation> findEntity(Long postId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(placeRecommendation)
                .join(placeRecommendation.writer, user)
                .leftJoin(placeRecommendation.links, link)
                .leftJoin(placeRecommendation.likes, likeHistory)
                .leftJoin(placeRecommendation.comment, comment)
                .where(idEq(postId))
                .fetchOne());
    }

    public Optional<LikeHistory> getLikeStatusByPostAndUser(Long postId, Long userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(likeHistory)
                .join(likeHistory.user, user)
                .join(likeHistory.post, placeRecommendation)
                .where(
                        user.id.eq(userId),
                        placeRecommendation.id.eq(postId))
                .fetchOne());
    }

    public Page<PostListResponseDto> list(Pageable pageable) {
        List<PlaceRecommendation> entities = findPosts(pageable);

        List<PostListResponseDto> content = toListDtos(entities, findCommentCountsByPostId(toPostIds(entities)));

        JPAQuery<PlaceRecommendation> countQuery = queryFactory
                .selectFrom(placeRecommendation);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private List<PostListResponseDto> toListDtos(List<PlaceRecommendation> entities, Map<Long, Long> commentCounts) {
        return entities.stream()
                .map(entity -> new PostListResponseDto(entity, commentCounts.get(entity.getId())))
                .collect(Collectors.toList());
    }

    private List<PlaceRecommendation> findPosts(Pageable pageable) {
        return findPosts(pageable, null);
    }

    private List<Long> toPostIds(List<PlaceRecommendation> entities) {
        return entities.stream()
                .map(Board::getId)
                .collect(Collectors.toList());
    }

    private Map<Long, Long> findCommentCountsByPostId(List<Long> postIds) {
        List<Tuple> tuples = queryFactory
                .select(
                        comment.post.id,
                        comment.count())
                .from(comment)
                .where(comment.post.id.in(postIds))
                .groupBy(comment.post.id)
                .fetch();

        Map<Long, Long> result = new HashMap<>();
        for (Tuple tuple : tuples) {
            result.put(tuple.get(comment.post.id), tuple.get(comment.count()));
        }

        return result;
    }

    private List<PlaceRecommendation> findPosts(Pageable pageable, SearchDto cond) {
        return queryFactory
                .selectFrom(placeRecommendation)
                .join(placeRecommendation.writer, user).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression idEq(Long postId) {
        return placeRecommendation.id.eq(postId);
    }
}

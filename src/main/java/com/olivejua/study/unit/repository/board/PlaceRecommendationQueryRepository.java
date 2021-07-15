package com.olivejua.study.unit.repository.board;

import com.olivejua.study.domain.board.Board;
import com.olivejua.study.domain.board.LikeHistory;
import com.olivejua.study.domain.board.PlaceRecommendation;
import com.olivejua.study.web.dto.board.place.PostListResponseDto;
import com.olivejua.study.web.dto.board.search.SearchDto;
import com.olivejua.study.web.dto.board.search.SearchType;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

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
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
@Repository
public class PlaceRecommendationQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final String columnCommentCount = "commentCount";
    private final String columnLikeCount = "likeCount";
    private final String columnDislikeCount = "dislikeCount";

    /**
     * entity 전체목록 조회
     */
    public Page<PostListResponseDto> findEntities(Pageable pageable) {
        List<PlaceRecommendation> entities = selectEntities(pageable);

        List<Tuple> tuples = findAllJoinCountsByPostIds(toPostIds(entities));

        List<PostListResponseDto> content = toListDtos(
                entities,
                tupleToMap(tuples, columnLikeCount),
                tupleToMap(tuples, columnDislikeCount),
                tupleToMap(tuples, columnCommentCount)
        );

        JPAQuery<PlaceRecommendation> countQuery = queryFactory
                .selectFrom(placeRecommendation);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /**
     * entity 검색목록 조회
     */
    public Page<PostListResponseDto> findEntitiesWith(SearchDto cond, Pageable pageable) {
        List<PlaceRecommendation> entities = selectEntities(pageable, cond);

        List<Tuple> tuples = findAllJoinCountsByPostIds(toPostIds(entities));
        List<PostListResponseDto> content = toListDtos(
                entities,
                tupleToMap(tuples, columnCommentCount),
                tupleToMap(tuples, columnLikeCount),
                tupleToMap(tuples, columnDislikeCount)
        );

        JPAQuery<PlaceRecommendation> countQuery = queryFactory
                .selectFrom(placeRecommendation)
                .where(
                        titleContains(cond),
                        contentContains(cond),
                        addressContains(cond)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /**
     * entity 1개 조회
     */
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

    /**
     * 로그인 중인 User가 특정 Post의 '좋아요 entity' 조회
     */
    public Optional<LikeHistory> findLikeHistoryByPostAndUser(Long postId, Long userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(likeHistory)
                .join(likeHistory.user, user)
                .join(likeHistory.post, placeRecommendation)
                .where(
                        user.id.eq(userId),
                        placeRecommendation.id.eq(postId))
                .fetchOne());
    }

    /**
     * 목록 조회 쿼리
     */
    private List<PlaceRecommendation> selectEntities(Pageable pageable) {
        return selectEntities(pageable, null);
    }

    private List<PlaceRecommendation> selectEntities(Pageable pageable, SearchDto cond) {
        return queryFactory
                .selectFrom(placeRecommendation)
                .join(placeRecommendation.writer, user).fetchJoin()
                .where(
                        titleContains(cond),
                        contentContains(cond),
                        addressContains(cond)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 변환 후 리턴: List<Entity> -> List<ResponseDto>
     */
    private List<PostListResponseDto> toListDtos(List<PlaceRecommendation> entities, Map<Long, Long> likeCount,
                                                 Map<Long, Long> dislikeCount, Map<Long, Long> commentCount) {
        return entities.stream()
                .map(entity ->
                        new PostListResponseDto(
                                entity,
                                likeCount.get(entity.getId()),
                                dislikeCount.get(entity.getId()),
                                commentCount.get(entity.getId())))
                .collect(Collectors.toList());
    }

    /**
     * List<Entity> 에서 List<postId>만 추출
     */
    private List<Long> toPostIds(List<PlaceRecommendation> entities) {
        return entities.stream()
                .map(Board::getId)
                .collect(Collectors.toList());
    }

    /**
     * join columns (comment, like, dislike) counts를 하나의 쿼리에서 모두 조회
     */
    private List<Tuple> findAllJoinCountsByPostIds(List<Long> postIds) {
        return queryFactory
                .select(
                        placeRecommendation.id,
                        ExpressionUtils.as(select(comment.count())
                                .from(comment)
                                .where(comment.post.id.eq(placeRecommendation.id)),columnCommentCount),
                        ExpressionUtils.as(select(likeHistory.count())
                                .from(likeHistory)
                                .where(
                                        likeHistory.post.id.eq(placeRecommendation.id),
                                        likeHistory.isLike.isTrue()), columnLikeCount),
                        ExpressionUtils.as(select(likeHistory.count())
                                .from(likeHistory)
                                .where(
                                        likeHistory.post.id.eq(placeRecommendation.id),
                                        likeHistory.isLike.isFalse()), columnDislikeCount)
                ).from(placeRecommendation)
                .where(placeRecommendation.id.in(postIds))
                .fetch();
    }

    /**
     * counts of join columns을 map<join column 명, count 조회값> 으로 조합하여 리턴
     */
    private Map<Long, Long> tupleToMap(List<Tuple> tuples, String extractingColumn) {
        Map<Long, Long> result = new HashMap<>();
        for (Tuple tuple : tuples) {
            result.put(
                    tuple.get(placeRecommendation.id),
                    tuple.get(Expressions.numberPath(
                            Long.class, extractingColumn)));
        }
        return result;
    }

    /**
     * Column 조건
     */
    private BooleanExpression idEq(Long postId) {
        return placeRecommendation.id.eq(postId);
    }

    private BooleanExpression titleContains(SearchDto cond) {
        if(cond != null
                && cond.getSearchType() == SearchType.TITLE
                && !StringUtils.isEmpty(cond.getKeyword()))
            return placeRecommendation.title.contains(cond.getKeyword());

        return null;
    }

    private BooleanExpression contentContains(SearchDto cond) {
        if(cond != null
                && cond.getSearchType() == SearchType.CONTENT
                && !StringUtils.isEmpty(cond.getKeyword()))
            return placeRecommendation.content.contains(cond.getKeyword());

        return null;
    }

    private BooleanExpression addressContains(SearchDto cond) {
        if(cond != null
                && cond.getSearchType() == SearchType.ADDRESS
                && !StringUtils.isEmpty(cond.getKeyword()))
            return placeRecommendation.address.contains(cond.getKeyword())
                    .or(placeRecommendation.addressDetail.contains(cond.getKeyword()));

        return null;
    }
}

var read_main = {
    init           : function () {
        var _this = this;

        _this.loadMapApi();
        _this.loadLikeStatus();

        $('.btn-like').on('click', function (e) {
            _this.clickLike($(e.target).attr('name'));
        });
    },
    loadMapApi : function () {
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };

        // 지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();

        // 주소로 좌표를 검색합니다
        geocoder.addressSearch($('#address').text(), function(result, status) {

            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {

                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                // 결과값으로 받은 위치를 마커로 표시합니다
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });

                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
            }
        });
    },
    loadLikeStatus : function () {
        const _this = this;

        const existLike = $('#existLike').val();
        const isLike = $('#isLike').val();

        if(parseInt(existLike)) {
            if(parseInt(isLike)) {
                _this.setLike_like();
            } else {
                _this.setLike_dislike();
            }
        } else {
            _this.setLike_none();
        }
    },
    clickLike : function (clickButton) {
        var _this = this;

        switch (clickButton) {
            case 'btn-like':
                console.log('좋아요');

                _this.submit_ajax_put(true);
                _this.setLike_like();
                break;
            case 'btn-dislike':
                console.log('별로예요');

                _this.submit_ajax_put(false);
                _this.setLike_dislike();
                break;
            case 'btn-like-chosen':
                console.log('좋아요 취소');
            case 'btn-dislike-chosen':
                console.log('별로예요 취소');
            default:
                _this.submit_ajax_delete();
                _this.setLike_none();
                break;
        }
    },
    setLike_none : function () {
        $('button[name=btn-like]').css('display', 'inline-block');
        $('button[name=btn-like-chosen]').css('display', 'none');
        $('button[name=btn-dislike]').css('display', 'inline-block');
        $('button[name=btn-dislike-chosen]').css('display', 'none');
    },
    setLike_like : function () {
        $('button[name=btn-like]').css('display', 'none');
        $('button[name=btn-like-chosen]').css('display', 'inline-block');
        $('button[name=btn-dislike]').css('display', 'inline-block');
        $('button[name=btn-dislike-chosen]').css('display', 'none');
    },
    setLike_dislike : function () {
        $('button[name=btn-like]').css('display', 'inline-block');
        $('button[name=btn-like-chosen]').css('display', 'none');
        $('button[name=btn-dislike]').css('display', 'none');
        $('button[name=btn-dislike-chosen]').css('display', 'inline-block');
    },
    submit_ajax_put : function (isLike) {
        var data = {
            userCode : $('#userCode').val(),
            postNo : $('#postNo').val(),
            isLike : isLike,
        };

        $.ajax({
            type: 'PUT',
            url: '/place/posts/like',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (response) {
            console.log('제출 완료 - ' + response);
            $('#likeCount').text(response.likeCount);
            $('#dislikeCount').text(response.dislikeCount);

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    submit_ajax_delete : function () {
        var userCode = $('#userCode').val();
        var postNo = $('#postNo').val();

        $.ajax({
            type: 'DELETE',
            url: '/place/posts/like/'+ postNo + '/' + userCode,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log('좋아요 혹은 별로예요 취소 - ' + response);
            $('#likeCount').text(response.likeCount);
            $('#dislikeCount').text(response.dislikeCount);
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

read_main.init();
var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            setConditionExplanation();
            _this.save();
        });
        $('#btn-update').on('click', function () {
            setConditionExplanation();
            _this.update()
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        $('#btn-search').on('click', function (e) {
            _this.search();
        });
    },
    save : function () {
        const _this = this;
        const data = {
            title : $('#title').val(),
            content : $('#content').val(),
            userCode : $('#userCode').val()
        };

        if(!(_this.validation_data(data))) {
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/question/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href='/question/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        const _this = this;

        const data = {
            title : $('#title').val(),
            content : $('#content').val()
        };

        if(!(_this.validation_data(data))) {
            return;
        }

        const postNo = $('#postNo').val();

        $.ajax({
            type: 'PUT',
            url: '/question/posts/'+ postNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href=`/question/posts?postNo=${postNo}&page=0`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        $.ajax({
            type: 'DELETE',
            url: '/question/posts/'+ $('#postNo').val(),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href='/question/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    search : function () {
        const searchType = $('#search-category option:selected').val();
        const keyword = $('#search-keyword').val();

        if(searchType==="none") {
            window.location.href='/question/list';
        } else {
            window.location.href=`/question/list?searchType=${searchType}&keyword=${keyword}`;
        }
    },
    validation_data : function (data) {
        const isFilledTitle = data.title !== "";

        if(!isFilledTitle) {
            $('#modal-validation').find('h5').text('제목을 입력해주세요.');
            $('#modal-validation').modal();

            return false;
        }

        return true;
    }
};

main.init();
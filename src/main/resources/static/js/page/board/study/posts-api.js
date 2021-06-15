var main = {
    init : function () {
        const _this = this;
        $('#btn-save').on('click', function () {
            setConditionExplanation();
            _this.save();
        });
        $('#btn-update').on('click', function () {
            setConditionExplanation();
            _this.update();
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

        let languageList = [];
        $('.language-lumps').each(function () {
            let language = $(this).text().replace('X', '');
            languageList.push(language);
        });

        let data = {
            title: $('#title').val(),
            conditionLanguages: languageList,
            conditionPlace: $('#conditionPlace').val(),
            conditionStartDate: $('#startDate').val(),
            conditionEndDate: $('#endDate').val(),
            conditionCapacity: $('#checkbox-nolimit').is(':checked') ? 0 : $('#conditionCapacity').val(),
            conditionExplanation: $('#conditionExplanation').val()
        };

        if(!(_this.validation_saveData(data))) {
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/study/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href='/study/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function() {
        const _this = this;

        let languageList = [];
        $('.language-lumps').each(function () {
            let language = $(this).text().replace('X', '');
            languageList.push(language);
        });

        const data = {
            userCode: $('#userCode').val(),
            title: $('#title').val(),
            conditionLanguages: languageList,
            conditionPlace: $('#conditionPlace').val(),
            conditionStartDate: $('#startDate').val(),
            conditionEndDate: $('#endDate').val(),
            conditionCapacity: $('#checkbox-nolimit').is(':checked') ? 0 : $('#conditionCapacity').val(),
            conditionExplanation: $('#conditionExplanation').val()
        };

        if(!(_this.validation_saveData(data))) {
            return;
        }

        const postNo = $('#postNo').val();

        $.ajax({
            type: 'PUT',
            url: '/study/posts/'+ postNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href=`/study/posts?postNo=${postNo}&page=0`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function() {
        const postNo = $('#postNo').val();

        $.ajax({
            type: 'DELETE',
            url: '/study/posts/'+ postNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href='/study/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    search : function () {
        const searchType = $('#search-category option:selected').val();
        const keyword = $('#search-keyword').val();

        if(searchType==="none") {
            window.location.href='/study/list';
        } else {
            window.location.href=`/study/list?searchType=${searchType}&keyword=${keyword}`;
        }
    },
    validation_saveData : function (data) {
        const isFilledTitle = data.title !== "";
        const isFilledLanguages = data.conditionLanguages.length > 0;
        const isFilledPlace = data.conditionPlace !== "";
        const isFilledCapacity = data.conditionCapacity !== "";
        const isFilledExplanation = data.conditionExplanation !== "";

        if(!isFilledTitle || !isFilledLanguages || !isFilledPlace || !isFilledCapacity || !isFilledExplanation) {
            $('#modal-validation').find('h5').text('입력란을 모두 채워주세요');
            $('#modal-validation').modal();

            return false;
        } else if(data.conditionStartDate > data.conditionEndDate) {
            $('#modal-validation').find('h5').text('시작일을 다시 입력해주세요.');
            $('#modal-validation').modal();

            return false;
        }

        return true;
    }
};

main.init();
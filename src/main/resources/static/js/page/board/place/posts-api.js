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

        $('#btn-search').on('click', function () {
            _this.search();
        });
    },
    save : function () {
        const _this = this;

        let links = [];
        $('label[name=link]').each(function() {
            links.push($(this).text());
        });

        const save_data = {
            userCode : $('#userCode').val(),
            title : $('#title').val(),
            address : $('#address').val(),
            addressDetail : $('#address-detail').val(),
            links : links,
            content : $('#content').val(),
            thumbnailPath : $('#thumbnail').val(),
            thumbnailFile : $('#thumbnail')[0].files[0]
        };

        if(!_this.validation_data(save_data, "save")) {
            return;
        }

        const formData = new FormData();
        for(const key in save_data) {
            formData.append(`${key}`, save_data[key]);
        }

        $.ajax({
            type: 'POST',
            url: '/place/posts',
            dataType: 'json',
            processData: false,
            contentType: false,
            data: formData
        }).done(function (data) {
            alert('글이 등록되었습니다.');
            window.location.href='/place/list';
        }).fail(function (error) {
            alert("save error : ");
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        const _this = this;

        const links = [];
        $('label[name=link]').each(function() {
            links.push($(this).text());
        });

        let update_data = {
            title : $('#title').val(),
            address : $('#address').val(),
            addressDetail : $('#address-detail').val(),
            links : links,
            content : $('#content').val(),
            oldThumbnailPath : $('#oldThumbnailPath').val(),
        };

        if($('#thumbnail')[0].files[0] !== undefined) {
            update_data = {
                ...update_data,
                thumbnailPath : $('#thumbnail').val(),
                thumbnailFile : $('#thumbnail')[0].files[0]
            };
        }

        if(!_this.validation_data(update_data, "update")) {
            return;
        }

        const formData = new FormData();
        for(const key in update_data) {
            formData.append(`${key}`, update_data[key]);
        }

        $.ajax({
            type: 'PUT',
            url: '/place/posts/'+$('#postNo').val(),
            dataType: 'json',
            processData: false,
            contentType: false,
            data: formData
        }).done(function (postNo) {
            alert('글이 수정되었습니다.');
            window.location.href=`/place/posts?postNo=${postNo}&page=0`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var postNo = $('#postNo').val();

        $.ajax({
            type: 'DELETE',
            url: '/place/posts/'+ postNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href='/place/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    search : function () {
        const searchType = $('#search-category option:selected').val();
        const keyword = $('#search-keyword').val();

        if(searchType === "none") {
            window.location.href='/place/list';
        } else {
            window.location.href=`/place/list?searchType=${searchType}&keyword=${keyword}`;
        }
    },
    validation_data : function (data, validKind) {
        const isEmptyTitle = data.title === "";
        const isEmptyAddress = data.address === "";
        const isEmptyAddressDetail = data.addressDetail === "";
        const isEmptyLinks = data.links.length === 0;

        let isEmptyData = isEmptyTitle || isEmptyAddress || isEmptyAddressDetail || isEmptyLinks;

        if(validKind === "save") {
            const isEmptyThumbnailPath = data.thumbnailPath === "";
            const isEmptyThumbnailFile = data.thumbnailFile === undefined;

            isEmptyData = isEmptyData || isEmptyThumbnailPath || isEmptyThumbnailFile;
        }

        if(isEmptyData) {
            $('#modal-validation').find('h5').text('입력란을 모두 채워주세요');
            $('#modal-validation').modal();

            return false;
        }

        return true;
    }
};

main.init();
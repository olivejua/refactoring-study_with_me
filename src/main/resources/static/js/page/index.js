const main = {
    init              : function () {
        const _this = this;

        _this.load_study_posts();
        _this.load_place_posts();
        _this.load_question_posts();

        $('#isOpenLogin').val() === "login" && _this.open_loginModal();
    },
    load_study_posts   : function () {
        const _this = this;

        $.ajax({
            type: 'GET',
            url: `/study/list/index`,
            contentType: 'application/json; charset=utf-8',
        }).done(function (response) {
            const postList = JSON.parse(response);
            const elements = _this.get_elements('study', postList);
            $('#list-study-content').append(elements);

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    load_place_posts   : function () {
        const _this = this;

        $.ajax({
            type: 'GET',
            url: `/place/list/index`,
            contentType: 'application/json; charset=utf-8',
        }).done(function (response) {
            const postList = JSON.parse(response);
            $('#list-place').append(_this.get_cards(postList));
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    load_question_posts: function () {
        const _this = this;

        $.ajax({
            type: 'GET',
            url: `/question/list/index`,
            contentType: 'application/json; charset=utf-8',
        }).done(function (response) {
            const postList = JSON.parse(response);
            const elements = _this.get_elements('question', postList);
            $('#list-question-content').append(elements);

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    get_elements      : function (boardName, list) {
        let elements = '';

        for(let i=0; i<list.length; i++) {
            let post = list[i];

            let link = `/${boardName}/posts?postNo=${post.postNo}&page=0`;
            elements +=
                `<tr class="table-light">
                    <td><a href="${link}">${post.postNo}</a></th>
                    <td><a href="${link}">${post.title}</a></td>
                    <td><a href="${link}">${post.user.nickname}</a></td>
                    <td><a href="${link}">${post.createdDate}</a></td>
                </tr>`;
        }

        return elements;
    },
    get_cards : function (list) {
        let cards = '';

        for(let i=0; i<list.length; i++) {
            let post = list[i];

            let date = post.createdDate.date;
            cards +=
                `<div class="card mb-3">
                    <img src="${post.thumbnailPath}" alt="Card image">
                    <div class="card-body">
                        <h5 class="card-text"><a href="/place/posts?postNo=${post.postNo}&page=0"> ${post.title}</a></h5>
                    </div>
                    <div class="card-footer text-muted">${date.year}.${date.month}.${date.day}</div>
                </div>`;
        }

        return cards;
    },
    open_loginModal : function () {
        $('#loginModal').modal();
    }
};

main.init();
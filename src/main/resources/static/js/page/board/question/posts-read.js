var read_main = {
    init           : function () {
        var _this = this;

        _this.loadComments();

        $(document).on('click', 'button[name=open-comment-modify]', function (e) {
            _this.openCommentModify(e.target);
        });

        $(document).on('click', 'button[name=open-reply-save]', function (e) {
            _this.openReplySave(e.target);
        });

        //댓글 CUD
        $(document).on('click', 'button[name=btn-comment-save]', function () {
            _this.commentSave();
        });

        $(document).on('click', 'button[name=btn-comment-modify]', function (e) {
            _this.commentModify(e.target);
        });

        $(document).on('click', 'button[name=btn-comment-delete]', function (e) {
            _this.commentDelete(e.target);
        });


        //답글 CUD
        $(document).on('click', 'button[name=btn-reply-save]', function (e) {
            _this.replySave(e.target);
        });

        $(document).on('click', 'button[name=btn-reply-modify]', function (e) {
            _this.replyModify(e.target);
        });

        $(document).on('click', 'button[name=btn-reply-delete]', function (e) {
            _this.replyDelete(e.target);
        });

        $('.div-reply-save').hide();
    },
    loadComments : function () {
        var _this = this;
        var boardName = $('#boardName').val();
        var postNo = $('#postNo').val();

        $.ajax({
            type: 'GET',
            url: `/posts/comment/list?boardName=${boardName}&postNo=${postNo}`,
            contentType: 'application/json; charset=utf-8',
        }).done(function (response) {
            var commentList = JSON.parse(response);

            $('#paste-comments').empty();
            _this.addElements_allCommentsAndReplies(commentList);

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    addElements_allCommentsAndReplies : function (commentList) {
        const login_userCode = Number($('#userCode').val());

        for(let i=0; i<commentList.length; i++) {
            let {commentNo, content, modifiedDate, user, replies} = commentList[i];
            let div_comment = $('#add-comment').children('div[name=wrapped-each-comment]').clone();

            div_comment.find('input[name=commentNo]').val(commentNo);
            div_comment.find('label[name=comment-user-nickname]').text(user.nickname);
            div_comment.find('label[name=comment-modifiedDate]').text(modifiedDate);

            if(login_userCode === user.userCode) {
                div_comment.find('.comment-modify-buttons').css('display', 'inline');
            } else {
                div_comment.find('.comment-modify-buttons').css('display', 'none');
            }

            div_comment.find('label[name=comment-content]').text(content);
            div_comment.find('textarea[name=comment-content-modify]').text(content);
            div_comment.find('.div-reply-save').attr('id', commentNo);

            for(let j=0; j<replies.length; j++) {
                let div_reply = $('#add-comment').children('div[name=wrapped-each-reply]').clone();
                let reply = replies[j];

                div_reply.find('input[name=replyNo]').val(reply.replyNo);
                div_reply.find('label[name=reply-user-nickname]').text(reply.user.nickname);
                div_reply.find('label[name=reply-modifiedDate]').text(reply.modifiedDate);

                if(login_userCode === reply.user.userCode) {
                    div_reply.find('.reply-modify-buttons').css('display', 'inline');
                } else {
                    div_reply.find('.reply-modify-buttons').css('display', 'none');
                }

                div_reply.find('label[name=reply-content]').text(reply.content);
                div_reply.find('textarea[name=reply-modify-content]').text(reply.content);

                div_comment.append(div_reply);
            }

            let div_reply_save = $('#add-comment').children('div[name=div-reply-save]').clone();
            div_reply_save.find('label[name=comment-user-nickname]').text($('#login-nickname').text());
            div_comment.append(div_reply_save);

            $('#paste-comments').append(div_comment);
        }
    },
    commentSave           : function () {
        var _this = this;

        var data = {
            postNo: $('#postNo').val(),
            boardName: $('#boardName').val(),
            userCode: $('#userCode').val(),
            content: $('#comment-content').val()
        };

        console.log(data);

        $.ajax({
            type: 'POST',
            url: '/posts/comment',
            // dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (response) {
            var add_comment = JSON.parse(response);
            console.log(add_comment);
            $('#comment-content').val("");

            _this.loadComments();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    commentModify : function (target) {
        var _this = this;
        var div = $(target).closest('div[name=div-one-comment]')[0];

        var data = {
            commentNo: $(div).children('input[name=commentNo]')[0].value,
            content: $(target).prev().val()
        };

        $.ajax({
            type: 'PUT',
            url: '/posts/comment/'+data.commentNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            _this.loadComments();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    commentDelete : function (target) {
        var _this = this;
        var div = $(target).closest('div[name=div-one-comment]')[0];
        var commentNo = $(div).children('input[name=commentNo]')[0].value;

        $.ajax({
            type: 'DELETE',
            url: '/posts/comment/'+commentNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            _this.loadComments();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    replySave : function (target) {
        var _this = this;
        var div = $(target).closest('div[name=wrapped-each-comment]');

        var data = {
            commentNo : $(div).find('input[name=commentNo]')[0].value,
            content : $(div).find('textarea[name=reply-content]')[0].value,
            postNo : $('#postNo').val(),
            boardName : $('#boardName').val()
        };

        $.ajax({
            type: 'POST',
            url: '/posts/reply',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            _this.loadComments();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    replyModify : function (target) {
        var _this = this;
        var div = $(target).closest('div[name=wrapped-each-reply]');

        var data = {
            replyNo : $(div).find('input[name=replyNo]')[0].value,
            content : $(div).find('textarea[name=reply-modify-content]')[0].value
        };

        $.ajax({
            type: 'PUT',
            url: '/posts/reply/' + data.replyNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            _this.loadComments();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    replyDelete : function (target) {
        var _this = this;
        var div = $(target).closest('div[name=wrapped-each-reply]');
        var replyNo = $(div).find('input[name=replyNo]')[0].value;

        $.ajax({
            type: 'DELETE',
            url: '/posts/reply/'+ replyNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            _this.loadComments();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    openCommentModify: function (target) {
        var wrappedDiv = $(target).closest('div');

        var p = $(wrappedDiv).children('p[name=p-comment-modify]')[0];
        var div = $(wrappedDiv).children('div[name=div-comment-modify]')[0];

        if($(div).css('display') === 'none') {
            $(div).css('display', 'flex');
            $(p).css('display', 'none');
        } else {
            $(p).css('display', 'flex');
            $(div).css('display', 'none');
        }
    },
    openReplySave: function (target) {
        var div = $(target).closest('div').nextAll('.div-reply-save')[0];

        if($(div).css("display") === "none") {
            $(div).show()
        } else {
            $(div).hide();
        }
    }
};

read_main.init();
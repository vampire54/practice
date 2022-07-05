const main = {

    init: function () {
        const _this = this;
        //작성
        $('#btn-save').on('click', function () {
            _this.save();
        });
        //수정
        $('#btn-update').on('click', function () {
            _this.update();
        });
        //삭제
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        // 댓글 저장
        $('#btn-comment-save').on('click', function () {
            _this.commentSave();
        });

        //삭제
        $('#btn-comment-delete').on('click', function () {
            _this.commentDelete();
        });
    },

    //작성
    save: function () {
        const data = {
            title: $('#title').val(),
            writer: $('#writer').val(),
            content: $('#content').val()
        };
        if (!data.title || data.title.trim() === "") {
            alert("제목을 입력하세요");

            return false;
        }
        if (!data.content || data.content.trim() === "") {
            alert("내용을 입력하세요")

            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/posts/save',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('등록되었습니다.');
                window.location.href = '/posts/list';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    //수정
    update: function () {
        const data = {
            id: $('#id').val(),
            title: $('#title').val(),
            content: $('#content').val()
        };

        if (!data.title || data.title.trim() === "") {
            alert("제목을 입력하세요");

            return false;
        }
        if (!data.content || data.content.trim() === "") {
            alert("내용을 입력하세요")

            return false;
        } else {
            const con_check = confirm("수정하시겠습니까?");

            if (con_check == true) {

                $.ajax({
                    type: 'PUT',
                    url: '/posts/save/' + data.id,
                    dataType: 'JSON',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    alert('수정되었습니다.');
                    window.location.href = '/posts/list';
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }
    },
    //삭제
    delete : function () {
        const id = $('#id').val();
        const con_check = confirm("삭제하시겠습니까?");

        if (con_check == true) {

            $.ajax({
                type: 'DELETE',
                url: '/posts/delete/' + id,
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8'

            }).done(function () {
                alert("삭제되었습니다.");
                window.location.href = '/posts/list';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    // 댓글 저장
    commentSave : function () {
        const data = {
            postsId: $('#postsId').val(),
            comment: $('#comment').val()
        }

        // 공백 및 빈 문자열 체크
        if (!data.comment || data.comment.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/posts/' + data.postsId + '/comments',
                dataType: 'TEXT',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('댓글이 등록되었습니다.');
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },
    // 댓글 삭제
    commentDelete : function (postsId, commentId) {
        const con_check = confirm("삭제하시겠습니까?");

        if (con_check == true) {

            $.ajax({
                type: 'DELETE',
                url: '/posts/' + postsId + '/comments/' + commentId,
                dataType: 'text',
            }).done(function () {
                alert('댓글이 삭제되었습니다.');
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }
}
main.init();


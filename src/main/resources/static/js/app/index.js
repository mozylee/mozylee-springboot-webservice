const main = {
    init: function() {
        const _this = this;
        $('#btn-save').on('click', function() {
            _this.save();
        })
    },

    save: () => {
        console.log("clicked!")
        const data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(() => {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail((error) => {
            alert(JSON.stringify(error))
        });
    }
}

main.init();
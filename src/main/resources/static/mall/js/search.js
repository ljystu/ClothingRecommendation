$(function () {
    $('#keyword').keypress(function (e) {
        var key = e.which; //e.which是按键的值
        if (key == 13) {
            var q = $(this).val();
            if (q && q != '') {
                window.location.href = '/search?keyword=' + q;
            }
        }
    });
});

function search(p) {
    var q = $('#keyword').val();
    if (q && q != '') {
        if (p && p != '')
            window.location.href = '/search?keyword=' + q + '&RSuser=' + p;
        else
            window.location.href = '/search?keyword=' + q;
    }
}
function searchBottom(p) {
    var q = $('#keywordmy').val();
    if (q && q != '') {
        if (p && p != '')
            window.location.href = '/search?keyword=' + q + '&RSuser=' + p;
        else
            window.location.href = '/search?keyword=' + q;
    }
}

function searchNull(p) {
    var q = $('#keywords').val();
    if (q && q != '') {
        window.location.href = '/search?keyword=' + q + '&RSuser=' + p;
    }
}

function more(q,p) {
    // var p = $('#wode').val();
    if (q && q != '') {
        if (p && p != '')
            window.location.href = '/searchall?keyword=' + q + '&RSuser=' + p;
        else
            window.location.href = '/searchall?keyword=' + q;
    }
}

function rtnindex() {
    window.location.href = '/index'
}

$(document).ready(function () {
    $("textarea").each(function () {
        $(this).html($(this).attr('value'));
    });
});
var isDirty = false;

$(document).ready(function() {

    $(window).bind('beforeunload', function(){
        if(isDirty)
            return 'The Page contains unsaved modifications. Are you sure you want to continue?';
    });

    $("form input,textarea,select").each(function() {
        $(this).change(function() {
            isDirty = true;
        });
    });

    $("form").submit(function() {
        isDirty = false;
    });
});


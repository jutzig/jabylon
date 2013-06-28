var isDirty = false;

$(document).ready(function() {

    $(window).bind('beforeunload', function(){
        if(isDirty)
            return 'The Page contains unsaved modifications. Are you sure you want to continue?';
    });

    $("form input,textarea,select").each(function() {
        $(this).change(function() {
            isDirty = true;
            // if there is a modify-indicator checkbox in the modified form, check it
            var form = $(this).parents("form");
            var indicator = form.children(".modify-indicator");
            indicator.attr("checked","checked");
        });
    });

    $("form").submit(function() {
        isDirty = false;
    });
});


//to remember what to do when tab-key is used
var currentFocus;


$(document).ready(function() {

    //set initial focus to translation
    if(currentFocus==null) {
        currentFocus = $("#translation");
        currentFocus.focus();
    };

    //show JS tooltip on badges
    $('.label').tooltip();

    $(":input").focus(function () {
        currentFocus = this;
    });

    // initialize keyboard shortcuts
    shortcut.add("Ctrl+Left", function() {
        //shift the focus to make sure we adjust the modified attribute
        if(currentFocus!=null)
            currentFocus.blur();
        $("#previous").click();
    });

    shortcut.add("Ctrl+Right", function() {
        //shift the focus to make sure we adjust the modified attribute
        if(currentFocus!=null)
            currentFocus.blur();
        $("#next").click();
    });

    shortcut.add("Ctrl+1", function() {
        $('#tooltabs li:eq(0) a').click();
    });

    shortcut.add("Ctrl+2", function() {
        $('#tooltabs li:eq(1) a').click();
    });

    shortcut.add("Ctrl+3", function() {
        $('#tooltabs li:eq(2) a').click();
    });

    shortcut.add("Ctrl+4", function() {
        $('#tooltabs li:eq(3) a').click();
    });

    shortcut.add("Ctrl+5", function() {
        $('#tooltabs li:eq(4) a').click();
    });

    shortcut.add("Ctrl+6", function() {
        $('#tooltabs li:eq(5) a').click();
    });

    shortcut.add("Ctrl+7", function() {
        $('#tooltabs li:eq(6) a').click();
    });

    shortcut.add("Ctrl+8", function() {
        $('#tooltabs li:eq(7) a').click();
    });

    shortcut.add("Ctrl+9", function() {
        $('#tooltabs li:eq(8) a').click();
    });

    shortcut.add("tab", function() {
        traverseFocus();
    });
    
    //allows suggestions to be applied directly from the badge
    $(".label[data-suggestion]").click(function(){
    	var suggestion = $(this).data('suggestion');
    	var widget = $("#translation");
    	widget.val(suggestion);
    	markDirty();
    });
});

function traverseFocus() {
    if(currentFocus==null) {
        //no focus => use the first
        currentFocus = $("#translation");
    }
    else if(currentFocus.id=="translation") {
        currentFocus = $("#translation-comment");
    }
    else if(currentFocus.id=="translation-comment") {
        currentFocus = $("#next");
    }
    else if(currentFocus.id=="next") {
        currentFocus = $("#previous");
    }
    else if(currentFocus.id=="previous") {
        currentFocus = $("#translation");
    }
    else {
        currentFocus = $("#translation");
    }
    currentFocus.focus();
}

/**
 * mark the editor as dirty so it will safe
 */
function markDirty() {
	var indicator = $("form").children(".modify-indicator");
	indicator.attr("checked","checked");
	isDirty = true;
 }

jQuery.extend( jQuery.fn.dataTableExt.oSort, {
    "percent-pre": function ( a ) {
        //customization to replace the html tags before sorting
        var x = a.replace( /<.*?>/g, "" );
        x = (x == "-") ? 0 : x.replace( /%/, "" );
        return parseFloat( x );
    },

    "percent-asc": function ( a, b ) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "percent-desc": function ( a, b ) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
} );

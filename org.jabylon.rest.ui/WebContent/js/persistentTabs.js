/*
 * Persistent Tabs for Bootstrap
 *
 * 1) Create tabs as usual, with href="..." attribute
 * 2) Add a "data-tabsheet" attribute to the enclosing <ul>
 * 3) Upon changing the tab, its "href" value will be persisted
 * 4) Upon reloading the page, the previously active tab will be activated again
 *
 * You may have multiple tab sheets on one page (use different "data-tabsheet"
 * values), and each of them will have its own active tab persisted. Tabs which
 * have an enclosing <ul> with no "data-tabsheet" attribute will not be persisted.
 *
 * Tabs are persisted in the Browser's sessionStorage.
 */
$('body').ready(function(e) {
  if (window.sessionStorage) {
        $('[data-tabsheet]').each(function(idx, el) {
            var tabsheet = $(el).data('tabsheet');
            var selector = window.sessionStorage.getItem('tabsheet-' + tabsheet);
            if (selector) {
                //console.log('Active tab in "' + tabsheet + '": ' + selector);
                $('[data-toggle="tab"]').filter(selector).tab('show');
            };
        });
    };

    $('body').on('shown', '[data-toggle="tab"]', function(e) {
        if (window.sessionStorage) {
            var $tab = $(e.target);
            var tabsheet = $tab.closest('[data-tabsheet]').data('tabsheet');
            if (tabsheet) {
                var selector = '[href="' + $tab.attr('href') + '"]';
                selector && window.sessionStorage.setItem('tabsheet-' + tabsheet, selector);
                //console.log('Active tab in "' + tabsheet + '": ' + selector);
            };
        };
    });
});

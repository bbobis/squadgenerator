const HomePage = {
    initialize: function () {
        //Register click event to expand all collapsible squad panels
        $('#expandAll').click(function () {
            $('.collapse').collapse('show');
        });

        //Register click event to hide all collapsible squad panels
        $('#collapseAll').click(function () {
            $('.collapse').collapse('hide');
        });

        //Register click event to each squad panel header, toggling the collapsible panel body for each header
        $('.card-header').each(function () {
            let $cardHeader = $(this);
            let collapsibleCardId = $cardHeader.data("collapsible-card-id");
            $cardHeader.css('cursor', 'pointer');
            $cardHeader.click(function () {
                $('#' + collapsibleCardId).collapse('toggle');
            });
        });

        //Set the height of all elements with the .scroll-y class to the window height minus the fixed header total height
        $('.scroll-y').height($(window).height() - 70);
    }
};
const HomePage = {
    initialize: function () {
        //Register click event to expand all collapsible squad panels
        $('#expandAll').click(function () {
            $('.collapse.squad-panel').collapse('show');
        });

        //Register click event to hide all collapsible squad panels
        $('#collapseAll').click(function () {
            $('.collapse.squad-panel').collapse('hide');
        });

        //Register click event to each squad panel header, toggling the collapsible panel body for each header
        $('.card-header.squad-panel').each(function () {
            let $squadPanelHeader = $(this);
            let squadPanelBody = $squadPanelHeader.data("squad-panel-body-id");
            $squadPanelHeader.css('cursor', 'pointer');
            $squadPanelHeader.click(function () {
                $('#' + squadPanelBody).collapse('toggle');
            });
        });

        //Disable generate squad button on submit.
        $("#createSquadForm").submit(function () {
            $("#generateSquad").attr('disabled','disabled');
        });

        //Set the height of all elements with the .scroll-y class to the window height minus the fixed header total height
        $('.scroll-y').height($(window).height() - 70);
    }
};
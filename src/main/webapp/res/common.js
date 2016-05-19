﻿$(function() {
    $("tr[data-url]").click(function() {
        location.href = $(this).attr("data-url");
    });

    $("div.pagination a").click(function() {
        $("input[name=pg]").val($(this).attr("data-page"));
        $("form").submit();
    });

    $("[data-auto-submit=true]").change(function() {
        $(this).parents("form").submit();
    });
    $("[data-confirm]").click(function() {
        return confirm($(this).attr("data-confirm"));
    });
    $('#myModal').on('shown.bs.modal', function () {
    	  $('#myInput').focus()
    	})
});

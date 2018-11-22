$(document).ready(function() {
$(function() {
$("#dialog").dialog({
autoOpen: false
});
$("#button").on("click", function() {
$("#dialog").dialog("open");
});
});

});
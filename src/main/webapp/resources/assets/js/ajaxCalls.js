$(document).ready(function () {
	/* ajax call to get datasouces */
    	 $.ajax({
             type: "POST",
             url: "influx-api/get-datasources",
             success: loadDataSource
         });
    	
    });



function getMeasurement() {
	alert("xshgdttg");
    var index = document
            .getElementById("dSource").selectedIndex;
    var value = document
            .getElementById("dSource").options;
    var text = value[index].text;

    var dataSource = {};
    dataSource["datasourceName"] = text;
    /*ajax call to get Measurements*/
    $.ajax({
                type: "POST",
                url: "influx-api/get-measurements",
                data: dataSource,
                success: function (response) {
                    var res = "<option value=\"1\">--Select Measurement--</option>";
                    var counter = 1;
                    for (item in response) {
                        counter = counter + 1;
                        res = res
                                + "<option value=\"" + counter + "\">"
                                + response[item]
                                + "</option>";

                    }
                    console.log(res);
                    $('#measure').html(res);
                }
            });
    /*ajax call to get  retention policy*/
    $.ajax({
        type: "POST",
        url: "influx-api/get-retentionPolicies",
        data: dataSource,
        success: function (response) {
            var res = "";
            var counter = 0;
            for (item in response) {
                counter = counter + 1;
                res = res
                        + "<option value=\"" + counter + "\">"
                        + response[item]
                        + "</option>";

            }
            console.log(res);
            $('#rp').html(res);
        }
    });

}
function getRestFields() {
	var index = document
    .getElementById("dSource").selectedIndex;
var value = document
    .getElementById("dSource").options;
var datasource = value[index].text;

var index1 = document
    .getElementById("measure").selectedIndex;
var value1 = document
    .getElementById("measure").options;
var measurement = value1[index1].text;

var row = {};
row["datasourceName"] = datasource;
row["measurement"] = measurement;
$.ajax({
        type: "POST",
        url: "influx-api/get-fields",
        data: row,
        success: function (response) {
            var res = "<option value=\"1\">--Select Field--</option>";
            var counter = 1;
            for (item in response) {
                counter = counter + 1;
                res = res
                        + "<option value=\"" + counter + "\">"
                        + response[item]
                        + "</option>";

            }
            console.log(res);
            $('#field').html(res);
        }
    });

$.ajax({
        type: "POST",
        url: "influx-api/get-tags",
        data: row,
        success: function (response) {
            var res = "";
            var where = "";
            var counter = 0;
            for (item in response) {
                counter = counter + 1;
                res = res
                        + "<option value=\"" + counter + "\">"
                        + response[item]
                        + "</option>";
                where = where
                        + "<option value=\"" + counter + "\">"
                        + response[item]
                        + "</option>";
            }
            console.log(res);
            console.log(where);
            $('#gby').html(res);
            $('#where-tag').html(where);
        }
    });

}
function loadDataSource(response) {
    var res = "<option value=\"1\">--Select DataSource--</option>";
    var counter = 1;
    for (item in response) {
        counter = counter + 1;
        res = res
                + "<option value=\"" + counter + "\">"
                + response[item]
                + "</option>";

        console.log(res);
    }
    $('#dSource').html(res);

}

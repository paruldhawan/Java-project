"use strict";

var complexId = 1
var simpleId = 1
var nodeId = 1
var whereId = 1

function getDataSource($nodeChild) {
	$.ajax({
		type : "POST",
		url : "influx-api/get-datasources",
		success : function(response) {
			var res = "<option value=\"1\">--Select DataSource--</option>";
			var counter = 1;
			for ( var item in response) {
				counter = counter + 1;
				res = res + "<option value=\"" + counter + "\">"
						+ response[item] + "</option>";

				console.log(res);
			}
			console.log($nodeChild)
			$($nodeChild).html(res);

		},
		error: function(xhr, ajaxOptions, thrownError){
			var resp = xhr.responseText;
			var r = JSON.parse(resp);
			alert("Error: "+ r.error)
		}
	});

}
function getMeasurement($nodeChildMeasure, data) {
	// alert(data);
	var dataSource = {};
	dataSource["datasourceName"] = data;
	//dataSource["retentionPolicy"] = retentionPolicy;
	$.ajax({
		type : "POST",
		url : "influx-api/get-measurements",
		data : dataSource,
		success : function(response) {
			var res = "<option value=\"1\">--Select Measurement--</option>";
			var counter = 1;
			for ( var item in response) {
				counter = counter + 1;
				res = res + "<option value=\"" + counter + "\">"
						+ response[item] + "</option>";

			}
			console.log(res);
			$($nodeChildMeasure).html(res);
		},
		error: function(xhr, ajaxOptions, thrownError){
			var resp = xhr.responseText;
			var r = JSON.parse(resp);
			alert("Error: "+ r.error)
		}
	});
}

function getField($nodeChildField, measurement, datasource, retentionPolicy) {
	var row = {};
	row["datasourceName"] = datasource;
	row["measurement"] = measurement;
	row["retentionPolicy"] = retentionPolicy;
	$.ajax({
		type : "POST",
		url : "influx-api/get-fields",
		data : row,
		success : function(response) {
			var res = "<option value=\"1\">--Select Field--</option>";
			var counter = 1;
			for ( var item in response) {
				counter = counter + 1;
				res = res + "<option value=\"" + counter + "\">"
						+ response[item] + "</option>";

			}
			console.log(res);
			$($nodeChildField).html(res);
		},
		error: function(xhr, ajaxOptions, thrownError){
			var resp = xhr.responseText;
			var r = JSON.parse(resp);
			alert("Error: "+ r.error)
		}
	});
}
function getWhere($nodeChildWhere, measurement, datasource, retentionPolicy) {
	var row = {};
	row["datasourceName"] = datasource;
	row["measurement"] = measurement;
	row["retentionPolicy"] = retentionPolicy;
	$.ajax({
		type : "POST",
		url : "influx-api/get-tags",
		data : row,
		success : function(response) {

			var where = "";
			var counter = 0;
			for ( var item in response) {
				counter = counter + 1;
				where = where + "<option value=\"" + counter + "\">"
						+ response[item] + "</option>";
			}

			$($nodeChildWhere).html(where);
		},
		error: function(xhr, ajaxOptions, thrownError){
			var resp = xhr.responseText;
			var r = JSON.parse(resp);
			alert("Error: "+ r.error)
		}
	});
}

function getGroupBy($nodeChildGroup, measurement, datasource, retentionPolicy) {
	var row = {};
	row["datasourceName"] = datasource;
	row["measurement"] = measurement;
	row["retentionPolicy"] = retentionPolicy;
	$.ajax({
		type : "POST",
		url : "influx-api/get-tags",
		data : row,
		success : function(response) {
			var res = "";
			var counter = 0;
			for ( var item in response) {
				counter = counter + 1;
				res = res + "<option value=\"" + counter + "\">"
						+ response[item] + "</option>";
			}
			console.log(res);

			$($nodeChildGroup).html(res);

		},
		error: function(xhr, ajaxOptions, thrownError){
			var resp = xhr.responseText;
			var r = JSON.parse(resp);
			alert("Error: "+ r.error)
		}
	});
}
function getRetentionPolicy($nodeChildRetention, data) {
	// alert("dataS"+data);
	console.log($nodeChildRetention);
	var dataSource = {};
	dataSource["datasourceName"] = data;
	// alert(dataSource["datasourceName"]);
	$.ajax({
		type : "POST",
		url : "influx-api/get-retentionPolicies",
		data : dataSource,
		success : function(response) {
			var res = "";
			var counter = 0;
			for ( var item in response) {
				counter = counter + 1;
				res = res + "<option value=\"" + counter + "\">"
						+ response[item] + "</option>";

			}
			console.log(res);
			$($nodeChildRetention).html(res);
		},
		error: function(xhr, ajaxOptions, thrownError){
			var resp = xhr.responseText;
			var r = JSON.parse(resp);
			alert("Error: "+ r.error)
		}
	});
}
function addSimpleQuery($parent) {

	console.log("Adding simple query to ", $parent)

	var $simpleQuery = $('.simple-query-container.template').clone()
	// var $whereParent=$('.where-clause template',$simpleQuery)
	$simpleQuery.attr('id', "simple-" + simpleId.toString())
	simpleId = simpleId + 1;
	$simpleQuery.removeClass('template')

	$parent.append($simpleQuery)

	console.log("Added simple query ", $simpleQuery, " to ", $simpleQuery)
	var $nodeChild = $('.data-Source-Select', $simpleQuery);
	var $nodeChildMeasure = $('.measure-select', $simpleQuery);
	$($nodeChild).on('change', dataSourceChangeEvent);
	getDataSource($nodeChild)
	$($nodeChildMeasure).on('change', measurementChangeEvent);

	addWhereClause($('.where-container', $simpleQuery))

	$('.add-where-button', $simpleQuery).on('click', addWhereClicked);

	// getDataSource('.removeButton',$whereParent).on('click', removeWhere);
}

function addWhereClause($parent) {

	var $whereClause = $('.where-clause.template').clone()
	$whereClause.attr('id', "where-" + whereId.toString())
	whereId = whereId + 1;
	$whereClause.removeClass('template')
	// remove handler
	$('.removeButton', $whereClause).on('click', removeWhere);
	// add tag list
	var $tagSelect = $('.where-select:first', $parent)
	var $tagList = $('option', $tagSelect).clone()

	var $newSelect = $('.where-select', $whereClause)
	$('option', $newSelect).remove()
	$($newSelect).append($tagList)

	$parent.append($whereClause)

}
function removeWhere(event) {
	console.log("here");
	var $sender = event.target;
	var $parentNode = $($sender).parents('.where-clause')
	console.log("sender", $sender)
	console.log("parent node", $parentNode)
	$($parentNode).remove();
}

function addWhereClicked(obj) {
	var $parentContainer = $(this).parents('.where-container:first');
	console.log("Adding to ", $parentContainer);

	addWhereClause($parentContainer);

}

function addComplexQuery($parent) {

	console.log("Addign complex query to ", $parent)

	var $complexQuery = $('.complex-query-container.template').clone()
	$complexQuery.attr('id', 'complex-' + complexId.toString())
	complexId = complexId + 1;
	$complexQuery.removeClass('template')

	// since it don't Have child selecting will return intended child
	$('input.query-list-type-toggle', $complexQuery).on('click',
			groupTypeClicked);
	$('.add-node-button', $complexQuery).on('click', addNodeClicked);

	// #FIXME Seems like fixed
	var $nodeChildContainer = $($complexQuery).children('.node-list-container')
	console.assert($nodeChildContainer.length == 1)

	addNodeContainer($nodeChildContainer)
	$($parent).append($complexQuery)

	console.log("Adding complex query ", $complexQuery, " to ", $parent)
}

function addNodeContainer($parent) {

	console.log("Adding node to ", $parent)

	var $nodeContainer = $('.node-container.template').clone()
	$nodeContainer.attr('id', 'node-' + nodeId.toString())
	nodeId = nodeId + 1
	$nodeContainer.removeClass('template')

	$('div input.query-type-toggle', $nodeContainer).on('click',
			nodeTypeClicked);
	addSimpleQuery($('.node-child-container', $nodeContainer))

	$parent.append($nodeContainer)
	console.log("Added nodeconatiner ", $nodeContainer, " to ", $parent)
}

function addNodeClicked(obj) {
	var $parentContainer = $(this).parents('.complex-query-container:first');
	console.log("Adding to ", $parentContainer);
	var $nodeChildContainer = $($parentContainer).children(
			'.node-list-container');
	addNodeContainer($nodeChildContainer);

}
function dataSourceChangeEvent(event) {
	var $sender = event.target;
	if ($($sender).hasClass('data-Source-Select')) {
		var dataSource = $(this).children(":selected").text();
		var $simpleQuery = $($sender).parents('.simple-query-container:first');
		var $nodeChildRetention = $('.retention-Policy-Select', $simpleQuery);
		var $nodeChildMeasure = $('.measure-select', $simpleQuery);
		getRetentionPolicy($nodeChildRetention, dataSource);
		getMeasurement($nodeChildMeasure, dataSource);
	}
}
function measurementChangeEvent(event) {
	var $sender = event.target;
	if ($($sender).hasClass('measure-select')) {
		var measurement = $(this).children(":selected").text();
		var $simpleQuery = $($sender).parents('.simple-query-container:first');
		var $nodedataSource = $('.data-Source-Select', $simpleQuery);
		var $nodeRetentionPolicy = $('.retention-Policy-Select', $simpleQuery);
		var dataSource = $($nodedataSource).children(":selected").text();
		var retentionPolicy = $($nodeRetentionPolicy).children(":selected").text();
		var $nodeChildField = $('.field-select', $simpleQuery);
		getField($nodeChildField, measurement, dataSource, retentionPolicy);
		var $nodeChildWhere = $('.where-select', $simpleQuery);
		getWhere($nodeChildWhere, measurement, dataSource, retentionPolicy);
		var $nodeChildGroup = $('.group-select', $simpleQuery);
		getGroupBy($nodeChildGroup, measurement, dataSource, retentionPolicy);
	}
}

function nodeTypeClicked(event) {
	console.log("Node type clicked")
	var $sender = event.target;

	if ($($sender).hasClass('toggle-enabled')) {
		return;
	}
	var $parentNode = $($sender).parents('.node-container:first')
	$('div > .toggle-enabled:first', $parentNode).addClass('toggle-disabled')
	$('div > .toggle-enabled:first', $parentNode).removeClass('toggle-enabled')

	$($sender).addClass('toggle-enabled')
	$($sender).removeClass('toggle-disabled')

	var queryType = $($sender).attr('name')
	console.log("Changing querytype to ", queryType)
	if (queryType == 'simple') {
		$('.node-child-container >  .simple-query-container:first', $parentNode)
				.removeClass('hidden')
		$('.node-child-container >  .complex-query-container:first',
				$parentNode).addClass('hidden')
	} else if (queryType == 'complex') {

		// #FIXME
		console.log($('.node-child-container >  .complex-query-container',
				$parentNode))
		if ($('.node-child-container >  .complex-query-container', $parentNode).length == 0) {
			console.log("No children so appending")
			addComplexQuery($($parentNode).children(
					'.node-child-container:first'))
		}

		$('.node-child-container >  .simple-query-container:first', $parentNode)
				.addClass('hidden')
		$('.node-child-container >  .complex-query-container:first',
				$parentNode).removeClass('hidden')

	} else {

		console.error("unknow node type")
	}

}

function groupTypeClicked(event) {

	console.log("Group clicked")
	var $sender = event.target;

	if ($($sender).hasClass('toggle-enabled')) {
		return;
	}
	var $parentNode = $($sender).parents('.complex-query-container:first')
	$('.toggle-enabled:first', $parentNode).addClass('toggle-disabled')
	$('.toggle-enabled:first', $parentNode).removeClass('toggle-enabled')

	$($sender).addClass('toggle-enabled')
	$($sender).removeClass('toggle-disabled')

}

function parseWhereQuery($whereContainer) {

	var $children = $('.inner-wh', $whereContainer)
	var lst = []
	console.log("Children are ", $children)
	for (var i = 0; i < $children.length; i++) {

		// $children =
		// $('.node-list-container:first',$complexQuery).children('.node-container')
		var whereClause = {}
		var $child = $children.get(i)
		whereClause.tagKey = $('.where-select', $child).children(":selected")
				.text();
		whereClause.operator = $('.where-operator', $child).children(
				":selected").text();
		whereClause.value = $('.where-value', $child).val();

		lst.push(whereClause)
	}

	return lst
}

function traverseSimpleQuery($simpleQuery) {

	var val = {};

	val["type"] = "simple";

	var dataSource = $('.data-Source-Select', $simpleQuery).children(
			":selected").text();
	val["dataSource"] = dataSource;
	var retentionPolicy = $('.retention-Policy-Select', $simpleQuery).children(
			":selected").text();
	val["retentionPolicy"] = retentionPolicy;
	var measurement = $('.measure-select', $simpleQuery).children(":selected")
			.text();
	val["measurement"] = measurement;
	var groupByDuration = $('.grouptime-select', $simpleQuery).children(
			":selected").text();
	val["groupByDuration"] = groupByDuration;
	var field = $('.field-select', $simpleQuery).children(":selected").text();
	val["fieldName"] = field;
	var fieldOperator = $('.field-operator-select', $simpleQuery).children(
			":selected").text();
	val["fieldOperator"] = fieldOperator;
	var fieldThreshold = $('.threshold-value', $simpleQuery).val();
	val["fieldThreshold"] = fieldThreshold;
	var group = [];
	$('.group-select', $simpleQuery).children(":selected").each(
			function(i, selected) {
				group[i] = $(selected).text();

			});
	val["groupBy"] = group;
	var groupByOperation = $('.group-operation-select', $simpleQuery).children(
			":selected").text();
	val['groupByOperation'] = groupByOperation
	
	var derivativeOperation = $('.derivative-operation-select', $simpleQuery).children(
	":selected").text();
val['derivativeOperation'] = derivativeOperation
	
	/*
	 * var WhereTag=$('.where-select',
	 * $simpleQuery).children(":selected").text(); val["WhereTag"]=WhereTag; var
	 * WhereOperator=$('.where-operator',
	 * $simpleQuery).children(":selected").text();
	 * val["WhereOperator"]=WhereOperator; var WhereValue=$('.where-value',
	 * $simpleQuery).val(); val["WhereValue"]=WhereValue;
	 */

	var where = parseWhereQuery($('.where-container', $simpleQuery))
	val["where"] = where
	console.log("Where clause is ", where)
	return val
}
function traverseComplexQuery($complexQuery) {

	console.log("Traversing ", $complexQuery)

	var val = {}
	var id = $($complexQuery).attr('id')
	var type = "and";

	var $selectedButton = $($complexQuery).children('.toggle-container')
			.children('.toggle-enabled')
	type = $($selectedButton).attr('name')

	val.type = "complex"
	val.operation = type
	var lst = []
	var $children = $('.node-list-container:first', $complexQuery).children(
			'.node-container')
	console.log("Children are ", $children)
	for (var i = 0; i < $children.length; i++) {

		// $children =
		// $('.node-list-container:first',$complexQuery).children('.node-container')
		lst.push(traverseNode($children.get(i)))
	}

	val.subQueries = lst;
	val.id = id;

	console.log("Val ", $complexQuery, " is ", val)
	return val;
}

function traverseNode($node) {

	console.log("Traversing node ", $($node).attr('id'))

	var $nodeChildContainer = $($node).children('.node-child-container:first')

	console.log("Node children count: ",
			$($nodeChildContainer).children().length)

	var $complexQueries = $($nodeChildContainer).children(
			'.complex-query-container')
	var $simpleQueries = $($nodeChildContainer).children(
			'.simple-query-container')

	var val = {}

	console.log("Complex ", $complexQueries)
	console.log("Simple ", $simpleQueries)

	console.assert($complexQueries.length < 2)
	console.assert($simpleQueries.length == 1)

	if ($complexQueries.length == 0 || $($complexQueries).hasClass("hidden")) {
		if ($simpleQueries.length != 1) {
			console.log("Error: bad children ")
		}
		return traverseSimpleQuery($simpleQueries)

	} else {
		return traverseComplexQuery($complexQueries)
	}

}

function validateWhereQuery($whereContainer) {

	var $children = $('.inner-wh', $whereContainer)
	var lst = []
	console.log("Children are ", $children)
	for (var i = 0; i < $children.length; i++) {

		var $child = $children.get(i)
		var whereClause = {}

		whereClause.tagKey = $('.where-select', $child).children(":selected")
				.text();
		whereClause.operator = $('.where-operator', $child).children(
				":selected").text();
		whereClause.value = $('.where-value', $child).val();

		if (whereClause.tagKey === "" || whereClause.value === "") {
			alert("Insert proper where clause")
			return false
		}

	}

	return true
}

function validateSimpleQuery($simpleQuery) {

	var val = {};

	val["type"] = "simple";

	var dataSource = $('.data-Source-Select', $simpleQuery).children(
			":selected").text();
	if (dataSource === "" || dataSource == "--Select DataSource--") {
		alert("Insert proper datasource ")
		return false
	}

	var retentionPolicy = $('.retention-Policy-Select', $simpleQuery).children(
			":selected").text();
	if (retentionPolicy === "") {
		alert("Insert proper retention policy ")
		return false
	}

	var measurement = $('.measure-select', $simpleQuery).children(":selected")
			.text();
	if (measurement === "") {
		alert("Insert proper measurement ")
		return false
	}
	var groupByDuration = $('.grouptime-select', $simpleQuery).children(
			":selected").text();
	val["groupByDuration"] = groupByDuration;
	var field = $('.field-select', $simpleQuery).children(":selected").text();
	val["fieldName"] = field;
	if (field === "") {
		alert("Insert proper field ")
		return false
	}
	var fieldOperator = $('.field-operator-select', $simpleQuery).children(
			":selected").text();
	val["fieldOperator"] = fieldOperator;
	var fieldThreshold = $('.threshold-value', $simpleQuery).val();
	val["fieldThreshold"] = fieldThreshold;

	// #TODO add number comparator
	if (fieldThreshold === "") {
		alert("Insert proper field ");
		return false
	}

	var group = [];
	$('.group-select', $simpleQuery).children(":selected").each(
			function(i, selected) {
				group[i] = $(selected).text();

			});
	val["groupBy"] = group;
	var groupByOperation = $('.group-operation-select', $simpleQuery).children(
			":selected").text();
	val['groupByOperation'] = groupByOperation

	var derivativeOperation = $('.derivative-operation-select', $simpleQuery).children(
	":selected").text();
val['derivativeOperation'] = groupByOperation
	
	var where = validateWhereQuery($('.where-container', $simpleQuery))
	if (!where) {
		return false;
	}

	return val
}

function validateComplexQuery($complexQuery) {
	var val = {}
	var id = $($complexQuery).attr('id')
	var type = "and";

	var $andButton = $($complexQuery).children(
			'.query-list-type-toggle[name="and"]:first')
	if ($($andButton).hasClass("toggle-disabled")) {
		type = "or"
	}

	var $children = $('.node-list-container:first', $complexQuery).children(
			'.node-container')
	for (var i = 0; i < $children.length; i++) {
		var res = validateNode($children.get(i))
		if (!res) {
			return res;
		}

	}
	return true
}

function validateNode($node) {

	var $nodeChildContainer = $($node).children('.node-child-container:first')

	var $complexQueries = $($nodeChildContainer).children(
			'.complex-query-container')
	var $simpleQueries = $($nodeChildContainer).children(
			'.simple-query-container')

	console.assert($complexQueries.length < 2)
	console.assert($simpleQueries.length == 1)
	if ($complexQueries.length == 0 || $($complexQueries).hasClass("hidden")) {

		if ($simpleQueries.length != 1) {
			console.log("Error: bad children ")
		}
		return validateSimpleQuery($simpleQueries)

	} else {
		return validateComplexQuery($complexQueries)
	}
}


/////////////////////////////////region load///////////////////////

function loadSimpleQuery($simpleQuery, query) {

	var val = {};

	val["type"] = "simple";

	var dataSource = $('.data-Source-Select', $simpleQuery).children(
			":selected").text();
	if (dataSource === "" || dataSource == "--Select DataSource--") {
		alert("Insert proper datasource ")
		return false
	}

	var retentionPolicy = $('.retention-Policy-Select', $simpleQuery).children(
			":selected").text();
	if (retentionPolicy === "") {
		alert("Insert proper retention policy ")
		return false
	}

	var measurement = $('.measure-select', $simpleQuery).children(":selected")
			.text();
	if (measurement === "") {
		alert("Insert proper measurement ")
		return false
	}
	var groupByDuration = $('.grouptime-select', $simpleQuery).children(
			":selected").text();
	val["groupByDuration"] = groupByDuration;
	var field = $('.field-select', $simpleQuery).children(":selected").text();
	val["fieldName"] = field;
	if (field === "") {
		alert("Insert proper field ")
		return false
	}
	var fieldOperator = $('.field-operator-select', $simpleQuery).children(
			":selected").text();
	val["fieldOperator"] = fieldOperator;
	var fieldThreshold = $('.threshold-value', $simpleQuery).val();
	val["fieldThreshold"] = fieldThreshold;

	// #TODO add number comparator
	if (fieldThreshold === "") {
		alert("Insert proper field ");
		return false
	}

	var group = [];
	$('.group-select', $simpleQuery).children(":selected").each(
			function(i, selected) {
				group[i] = $(selected).text();

			});
	val["groupBy"] = group;
	var groupByOperation = $('.group-operation-select', $simpleQuery).children(
			":selected").text();
	val['groupByOperation'] = groupByOperation

	var derivativeOperation = $('.derivative-operation-select', $simpleQuery).children(
	":selected").text();
val['derivativeOperation'] = groupByOperation
	
	var where = validateWhereQuery($('.where-container', $simpleQuery))
	if (!where) {
		return false;
	}

	return val
}

function loadComplexQuery($complexQuery,query) {
	var val = {}
	var id = $($complexQuery).attr('id')
	var type = "and";

	var $andButton = $($complexQuery).children(
			'.query-list-type-toggle[name="and"]:first')
	if ($($andButton).hasClass("toggle-disabled")) {
		type = "or"
	}

	var $children = $('.node-list-container:first', $complexQuery).children(
			'.node-container')
	for (var i = 0; i < $children.length; i++) {
		var res = validateNode($children.get(i))
		if (!res) {
			return res;
		}

	}
	return true
}

function loadNode($node, query) {

	var $nodeChildContainer = $($node).children('.node-child-container:first')

	var $complexQueries = $($nodeChildContainer).children(
			'.complex-query-container')
	var $simpleQueries = $($nodeChildContainer).children(
			'.simple-query-container')

	console.assert($complexQueries.length < 2)
	console.assert($simpleQueries.length == 1)
	if ($complexQueries.length == 0 || $($complexQueries).hasClass("hidden")) {

		if ($simpleQueries.length != 1) {
			console.log("Error: bad children ")
		}
		return validateSimpleQuery($simpleQueries)

	} else {
		return validateComplexQuery($complexQueries)
	}
}

//////////////////////////////////////////////

function submitForm() {

	var validated = validateComplexQuery($('.complex-query-container:first',
			$('#tree-container')))

	if (validated == false) {
		return 

	}

	var query = traverseComplexQuery($('.complex-query-container:first',
			$('#tree-container')))
	var notifications = [];
	$('#notificationName1').children(':selected').each(function(i, selected) {
		notifications[i] = $(selected).text();
	});
	// console.log("notifs are ",notifications);
	//alert(notifications);
	$('#notificationName').val(notifications)
	$('#query-input').val(JSON.stringify(query))
	$('#create-alarm-form').submit()
}

function showJson(event) {

	var val = traverseComplexQuery($('.complex-query-container:first',
			$('#tree-container')))
	alert(JSON.stringify(val))
}
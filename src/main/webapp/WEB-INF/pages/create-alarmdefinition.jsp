<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta charset="utf-8">
<title>Create AlarmDefinition</title>




<link rel="stylesheet"
	href="<c:url value="/resources/mycss/tree.css" />" type="text/css"
	media="all" />
<link rel="stylesheet"
	href="<c:url value="/resources/mycss/tree.css" />" type="text/css"
	media="all" />
<script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/dropdown.js" />"
	type="text/javascript"></script>
<!--  <script src="<c:url value="/resources/js/ajaxCalls.js" />"type="text/javascript"></script>
	-->


<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

<script>
	var id = 1;
	function addNodeTo(parentNode) {
		var $queryNode = $('#query-ui-template').clone();
		$queryNode.attr('id', 'node-' + id.toString())
		id = id + 1;
		console.log("Parent node is ", parentNode)
		$queryNode.addClass('tree-node')
		$(".add-node-button", $queryNode).on('click', addButtonClicked)
		$(".add-sub-node-button", $queryNode).on('click', addSubNodeClicked)
		parentNode.append($queryNode);
	}
	$(document).ready(function() {
		console.log("Page ready");
		//$('#generateJson').on('click', showJson)
		addComplexQuery($('#tree-container'))
		$('#submit-button').on('click', submitForm)

	})
</script>


</head>



<body>


	<jsp:include page="bodyHeader.jsp"></jsp:include>

	<h2>Create Alarm Definition</h2>


	<div>
		<form action="create-alarmdefinition" method="POST"
			id="create-alarm-form">
			<table class="table table-striped" style="text-align: center">

				<tr>
					<td>Name:</td>
					<td><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><input type="text" name="description" /></td>
				</tr>
				<tr>
					<td>Enabled</td>
					<td><INPUT TYPE="radio" NAME="enabled" VALUE="TRUE" CHECKED>
						TRUE <INPUT TYPE="radio" NAME="enabled" VALUE="FALSE">
						FALSE</td>

				</tr>

				<tr>
					<td>Notification <input type="hidden" name="query"
						id="query-input"></input> <input type="hidden"
						name="notificationName" id="notificationName"></input>
					</td>
					<td><select name="notifications" multiple="multiple"
						style="width: 190px;" id="notificationName1">
							<c:forEach var="notification" items="${notifications}"
								varStatus="status">
								<option>${notification}</option>
							</c:forEach>
					</select></td>
				</tr>

			</table>

		</form>
	</div>


	<h2>Query</h2>



	<div id="tree-container"></div>


	<input id="submit-button" class="btn btn-warning" type="button"
		value="Add"></input>

	<div class="node-container template">
		<div>
			<input class="query-type-toggle toggle-enabled btn" type="button"
				value="Simple Query" name="simple"> </input> <input
				class="query-type-toggle toggle-disabled btn" type="button"
				value="Complex Query" name="complex"> </input>
		</div>

		<div class="node-child-container"></div>


	</div>

	<div class="simple-query-container template">

		<div class="col-lg-12">
			<h3>Query:</h3>
		</div>
		<div class="row">
			<div class="col-lg-3">
				<label name="dSource"> Data Source: </label>
			</div>

			<div class="col-lg-9">
				<select name="dSource" id="dSource"
					class="data-Source-Select form-control" style="width: 190px;">
					<option value="1">--Select DataSource--</option>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-3">
				<label>Retention Policies:</label>
			</div>
			<div class="col-lg-9">
				<select id="rp" class="retention-Policy-Select form-control"
					style="width: 190px;">

				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-3">
				<label name="measure">Measurement:</label>
			</div>

			<div class="col-lg-9">
				<select name="measure" id="measure"
					class="form-control measure-select" style="width: 190px;">
					<option value="1">--Select Measurement--</option>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-3">
				<label>GroupBy Duration:</label>
			</div>

			<div class="col-lg-9">
				<select name="groupby_duration"
					class="form-control grouptime-select" id="groupby_duration"
					style="width: 190px;">
					<option value="1">1m</option>
					<option value="1">2m</option>
					<option value="1">5m</option>
					<option value="1">10m</option>
					<option value="1">15m</option>
				</select>
			</div>
		</div >
		<div class="row">
			<div class="col-lg-3">
				<label>GroupBy Operation:</label>
			</div>

			<div class="col-lg-9">
				<select name="groupByOperation"
					class="form-control group-operation-select" id="groupby_operation"
					style="width: 190px;">
					<option value="1">mean</option>
					<option value="2">max</option>
					<option value="3">min</option>
					<option value="4">mode</option>
					<option value="5">median</option>
					<option value="6">first</option>
					<option value="7">last</option>
					<option value="8">count</option>
				</select>
			</div>
		</div>

		<!--div class="row">
			<div class="col-lg-3">
				<label>Derivative Operation:</label>
			</div>

			<div class="col-lg-9">
				<select name="derivativeOperation"
					class="form-control derivative-operation-select" id="groupby_operation"
					style="width: 190px;">
					<option value="1">none</option>
					<option value="2">derivative</option>
					<option value="3">difference</option>
					<option value="4">non_negative_derivative</option>
				</select>
			</div>
		</div-->

		<div class="row">
			<div class="col-lg-3">
				<label>Field:</label>
			</div>

			<div class="col-lg-3">
				<select name="field" id="field" class="form-control field-select"
					style="width: 190px;">
					<option value="1">--Select Field--</option>
				</select>
			</div>


			<div class="col-lg-3">
				<select name="operator" id="operator" style="width: 190px;"
					class="form-control field-operator-select">
					<option value="1">equals</option>
					<option value="2">not equals</option>
					<option value="3">less than</option>
					<option value="4">greater than</option>
				</select>
			</div>

			<div class="col-lg-3">
				<input type="text" name="threshold" placeholder="threshold"
					id="threshold" class="form-control threshold-value">
			</div>
		</div>

		<div class="row">

			<div class="col-lg-3">
				<label>Group By:</label>
			</div>

			<div class="col-lg-9">
				<select multiple="multiple" id="gby"
					class="form-control group-select" style="width: 190px;">

				</select>
			</div>
		</div>
		<div class="where-container">
			<div class="row">
				<input type="button"
					class="add-where-button right-float-button btn btn-success "
					value="+"></input>
				<div class="col-lg-3">
					<label>Where:</label>
				</div>
			</div>
			<!-- div class="inner-wh row">
				<div class="col-lg-3">
					<select id="where-tag" class="form-control where-select">
						<option value="1">--Select Tag--</option>
					</select>
				</div>
				<div class="col-lg-2">
					<select name="where-op" id="where-op" style="width: 190px;"
						class="form-control where-operator">
						<option value="1">equals</option>
						<option value="2">not equals</option>
						<option value="3">less than</option>
						<option value="4">greater than</option>
					</select>
				</div>
				<div class="col-lg-2">
					<input type="text" placeholder="value" id="where-value"
						class="form-control where-value">

				</div>
			</div-->

		</div>
	</div>

	<div class="where-clause template">
		<div class="inner-wh" class="row">
			<div class="col-lg-3">
				<label></label>
			</div>
			<div class="col-lg-3">
				<select id="where-tag" class="form-control where-select">
					<option value="1">--Select Tag--</option>
				</select>
			</div>
			<div class="col-lg-3">
				<select id="where-op" style="width: 190px;"
					class="form-control where-operator">
					<option value="1">=</option>
					<option value="2">!=</option>
					<option value="3">=~</option>
					<option value="4">!~</option>
				</select>
			</div>
			<div class="col-lg-2">
				<input type="text" placeholder="value" id="where-value"
					class="form-control where-value">
			</div>
			<div class="col-lg-1">
				<input type="button" class="removeButton btn btn-danger" value="-">
			</div>

		</div>
		<div class="where-footer"></div>
	</div>


	<div class="complex-query-container template">
		<input class="add-node-button btn btn-danger " type="button"
			value="Add Rule" name="add-node" onclick="addNodeClicked(this)"
			id="me"> </input>

		<div class="toggle-container">
			<input class="query-list-type-toggle toggle-enabled btn"
				type="button" value="AND" name="and"> </input> <input
				class="query-list-type-toggle toggle-disabled btn" type="button"
				value="OR" name="or"> </input>

		</div>

		<div class="node-list-container"></div>

	</div>


	<!--div class="add-node-template">
		<input type="button" class="add-node-button" value="+"></input>
		<div class="add-node-option-container">
			<input type="button" class="add-node-option" value="For All of"></input>
			<input type="button" class="add-node-option" value="For Any of"></input>
			<input type="button" class="add-node-option" value="Query"></input>
		</div>

	</div -->

</body>

</html>
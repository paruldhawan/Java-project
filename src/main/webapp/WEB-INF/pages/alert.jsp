

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- <script	  src="https://code.jquery.com/jquery-1.11.1.js" 
integrity="sha256-MCmDSoIMecFUw3f1LicZ/D/yonYAoHrgiep/3pCH9rw="  crossorigin="anonymous"></script>-->

<script src="https://code.jquery.com/jquery-3.1.1.js"
	integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
	crossorigin="anonymous"></script>

<c:url value="/resources/js/query-builder.standalone.min.js"
	var="mainJs" />
<script src="${mainJs}"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/mycss/bootstrap.css "/>" type="text/css"
	media="all" />
<link rel="stylesheet"
	href="<c:url value="/resources/mycss/query-builder.default.min.css" />"
	type="text/css" media="all" />

</head>

<body>
	<div
		style="padding: 10px 10px 6px; border: 1px solid rgb(220, 200, 150); background: rgba(250, 240, 210, 0.5) none repeat scroll 0% 0%;">
		<p>
			<label for="DataSource">Data Source:</label>
			<!--   <select name="dSource" id="dSource" size="0" class="form-control" style="width: 190px;">
=======
<script src="https://code.jquery.com/jquery-3.1.1.js" 
integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA=" crossorigin="anonymous"></script>

<c:url value="/resources/js/query-builder.standalone.min.js" var="mainJs" />
 <script src="${mainJs}"></script>
<link rel="stylesheet" href="<c:url value="/resources/mycss/bootstrap.css "/>" type="text/css" media="all" />
<link rel="stylesheet" href="<c:url value="/resources/mycss/query-builder.default.min.css" />" type="text/css" media="all" />

    </head>
   
    <body>
    <div style="padding: 10px 10px 6px; border: 1px solid rgb(220, 200, 150); background: rgba(250, 240, 210, 0.5) none repeat scroll 0% 0%;">
        <p>
            <label for="DataSource">Data Source:</label>
          <!--   <select name="dSource" id="dSource" size="0" class="form-control" style="width: 190px;">
>>>>>>> SimpleQuery
                <option value="1">DS1</option>
                <option value="2">DS2</option>
                <option value="3">DS3</option>
            </select>-->
<<<<<<< HEAD
			<select name="dSource" id="dSource" size="0" class="form-control"
				style="width: 190px;">
				<option value="1">--Select DataSource--</option>
				<c:forEach items="${db}" var="data">
					<option value="${data}">${data}</option>
				</c:forEach>

			</select>
		</p>
		<p>
			<label for="Measurement">Measurement:</label> <select name="measure"
				id="measure" class="form-control" style="width: 190px;">
				<option value="1">--Measurements--</option>
				<!--  <option value="2">Measurement2 for DS1</option>
                <option value="3">Measurement3 for DS1</option>-->
			</select>
		</p>
		<p>
			<label for="col">Field</label> <select name="col" id="col"
				class="form-control" style="width: 190px;">
				<option value="1">--Fields--</option>
			</select> <select name="op" id="op" class="form-control" style="width: 190px;">
				<option value="1">equals</option>
				<option value="2">not equals</option>
				<option value="3">greater than</option>
				<option value="1">less than</option>
				<option value="2">greater than equals</option>
				<option value="3">less than equals</option>
			</select> <input type="text" id="threshold" name="threshold"
				placeholder="threshold" class="form-control" style="width: 190px;" />
		</p>
		<p>
			<label for="fields">Group By:</label> <select name="f" id="f"
				class="form-control" style="width: 190px;">
				<option value="1">--TAGS--</option>

			</select>



		</p>
		</br> <label for="where" id="where" style="display: none">where:</label>
		<div id="builder"></div>

		<button id="btn-get-sql" onclick="getQuery(value)">Get Query</button>
	</div>
</body>
<script type="text/javascript">
	$dsource = $('#dSource')
	$table = $('#measure')
	$field = $('#f')
	$colmn = $('#col')
	$btn = $('#btn-get-sql')

	$dsource.change(function() {
		var x = document.getElementById("dSource").selectedIndex;
		var y = document.getElementById("dSource").options;
		console.log(x);

		$.ajax({
			type : "GET",
			url : "queryController",
			data : {
				'dSource' : x,
				request : 'dbList',
				'dataSource' : y[x].text
			},

			success : function(data) {
				$("#measure").html(data)
				console.log(data);

			}
		});
	});

	$table.change(function() {
		var i = document.getElementById("measure").selectedIndex;
		var j = document.getElementById("measure").options;
		var p = document.getElementById("dSource").selectedIndex;
		var q = document.getElementById("dSource").options;
		//console.log(x);

		$.ajax({
			type : "GET",
			url : "getFields",
			data : {
				'measure' : j[i].text,
				request : 'dbTable',
				'database' : q[p].text
			},

			success : function(data) {
				$("#col").html(data)
				console.log(data);

			}
		});
	});

	$colmn.change(function() {
		var x = document.getElementById("measure").selectedIndex;
		var y = document.getElementById("measure").options;
		var p = document.getElementById("dSource").selectedIndex;
		var q = document.getElementById("dSource").options;
		console.log(x);

		$.ajax({
			type : "GET",
			url : "groupByClause",
			data : {
				'measure' : y[x].text,
				'database' : q[p].text
			},

			success : function(data) {
				$("#f").html(data)
				console.log(data);

			}
		});
	});

	$field.change(

	function() {
		var filters = {
			filters : [ {
				id : '----',
				label : '----'
			//type: 'string'
			} ]
		};

		$('#f option').each(function() {
			var obj = {};
			var key = $(this).text();
			console.log(key);

			filters['filters'].push({
				id : key,
				label : key
			});
		});

		document.getElementById('where').style.display = 'block';
		console.log(filters);
		showBuilder(filters);
	}

	);

	function showBuilder(filters) {
		//$('#builder').load(window.location.href + " #builder" )
		$('#builder').queryBuilder(filters);
		//$( "#builder" ).load();
	}

	function getQuery() {
		//var query;
		var x = document.getElementById("dSource").selectedIndex;
		var y = document.getElementById("dSource").options;
		var ds = "";
		ds = ds + y[x].text;

		var z = document.getElementById("measure").selectedIndex;
		var w = document.getElementById("measure").options;
		var ms = w[z].text;

		var r = document.getElementById("col").selectedIndex;
		var s = document.getElementById("col").options;
		var colmn = s[r].text;

		var i = document.getElementById("op").selectedIndex;
		var j = document.getElementById("op").options;
		var operator = j[i].text;

		var q = document.getElementById("f").selectedIndex;
		var p = document.getElementById("f").options;
		var fs = p[q].text;

		var th = $("#threshold").val();

		var result = $('#builder').queryBuilder('getRules');
		console.log(result);
		if (!$.isEmptyObject(result)) {
			var query = JSON.stringify(result, null, 2);
		}
		// var query = result.sql;

		$.ajax({
			type : "GET",
			url : "createAlertCode",
			data : {
				'dataSource' : ds,
				'Measurement' : ms,
				'Field' : colmn,
				'Operator' : operator,
				'FieldThreshold' : th,
				'groupBy' : fs,
				'where' : query
			},

			success : function(data) {
				console.log(data);

			}
		});

		console.log(query);

		query = "Data Source:" + ds + "\nMeasurement:" + ms + "\nField:"
				+ colmn + "\nOperator:" + operator + "\nField Threshold:" + th
				+ "\nGROUP BY:" + fs + "\nWHERE:" + query;
		console.log(query);
		alert(query);

	}
</script>
=======
            <select name="dSource" id="dSource" size="0" class="form-control" style="width: 190px;">
						 <option value="1">--Select DataSource--</option>
						    <c:forEach items="${db}" var="data">
								<option value="${data}">${data}</option>
							</c:forEach> 
						 
						</select>
        </p>
        <p>
            <label for="Measurement">Measurement:</label>
            <select name="measure" id="measure" class="form-control" style="width: 190px;">
                <option value="1">--Measurements--</option>
              <!--  <option value="2">Measurement2 for DS1</option>
                <option value="3">Measurement3 for DS1</option>-->
            </select>
        </p>   
         <p>
            <label for="col">Field</label>
            <select name="col" id="col" class="form-control" style="width: 190px;">
                <option value="1">--Fields--</option>
                   </select>
            <select name="op" id="op" class="form-control" style="width: 190px;">
                <option value="1">equals</option>
                <option value="2">not equals</option>
                <option value="3">greater than</option>
                <option value="1">less than</option>
                <option value="2">greater than equals</option>
                <option value="3">less than equals</option>
            </select>
            <input type="text" id="threshold" name="threshold" placeholder="threshold" class="form-control"  style="width: 190px;"/>
        </p>  
         <p>
            <label for="fields">Group By:</label>
           <select name="f" id="f" class="form-control" style="width: 190px;">
                <option value="1">--TAGS--</option>
              
            </select>
            
           
            
        </p>         
         </br>
          <label for="where" id="where" style="display: none">where:</label>
         <div id="builder"></div>
         
    <button id="btn-get-sql" onclick="getQuery(value)">Get Query</button>
        </div>
    </body>
    <script type="text/javascript">
   
        $dsource = $('#dSource')
       $table=$('#measure')
       $field=$('#f')
       $colmn=$('#col')
       $btn=$('#btn-get-sql')
       
        $dsource.change (
            function() {
            	 var x = document.getElementById("dSource").selectedIndex;
            	 var y = document.getElementById("dSource").options;
            	    console.log(x);
            	    
                $.ajax({
                    type: "GET",
                    url: "queryController",
                    data: {'dSource' : x ,request: 'dbList' ,'dataSource':y[x].text },
                  
                    success: function(data){
                        $("#measure").html(data)
                        console.log(data);
                    
                     
                    }
                });
            }
        );
        
      
        $table.change (
                function() {
                	 var i = document.getElementById("measure").selectedIndex;
                	 var j = document.getElementById("measure").options;
                	 var p=document.getElementById("dSource").selectedIndex;
                	var q=document.getElementById("dSource").options;
                	    //console.log(x);
                	    
                    $.ajax({
                        type: "GET",
                        url: "getFields",
                        data: {'measure' :j[i].text  ,request: 'dbTable' ,'database':q[p].text },
                      
                        success: function(data){
                            $("#col").html(data)
                            console.log(data);
                            
                        }
                    });
                }
            );
        
        $colmn.change (
                function() {
                	 var x = document.getElementById("measure").selectedIndex;
                	 var y = document.getElementById("measure").options;
                	 var p=document.getElementById("dSource").selectedIndex;
                	 var q=document.getElementById("dSource").options;
                	    console.log(x);
                	    
                    $.ajax({
                        type: "GET",
                        url: "groupByClause",
                        data: {'measure' :y[x].text  ,'database':q[p].text },
                      
                        success: function(data){
                            $("#f").html(data)
                            console.log(data);
                            
                        }
                    });
                }
            );
        
        $field.change (
        		 
                function() {
                	var filters = {filters: [{
                       id: '----',
                       label: '----'
                        //type: 'string'
                    }]};

                	 $('#f option').each(function(){
                		 var obj = {};
                		 var key=$(this).text();
                		 console.log(key);
                		
                		 filters['filters'].push({id: key, label: key});
                	    });
                	 
                	 document.getElementById('where').style.display='block';
                	    console.log(filters);
                	 showBuilder(filters);
                }	 
                	
            );
        
        function showBuilder(filters) {
            //$('#builder').load(window.location.href + " #builder" )
            $('#builder').queryBuilder(filters);
            //$( "#builder" ).load();
        }
        
     
    		  function getQuery(){
            //var query;
             var x = document.getElementById("dSource").selectedIndex;
            	 var y = document.getElementById("dSource").options;
            	 var ds="";
            	 ds=ds+y[x].text;
            	 
              var z = document.getElementById("measure").selectedIndex;
              	 var w = document.getElementById("measure").options;
              	 var ms=w[z].text;
              	 
              	 var r=document.getElementById("col").selectedIndex;
              	 var s=document.getElementById("col").options;
              	 var  colmn=s[r].text;
              	 
              	var i=document.getElementById("op").selectedIndex;
             	 var j=document.getElementById("op").options;
             	 var  operator=j[i].text;
               	 
          var q = document.getElementById("f").selectedIndex;
           	 var p = document.getElementById("f").options;
            	 var fs=p[q].text;
               	 
            	var th=$("#threshold").val();
               	         	 
            var result = $('#builder').queryBuilder('getRules');
            console.log(result);
            if (!$.isEmptyObject(result)) {
              var query=JSON.stringify(result, null, 2);
              }
           // var query = result.sql;
           
             $.ajax({
                        type: "GET",
                        url: "createAlertCode",
                        data: {'dataSource':ds,'Measurement':ms,'Field':colmn,'Operator':operator,'FieldThreshold':th,'groupBy':fs,'where':query },
                      
                        success: function(data){
                            console.log(data);
                            
                        }
                    });
             
            console.log(query);
            
            query="Data Source:"+ds+"\nMeasurement:"+ms+"\nField:"+colmn+"\nOperator:"+operator+"\nField Threshold:"+th+"\nGROUP BY:"+fs+"\nWHERE:"+query;
            console.log(query);
            alert(query);

        }
    
    </script>
>>>>>>> SimpleQuery
</html>


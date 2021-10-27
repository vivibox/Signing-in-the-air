<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

    <link rel="stylesheet" type="text/css"
          href="/resource/jqgrid/css/ui.jqgrid-bootstrap4.css" />




    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <style>
        .pagination {
            font-size: 80%;
        }

        .pagination a {
            text-decoration: none;
            border: solid 1px #AAE;
            color: #15B;
        }

        .pagination a, .pagination span {
            display: block;
            float: left;
            padding: 0.3em 0.5em;
            margin-right: 5px;
            margin-bottom: 5px;
        }

        .pagination .current {
            background: #26B;
            color: #fff;
            border: solid 1px #AAE;
        }

        .pagination .current.prev, .pagination .current.next {
            color: #999;
            border-color: #999;
            background: #fff;
        }
    </style>


    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1>List of out-for-delivery</h1>

<div>${deletesucceed}</div>


<table id="tblGridId" > </table> <div id="tblGridPager" style="text-align:center" > </div>

<%--<button onclick="location='/admin/chip/index'">回到index</button>--%>
<script>
    $(function(){

        $("#tblGridId").jqGrid({
            url: '/parcels/out-for-delivery1',
            datatype: 'json',
            mtype: 'GET',
            styleUI : 'Bootstrap4',
            iconSet: 'fontAwesome',
            colModel: [
                { name: 'pid', label: 'Delivery ID'},
                { name: 'fromAddress', label: 'From Address'},
                { name: 'toAddress', label: 'To Address'},
                { name: 'status', label: 'Delivery status'},
                { name: 'receiver', label: 'Delivery receiver'},
                { name: 'signeePhoto', label: 'Photo of signature'},
                { name: 'signeeSignaturePhoto', label: 'Photo of signature with person'},
            ],
            pager: '#tblGridPager',
            rowNum: 3,
            page: 1,
            height: 'auto',
            autowidth: 'auto',
            sortorder: 'asc',
            rowList: [3,4,6],
        });


    });

    // function edit(Object) {
    //     window.location.href = "/admin/chip/index?chipNum=" + Object.id;
    // }
    // function del(Object) {
    //     $(location).attr('href', '/admin/chip/index?chipNum=' + Object.id);
    // }
</script>

<script src="/resource/js/all.min.js"></script>
<!-- jquery include ajax -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<!-- 	bootstrap 4.3.1 -->
<script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>

<!-- jqGrid -->
<!-- We support more than 40 localizations -->
<script type="text/javascript" src="/resource/jqgrid//grid.locale-tw.js"></script>
<!-- This is the Javascript file of jqGrid -->
<script type="text/javascript" src="/resource/jqgrid/jquery.jqGrid.min.js"></script>
</body>
</html>
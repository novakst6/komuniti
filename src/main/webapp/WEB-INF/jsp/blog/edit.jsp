<%-- 
    Document   : add
    Created on : 2.8.2012, 15:39:49
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="../../parts/header.jspf" %>

<style type="text/css" media="all">
    <!--
    @import url("<spring:url value="/texteditor/css/widgEditor.css" />");
    -->
</style>

<script src="<spring:url value="/texteditor/scripts/widgEditor.js" />"></script>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">

            <div class="page-header">
                <h2>Přidání článku</h2>
            </div>

            <form:form action="edit.htm" method="POST" commandName="addFormModel" enctype="multipart/form-data">
                <div class="row-fluid">
                    <div class="span7">

                        <form:errors path="*" cssClass="alert alert-error" element="div" />
                        <div class="control-group"> 
                            <form:label path="title" >Nadpis:</form:label>
                            <form:input path="title" cssClass="input-xxlarge" id="text_title" />
                            <form:errors path="title" cssClass="alert alert-error" element="div" />
                        </div>
                        <div class="control-group">
                            <form:label path="text" >Text:</form:label>
                            <form:textarea path="text" cssClass="widgEditor nothing" id="text_text" />
                            <form:errors path="text" cssClass="alert alert-error" element="div" />
                        </div>
                            <form:hidden path="id" />
                        <div class="control-group pull-right">
                            <button class="btn btn-primary" id="btn_edit_article_submit">Upravit</button>
                        </div>

                    </form:form>    
                </div>

            </div>
        </div>

        <%@include file="../../parts/footer.jspf" %>



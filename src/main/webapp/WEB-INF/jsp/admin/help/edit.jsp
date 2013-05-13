<%-- 
    Document   : edit
    Created on : 2.8.2012, 15:39:57
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../../../parts/header.jspf" %>

<style type="text/css" media="all">
    <!--
	@import url("<spring:url value="/texteditor/css/widgEditor.css" />");
     -->
</style>

<script src="<spring:url value="/texteditor/scripts/widgEditor.js" />"></script>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="../../../parts/adminmenu.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Editace nápovědy</h2>
            </div>
                
            <form:form action="edit.htm" method="POST" commandName="editFormModel" enctype="multipart/form-data">   
                <filedset>
                    <form:errors path="*" cssClass="alert alert-error" element="div" />
                    <div class="control-group"> 
                        <form:label path="title" >Název:</form:label>
                        <form:input path="title" />
                        <form:errors path="title" cssClass="alert alert-error" element="div" />
                    </div>
                    <div class="control-group">
                        
                        <form:label path="text" >Text:</form:label>
                        <form:textarea path="text" cssClass="widgEditor nothing" />
                        <form:errors path="text" cssClass="alert alert-error" element="div" />
                    </div>
                   
                        <form:hidden path="id" />
             </filedset>
                    <button class="btn">Uložit</button>
                    <a href="show.htm" class="btn">Zrušit</a>
               
            </form:form>    
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>
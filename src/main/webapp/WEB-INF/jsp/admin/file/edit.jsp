<%-- 
    Document   : edit
    Created on : 2.8.2012, 15:39:57
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib  prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="../../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="../../../parts/adminmenu.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Editace souboru</h2>
            </div>
   
            <form:form action="edit.htm" method="POST" commandName="editFormModel" enctype="multipart/form-data">
                    <form:errors path="*" cssClass="alert alert-error" element="div" />
                    <div class="control-group">
                        <form:label path="description" >Popis souboru:</form:label>
                        <form:input path="description" />
                        <form:errors path="description" cssClass="alert alert-error" element="div" />
                    </div>
                    <div class="well alert-info">
                        <form:checkbox path="keepFile" /> Ponechat původní soubor
                    </div>
                    <div class="control-group">
                        <form:label path="file" >Vybrat nový soubor:</form:label>
                        <form:input path="file" type="file" />
                        <form:errors path="file" cssClass="alert alert-error" element="div" />
                    </div>
                    <div class="control-group">
                        <form:hidden path="id" />
                    </div>
                    <button class="btn btn-primary">Uložit</button>
                    <a href="show.htm" class="btn">Zrušit</a>
            </form:form>    
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>
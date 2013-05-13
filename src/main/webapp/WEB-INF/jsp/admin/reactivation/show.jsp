<%-- 
    Document   : show
    Created on : 2.8.2012, 15:37:43
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="../../../parts/adminmenu.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Reaktivace smazaného uživatele</h2>
            </div>
             
            <form:form action="show.htm" method="POST" commandName="showFormModel" style="margin-top: 1em;">
                <form:errors path="*" cssClass="alert alert-error" element="div" />
                <div class="control-group">
                        <form:label path="email" >Email uživatele:</form:label>
                        <form:input path="email" />
                        <form:errors path="email" cssClass="alert alert-error" element="div" />
                </div>
            <button class="btn btn-primary">Odeslat</button>
            </form:form>
    
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>



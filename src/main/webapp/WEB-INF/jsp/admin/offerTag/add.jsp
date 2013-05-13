<%-- 
    Document   : add
    Created on : 7.6.2012, 15:39:49
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
                <h2>Přidání kategorie nabídky</h2>
            </div>
                
            <form:form action="add.htm" method="POST" commandName="addFormModel">

                    <div class="control-group">
                        <form:label path="name" >Jméno kategorie:</form:label>
                        <form:input path="name" />
                        <form:errors path="name" cssClass="alert alert-error" element="div" />
                    </div>
                    <button class="btn btn-primary">Přidat</button>

            </form:form>    
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>



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
                <h2>Nastavení aplikace</h2>
            </div>
            
            <c:choose>
                <c:when test="${conf != null}">
                    <c:if test="${conf.contentModerator != null}">
                        <h4><b>Správce obsahu:</b> ${conf.contentModerator.email}</h4>
                    </c:if>
                    <c:if test="${conf.contentModerator == null}">
                        <h4>Nekonfigurováno.</h4>
                    </c:if>    
                </c:when>
            </c:choose>
             
            <form:form action="config.htm" method="POST" commandName="confFormModel" style="margin-top: 1em;">
                <div class="control-group">
                        <form:label path="contentModerator" >Správce obsahu:</form:label>
                        <form:input path="contentModerator" />
                        <form:errors path="*" cssClass="alert alert-error" element="div" />
                </div>
            <button class="btn btn-primary">Nastavit</button>
            </form:form>
    
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>



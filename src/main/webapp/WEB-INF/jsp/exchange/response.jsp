<%-- 
    Document   : new
    Created on : 16.7.2012, 19:07:12
    Author     : novakst6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span4">

            <div class="page-header">
                <h2>Odpověď na inzerát</h2>
                <h4>${offer.title}</h4>
            </div>
            
            <form:form action="response.htm" commandName="newFormModel" method="POST">
                 <form:errors path="*" cssClass="alert alert-error" element="div" />
                 
                    <div class="container">  
                        <b>Příjemce:</b> ${offer.author.email}
                    </div>
                    <br />
                        <div class="control-group">
                        <form:label path="subject" >Předmět zprávy:</form:label>
                        <form:input path="subject" size="150" id="text_subject"/>
                        <form:errors path="subject" cssClass="alert alert-error" element="div" />   
                        </div> 
                        <div class="control-group">
                        <form:label path="text" >Text:</form:label>
                        <form:textarea path="text" rows="10" id="text_text"/>
                        <form:errors path="text" cssClass="alert alert-error" element="div" />   
                        </div>
                        <form:hidden path="recepientId" />
                        <form:hidden path="offerId" />
                        
                    <div class="btn-group">
                        <button class="btn btn-primary" id="btn_response_submit">Odeslat</button><a href="<spring:url value="show.htm" />" class="btn">Zrušit</a>
                    </div>   
            </form:form>   
             
        </div>
      </div>
</div>
        <%@include file="../../parts/footer.jspf" %>

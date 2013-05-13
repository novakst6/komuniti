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
        <div class="span2">
            <%@include file="../../parts/mailmenu.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Nová zpráva</h2>
            </div>
            
            <form:form action="new.htm" commandName="newFormModel" method="POST">
                 <form:errors path="*" cssClass="alert alert-error" element="div" />

                    <div class="control-group">
                        <span>    
                        <form:label path="recepient" >Příjemce:</form:label>
                        <form:input path="recepient" id="text_recepient"/>
                        <form:errors path="recepient" cssClass="alert alert-error" element="div" />
                        </span>
                    </div>
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
                    <div class="btn-group">
                        <button class="btn btn-primary" id="btn_send_message">Odeslat</button><a href="<spring:url value="inbox.htm" />" class="btn">Zrušit</a>
                    </div>
   
            </form:form>   
             
        </div>
      </div>
</div>
        <%@include file="../../parts/footer.jspf" %>

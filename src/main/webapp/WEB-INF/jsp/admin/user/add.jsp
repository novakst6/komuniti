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
                <h2>Přidání nového uživatele</h2>
            </div>
                
            <form:form action="add.htm" method="POST" commandName="addFormModel">
                <form:errors path="*" cssClass="alert alert-error" element="div" />

                    <div class="control-group">
                        <form:label path="email" >Email:</form:label>
                        <form:input path="email" />
                        <form:errors path="email" cssClass="alert alert-error" element="div" />
                    </div>
                    
                    <div class="control-group">
                        <form:label path="googleName" >Jméno a příjmení:</form:label>
                        <form:input path="googleName" />
                        <form:errors path="googleName" cssClass="alert alert-error" element="div" />
                    </div>
                    
                    <div class="control-group">
                        <form:label path="regionId">Region:</form:label>
                        <form:select path="regionId">
                            <c:forEach items="${regions}" var="r">
                                <c:forEach items="${r}" var="n">
                                    <form:option value="${n.region.id}">
                                        <c:forEach begin="1" end="${n.index}">&nbsp;&nbsp;</c:forEach>
                                        ${n.region.name}
                                    </form:option>
                                </c:forEach>
                            </c:forEach>
                        </form:select> 
                        <form:errors path="regionId" cssClass="alert alert-error" element="div"/>
                    </div>
                    <div class="control-group">
                        <form:label path="centerId">Centrum:</form:label>
                        <form:select items="${centerMap}" path="centerId"/>
                        <form:errors path="centerId" cssClass="alert alert-error" element="div"/>
                    </div>
                    
                    <div class="control-group">
                        <form:label path="active" >Aktivní:
                        <form:checkbox path="active" />
                        </form:label>
                        <form:errors path="active" cssClass="alert alert-error" element="div" />
                    </div>
                    <button class="btn btn-primary">Přidat</button>

            </form:form>    
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>



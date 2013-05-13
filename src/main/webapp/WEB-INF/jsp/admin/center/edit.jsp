<%-- 
    Document   : edit
    Created on : 7.6.2012, 15:39:57
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
                <h2>Editace centra</h2>
            </div>
                
            <form:form action="edit.htm" method="POST" commandName="editFormModel">
                    <form:errors path="*" cssClass="alert alert-error" element="div" />
                <filedset>
                    <div class="control-group">
                        <form:label path="name" >Název centra:</form:label>
                        <form:input path="name" cssClass="input-xxlarge"/>
                        <form:errors path="name" cssClass="alert alert-error" element="div" />
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
                        <form:label path="info" >Informace:</form:label>
                        <form:textarea path="info" cssClass="widgEditor nothing"/>
                        <form:errors path="info" cssClass="alert alert-error" element="div" />
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
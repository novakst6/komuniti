<%-- 
    Document   : edit
    Created on : 2.8.2012, 15:39:57
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
                <h2>Editace štítku</h2>
            </div>
                
            <form:form action="edit.htm" method="POST" commandName="editFormModel">

                    <form:errors path="*" cssClass="alert alert-error" element="div" />
                    <div class="control-group">
                        <form:label path="parentId">Nadřazený štítek:</form:label>
                        <form:select path="parentId">
                            <c:forEach items="${tags}" var="t">
                                <c:forEach items="${t}" var="n">
                                    <form:option value="${n.tag.id}">
                                        <c:forEach begin="1" end="${n.index}">&nbsp;&nbsp;</c:forEach>
                                        ${n.tag.name}
                                    </form:option>
                                </c:forEach>
                            </c:forEach>
                        </form:select> 
                        <form:errors path="parentId" cssClass="alert alert-error" element="div"/>
                    </div>
                    <div class="control-group">
                        <form:label path="name" >Jméno štítku:</form:label>
                        <form:input path="name" />
                        <form:errors path="name" cssClass="alert alert-error" element="div" />
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
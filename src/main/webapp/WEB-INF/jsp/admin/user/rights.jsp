<%-- 
    Document   : rights
    Created on : 7.7.2012, 17:27:29
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@include file="../../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="../../../parts/adminmenu.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Administrace rolí uživatele <sec:authentication property="principal.username"/></h2>
            </div>
                
            <form:form action="rights.htm" method="POST" commandName="rightsFormModel">
                <form:errors path="*" cssClass="alert alert-error" element="div" />
                    <div class="control-group">
                        <c:forEach items="${rolesMap}" var="r">
                            <form:label path="roles">
                                <form:checkbox path="roles" value="${r.id}" cssClass="checkbox"/> ${r.name} 
                            </form:label>
                        </c:forEach>
                        <form:errors path="roles" cssClass="alert alert-error" element="div" />
                    </div>
                    <div class="control-group">
                        <form:hidden path="id" />
                        <form:errors path="id" cssClass="alert alert-error" element="div" />
                    </div>
                    <button class="btn btn-primary">Odeslat</button>
            </form:form>    
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>

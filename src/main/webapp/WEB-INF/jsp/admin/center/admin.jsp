<%-- 
    Document   : show
    Created on : 7.6.2012, 15:37:43
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
        <div class="span8">

            <div class="page-header">
                <h2>Nastavení administrátora centra</h2>
            </div>
            <div class="pull-right">
                <form:form commandName="centerAdminFulltextFilterForm" action="fulltext.htm?id=${adminFormModel.centerId}" method="POST">
                    <form:input path="keywords" id="text_keyword" />
                    <button class="btn" type="submit" id="btn_fulltext">Hledej</button>
                </form:form>
            </div>
            <div class="container-fluid">    
            <form:form action="admin.htm" method="POST" commandName="adminFormModel" style="margin-top: 1em;">
                <form:errors path="*" cssClass="alert alert-error" element="div"/>
            <table class="table table-striped">
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="span1"><form:radiobutton path="adminId" value="${user.id}" /></div>
                                    <div class="span1"><span class="badge badge-info">${user.id}</span></div>
                                    <div class="span8"><span>${user.email}</span> <span>[${user.googleName}]</span></div>
                                </div>
                            </td>    
                        </tr>
                    </c:forEach>
                        </tbody>
            </table> 
                <form:hidden path="centerId" />
                <button class="btn btn-primary">Uložit</button>
            </form:form>
            </div>    
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>



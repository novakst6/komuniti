<%-- 
    Document   : show
    Created on : 2.7.2012, 22:05:07
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
                <h2>Administrace aktivace uživatelů</h2>
            </div>
                
            <table class="table table-striped">
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="span1"><span class="badge">${user.id}</span></div>
                                    <div class="span6">${user.email} [${user.googleName}]</div>
                                    <div class="span3">
                                    <c:forEach items="${user.roles}" var="role" >
                                        <span class="badge badge-inverse">${role.name}</span>
                                    </c:forEach>
                                    </div>
                                    <div class="span2"><a class="btn btn-primary" href="active.htm?id=${user.id}" id="link_active${user.id}">Aktivovat účet</a></div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
            </table>
            <%@include file="../../../parts/paginator.jspf" %>
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>



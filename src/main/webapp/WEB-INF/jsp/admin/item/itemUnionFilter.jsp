<%-- 
    Document   : itemUnionFilter
    Created on : 8.5.2013, 14:42:41
    Author     : novakst6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-striped">
<c:forEach items="${items}" var="i">
    <tr>
        <td>
            <div class="row-fluid">
                <div class="span1"><span class="badge">${i.id}</span></div>
                <div class="span8">${i.name}</div>
                <div class="span3"><a class="btn btn-navbar" href="unionAdd.htm?id=${i.id}">Přidej</a></div>
            </div>
        </td>                  
    </tr>
</c:forEach>
</table>

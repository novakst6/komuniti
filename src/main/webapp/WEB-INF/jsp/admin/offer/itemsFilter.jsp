<%-- 
    Document   : itemsFilter
    Created on : 4.4.2013, 6:09:05
    Author     : novakst6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<table class=" table table-striped">
    <c:forEach items="${items}" var="item">
        <tr>
            <td>
                <div class="span2"><input type="radio" name="itemId" value="${item.id}" id="itemId${item.id}"/></div>
                <div class="span6">${item.name}</div>
            </td>
        </tr>
    </c:forEach>
</table>

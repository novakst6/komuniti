<%-- 
    Document   : help
    Created on : 6.6.2012, 16:20:14
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span10">

            <div class="page-header">
                <h2>Nápověda</h2>
            </div>
            <div class="container-fluid">
            <div class="span10">
            <table class="table table-striped">
                    <c:forEach items="${helps}" var="h">
                        <tr>
                            <td>
                                <div class="span12">
                                    <a href="detail.htm?id=${h.id}" id="link_help${h.id}}"><h4>${h.title}</h4></a>
                                </div>
                            </td>                          
                        </tr>
                    </c:forEach>
            </table> 
                <%@include file="../../parts/paginator.jspf" %>
            </div>
                
            
                </div>
        </div>
      </div>
</div>
        <%@include file="../../parts/footer.jspf" %>
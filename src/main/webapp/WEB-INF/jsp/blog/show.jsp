<%-- 
    Document   : show
    Created on : 7.6.2012, 15:37:43
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
                <h2>Blog</h2>
            </div>
            <a href="add.htm" class="btn btn-primary">Přidat nový článek</a>
            <div class="pull-right">
                <form:form commandName="blogTextFilterForm" action="show.htm" method="POST">
                    <form:input path="keywords" id="text_keyword" />
                    <button class="btn" type="submit" id="btn_fulltext">Hledej</button>
                </form:form>
            </div>
            
            <table class="table table-striped">
                <tbody>
                    <c:forEach items="${articles}" var="a">
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="span7"><a href="detail.htm?id=${a.id}" id="link_article${a.id}"><h4>${a.title}</a></h4></div>
                                    <div class="span3"><span>${a.author.googleName} </span><span class="badge badge-info"> ${a.author.email}</span></div>
                                    <c:if test="${userName == a.author.email}">
                                        <div class="span1"><a class="btn btn-navbar" href="edit.htm?id=${a.id}" id="link_edit${a.id}">Upravit</a></div>
                                        <div class="span1"><a class="btn btn-danger" href="delete.htm?id=${a.id}" id="link_delete${a.id}">Smazat</a></div>
                                    </c:if>
                                </div>
                            </td>          
                        </tr>
                    </c:forEach>
                        </tbody>
            </table> 
              
            <%@include file="../../parts/paginator.jspf" %>
        </div>
      </div>
</div>
        <%@include file="../../parts/footer.jspf" %>



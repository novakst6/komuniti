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
        <div class="span2">
            <%@include file="../../parts/catalogfilter.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Katalog věcí</h2>
            </div>
            <div class="pull-right">
                <form:form commandName="fullTextFilterForm" action="fulltext.htm" method="POST">
                    <form:input path="keywords" id="text_keywords" />
                    <button class="btn btn-primary" type="submit" id="btn_fulltext_submit" >Hledej</button>
                </form:form>
            </div>
            <table class="table table-striped">
            <c:forEach items="${items}" var="i" varStatus="itemStatus">
                <tr>
                    <td>
                <div class="row-fluid">
                    <div class="span6"><h4><a href="detail.htm?id=${i.id}" id="link_item${itemStatus.index}">${i.name}</a></h4></div>
                    <div class="span4">
                        <c:forEach items="${i.tags}" var="tag" varStatus="tagStatus">
                            <c:if test="${tagStatus.index < 2}">
                                <span class="badge badge-inverse">${tag.name}</span>
                            </c:if>
                            <c:if test="${tagStatus.index == 2}">
                                <a data-toggle="collapse" data-target="#all-tags${itemStatus.index}">...</a><br />    
                            </c:if>
                        </c:forEach>
                        
                        <div class="collapse" id="all-tags${itemStatus.index}">
                            <c:forEach items="${i.tags}" var="tag" begin="2">
                                <span class="badge badge-inverse">${tag.name}</span>
                            </c:forEach>
                            <div>&nbsp;</div>
                        </div>
                    </div>
                        <div class="span2"><a class="btn" href="<spring:url value="/exchange/add.htm?id=${i.id}"/>">Vytvořit nabídku</a></div>
                </div>   
                    </td>
            </tr>
            </c:forEach>  
            </table>
            <%@include file="../../parts/paginator.jspf" %>
        </div>
    </div>
</div>
<%@include file="../../parts/footer.jspf" %>



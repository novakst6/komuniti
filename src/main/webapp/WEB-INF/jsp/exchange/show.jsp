<%-- 
    Document   : show
    Created on : 7.6.2012, 15:37:43
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="../../parts/exchangefilter.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Burza</h2>
            </div>
            <div class="pull-right">
                <form:form commandName="exchangeTextFilterForm" action="fulltext.htm" method="POST">
                    <form:input path="keywords" id="text_keywords" />
                    <button class="btn" type="submit" id="btn_fulltext_submit" >Hledej</button>
                </form:form>
            </div>
            <div class="pull-left" style="margin: 0em 0em 2em 0em;">               
                <a href="add.htm" class="btn btn-primary" id="link_add_offer">Vytvořit novou nabídku</a>                
            </div>

            <table class="table table-striped">

                <c:forEach items="${offers}" var="o" varStatus="offerStatus">
                    <tr>
                        <td>
                            <div class="row-fluid">
                                <div class="span4">
                                    <div><h4><a href="detail.htm?id=${o.id}" id="link_offer${o.id}">${o.title}</a></h4></div>
                                    <div><span>${o.author.googleName} </span><span class="badge badge-info">${o.author.email}</span></div>
                                </div>
                                <div class="span3">
                                    <c:forEach items="${o.tags}" var="tag" varStatus="tagStatus">
                                        <c:if test="${tagStatus.index < 2}">
                                            <span class="badge badge-inverse">${tag.name}</span>
                                        </c:if>
                                        <c:if test="${tagStatus.index == 2}">
                                            <a data-toggle="collapse" data-target="#all-tags${offerStatus.index}">další...</a><br />    
                                        </c:if>
                                    </c:forEach>
                                    <div class="collapse" id="all-tags${offerStatus.index}">
                                        <c:forEach items="${o.tags}" var="tag" begin="2">
                                            <span class="badge badge-inverse">${tag.name}</span>
                                        </c:forEach>
                                        <div>&nbsp;</div>
                                    </div>    
                                </div>
                                <div class="span2"><a href="<spring:url value="/catalog/detail.htm?id=${o.item.id}"/>" id="link_detail${o.id}}">${o.item.name}</a></div>
                                <div class="span1"><a class="btn btn-primary" href="response.htm?id=${o.id}" id="link_response${o.id}">Reagovat</a></div>
                                <c:if test="${userName == o.author.email}">
                                    <div class="span1"><a class="btn" href="edit.htm?id=${o.id}" id="link_edit${o.id}">Upravit</a></div>
                                    <div class="span1"><a class="btn btn-danger" href="end.htm?id=${o.id}" id="link_end${o.id}">Ukončit</a></div>
                                </c:if>
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



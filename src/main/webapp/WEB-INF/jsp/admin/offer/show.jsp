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
        <div class="span10">

            <div class="page-header">
                <h2>Administrace nabídek</h2>
            </div>

            <div class="pull-right">
                <form:form commandName="offerTextFilterForm" action="show.htm" method="POST">
                    <form:input path="keywords" id="text_keywords" />
                    <button class="btn" type="submit" id="btn_fulltext_submit" >Hledej</button>
                </form:form>
            </div>

            <div class="pull-left" style="margin: 0em 0em 2em 0em;">               
                <a href="add.htm" class="btn btn-primary">Vytvořit novou nabídku</a>                
            </div>

            <table class="table table-striped">
                <c:forEach items="${offers}" var="o" varStatus="offerStatus">
                    <tr>
                        <td>
                            <div class="span1">
                                <div style="margin-bottom: 5px;"><span class="badge">${o.id}</span></div>
                                <div>
                                    <c:if test="${o.active == false}">
                                        <span class="badge badge-important">NE</span>
                                    </c:if>
                                    <c:if test="${o.active == true}">
                                        <span class="badge badge-success">ANO</span>
                                    </c:if>
                                </div>
                            </div>
                            <div class="span5">
                                <div><a href="detail.htm?id=${o.id}">${o.title}</a></div>
                                <div><span class="badge badge-inverse">${o.author.email}</span> <span>${o.item.name}</span></div>                       
                            </div>
                            <div class="span4">
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
                            <div class="span2">
                                <div class="btn-group pull-left">
                                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                                        <i class="icon-cog"></i> 
                                        Další akce
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="edit.htm?id=${o.id}">Upravit</a></li>
                                        <li><a href="delete.htm?id=${o.id}">Smazat</a></li>
                                    </ul>
                                </div>
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



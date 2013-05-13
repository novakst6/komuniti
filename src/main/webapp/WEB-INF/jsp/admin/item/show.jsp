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
                <h2>Administrace věcí</h2>
            </div>


            <div class="pull-left" style="margin: 0em 0em 2em 0em;">               
                <a href="add.htm" class="btn btn-primary">Vytvořit nový profil zboží</a>               
            </div>
            <div class="pull-right">
                <form:form commandName="fullTextFilterForm" action="show.htm" method="POST">
                    <form:input path="keywords" id="text_keywords" />
                    <button class="btn" type="submit" id="btn_fulltext_submit" >Hledej</button>
                </form:form>
            </div>
            <table class="table table-striped">
                <c:forEach items="${items}" var="i" varStatus="itemStatus">
                    <tr>
                        <td>
                            <div class="row-fluid">
                                <div class="span1">
                                    <div style="margin-bottom: 5px;"><span class="badge">${i.id}</span></div>
                                    <div>
                                        <c:if test="${i.active == false}">
                                            <span class="badge badge-important">NE</span>
                                        </c:if>
                                        <c:if test="${i.active == true}">
                                            <span class="badge badge-success">ANO</span>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="span5"><h4><a href="detail.htm?id=${i.id}" id="link_item${itemStatus.index}">${i.name}</a></h4></div>
                                <div class="span4">
                                    <c:forEach items="${i.tags}" var="tag" varStatus="tagStatus">
                                        <c:if test="${tagStatus.index < 2}">
                                            <span class="badge badge-inverse">${tag.name}</span>
                                        </c:if>
                                        <c:if test="${tagStatus.index == 2}">
                                            <a data-toggle="collapse" data-target="#all-tags${itemStatus.index}">další...</a><br />    
                                        </c:if>
                                    </c:forEach>

                                    <div class="collapse" id="all-tags${itemStatus.index}">
                                        <c:forEach items="${i.tags}" var="tag" begin="2">
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
                                <li><a href="edit.htm?id=${i.id}">Upravit</a></li>
                                <li><a href="delete.htm?id=${i.id}">Smazat</a></li>
                            </ul>
                        </div>
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



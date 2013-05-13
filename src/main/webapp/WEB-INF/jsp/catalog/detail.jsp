<%-- 
    Document   : show
    Created on : 7.6.2012, 15:37:43
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@include file="../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="page-header">
        <h2>Detail zboží - ${item.name}</h2>
    </div>
    <div class="row-fluid">
        <div class="span7">
            <div class="well">
                <h3>Informace o nabízeném zboží</h3>
            </div>

            <div class="container-fluid">
                <div class="container-fluid">
                    <h4>Tagy:</h4>   
                    <div class="container-fluid">
                        <c:forEach items="${item.tags}" var="tag">
                            <span class="badge badge-inverse">${tag.name}</span>
                        </c:forEach>
                    </div>
                </div>
                <br />
                <div class="container-fluid">
                    <h4>Popis:</h4>
                    <div class="container-fluid">
                        <p>${item.description}</p>
                        <p>${item.text}</p>
                    </div>
                </div>
                <br />
                <div class="container-fluid">
                    <h4>Přílohy:</h4>
                    <table class="table table-striped">
                        <c:forEach items="${item.files}" var="file">
                            <tr>
                                <td>
                                    <div class="span9">
                                        Název: <a href="<spring:url value="/file/download.htm?id=${file.id}" />" target="_blank">${file.name}</a>
                                        <br />
                                        <c:if test="${file.description != ''}">
                                            Popis souboru: ${file.description}
                                        </c:if>
                                    </div>
                                    <div class="span3">
                                        Velikost: ${file.fileSize} bytů
                                    </div>
                                </td>
                            </tr>            
                        </c:forEach>
                    </table>
                </div>
                <br />
                <div class="container-fluid">
                    <h4>Náhledy obrázků:</h4>
                    <div id="gallery" data-toggle="modal-gallery" data-target="#modal-gallery">
                        <c:forEach items="${imgs}" var="im">
                            <a href="<spring:url value="/file/download.htm?id=${im.id}"/>" target="_blank">
                                <img src="<spring:url value="/file/download.htm?id=${im.id}"/>" style="width: 100px; height: 100px;" />
                            </a>
                        </c:forEach>
                    </div>
                </div>    
            </div>

        </div>
        <div class="span5">
            <div class="well">
                <h3>Aktivní nabídky k tomuto zboží</h3>
            </div>

            <div class="span11" style="overflow-y: auto; height: 60%;">
                <table class="table table-striped">
                    <c:forEach items="${activeOffers}" var="ao">
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="span8"><a href="<spring:url value="/exchange/detail.htm?id=${ao.id}" />" >${ao.title}</a></div>
                                    <div class="span3"><span class="badge">${ao.author.email}</span></div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>            
        <div class="row-fluid">            
            <div class="well">
                <h3>Komentáře</h3>
            </div>
        </div>
        <div class="row-fluid">

            <div class="span6">
                <div class="well">
                    <h4>Přidat komentář ke zboží</h4>
                    <form:form action="detail.htm" commandName="addFormModel" method="POST" >
                        <div class="control-group"> 
                            <form:label path="text" >Text komentáře:</form:label>
                            <form:textarea path="text" id="text_comment"/>
                            <form:errors path="text" cssClass="alert alert-error" element="div" />
                        </div>
                        <form:hidden path="id" />
                        <button type="submit" class="btn btn-primary" id="btn_comment_submit">Přidat komentář</button>
                    </form:form>
                </div>
                <c:forEach items="${item.comments}" var="c">
                    <table class="table table-striped">
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="span5"><b>Datum vložení:</b> ${c.dateOfInsert}</div>
                                    <div class="span5"><span><b>Autor:</b> ${c.author.googleName} </span><span class="label label-info"> ${c.author.email}</span></div>
                                    <sec:authorize ifAnyGranted="ROLE_ADMIN_CONTENT,ROLE_ADMIN" >
                                        <div class="span2"><a href="block.htm?id=${c.id}&amp;idArticle=${article.id}">Blokovat</a></div>
                                    </sec:authorize>
                                </div>
                                <div class="row-fluid">
                                    <div class="span12">
                                        <c:choose>
                                            <c:when test="${c.banned == true}">
                                                <i>Komentář byl smazán.</i>
                                            </c:when>
                                            <c:when test="${c.banned == false}">
                                                <p>${c.text}</p>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<%@include file="../../parts/footer.jspf" %>



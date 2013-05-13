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
        </div>
    </div>
</div>
<%@include file="../../../parts/footer.jspf" %>



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
        <div class="span12">
            <div class="page-header">
                <h2>Detail nabídky</h2>
            </div>

            <div class="row-fluid">
                <div class="span6">
                    <div class="well">
                        <h3>${offer.title}</h3>
                    </div>
                    <div class="container-fluid">
                        <h4>Kategorie:</h4>   
                        <div class="container-fluid">
                            <c:forEach items="${offer.tags}" var="tag">
                                <span class="badge badge-inverse">${tag.name}</span>
                            </c:forEach>
                        </div>
                    </div>
                    <br />
                    <div class="container-fluid">
                        <h4>Informace o nabídce:</h4>
                        <div class="container-fluid">
                            <p><b>Vloženo:</b> ${offer.insertDate}</p>
                            <p><b>Autor:</b> ${offer.author.email} </p>
                            <p>
                                <c:if test="${offer.item.active == true}">
                                    <b>Nabídka je aktivní</b>
                                </c:if>
                                <c:if test="${offer.item.active == true}">
                                    <b>Nabídka není aktivní</b>
                                </c:if>
                            </p>
                            <c:if test="${offer.edited == true}">
                                <p><b>Editováno dne:</b> ${offer.editedDate}</p> 
                                <p><b>Uživatelem:</b> ${offer.editedBy.email}</p>
                            </c:if>
                        </div>
                    </div> 
                    <br />
                    <div class="container-fluid">
                        <h4>Text nabídky:</h4>
                        <p>${offer.text}</p>
                    </div>
                    <br />
                    <div class="container-fluid">
                        <h4>Přílohy od uživatele:</h4>
                        <table class="table table-striped">
                            <c:forEach items="${offer.files}" var="file">
                                <tr>
                                    <td>
                                        <div class="span8">
                                            Název: <a href="<spring:url value="/file/download.htm?id=${file.id}" />" target="_blank">${file.name}</a>
                                        </div>
                                        <div class="span4">
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
                                <c:forEach items="${imgsO}" var="im">
                                    <a href="<spring:url value="/file/download.htm?id=${im.id}"/>" target="_blank">
                                        <img src="<spring:url value="/file/download.htm?id=${im.id}"/>" style="width: 100px; height: 100px;" />
                                    </a>    
                                </c:forEach>
                        </div>
                    </div> 
                </div>
                <div class="span5"> 
                    <div class="well">      
                        <h3>Nabízené zboží</h3> 
                    </div>
  
                    <div class="container-fluid">
                        <div class="container-fluid">
                            <h4>Název: </h4>
                            <div class="container-fluid">
                                <p>${offer.item.name}</p>
                            </div>
                        </div>
                        <div class="container-fluid">
                            <h4>Tagy:</h4>   
                            <div class="container-fluid">
                                <c:forEach items="${offer.item.tags}" var="tag">
                                    <span class="badge badge-inverse">${tag.name}</span>
                                </c:forEach>
                            </div>
                        </div>
                        <br />
                        <div class="container-fluid">
                            <h4>Popis:</h4>
                            <div class="container-fluid">
                                <p>${offer.item.description}</p>
                                <p>${offer.item.text}</p>
                            </div>
                        </div>
                        <br />
                        <div class="container-fluid">
                            <h4>Přílohy:</h4>
                            <table class="table table-striped">
                                <c:forEach items="${offer.item.files}" var="file">
                                    <tr>
                                        <td>
                                            <div class="span8">
                                                Název: <a href="<spring:url value="/file/download.htm?id=${file.id}" />" target="_blank">${file.name}</a>
                                            </div>
                                            <div class="span4">
                                                Velikost: ${file.fileSize} bytů
                                            </div>
                                        </td>
                                    </tr>            
                                </c:forEach>
                            </table>
                        </div>
                        <div class="container-fluid">
                            <h4>Náhledy obrázků:</h4>
                            <div>
                                <c:forEach items="${imgsI}" var="im">
                                    <a href="<spring:url value="/file/download.htm?id=${im.id}"/>" target="_blank">
                                        <img src="<spring:url value="/file/download.htm?id=${im.id}"/>" style="width: 100px; height: 100px;" />
                                    </a>    
                                </c:forEach>
                            </div>
                        </div>    

                    </div>
                </div>

            </div>  
        </div>
    </div>
</div>
<%@include file="../../parts/footer.jspf" %>



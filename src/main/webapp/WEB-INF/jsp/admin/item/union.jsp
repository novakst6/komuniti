<%-- 
    Document   : show
    Created on : 7.6.2012, 15:37:43
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../../../parts/header.jspf" %>
<script>
    function httpGet()
    {
        var keyword = document.getElementById("text_find_fulltext").value;
        var xmlHttp = null;
        xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", "itemsFilter.htm?keyword="+keyword, false );
        xmlHttp.send( null );
        return xmlHttp.responseText;
    }
    
function itemsFilter(divName){
        document.getElementById(divName).innerHTML = httpGet();
        document.getElementById('paginatorFulltext').style.visibility = 'hidden';
    }
</script>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="../../../parts/adminmenu.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Administrace zboží - sloučení duplicit</h2>
            </div>

            <div class="row-fluid">
                <div class="span6">
                    <div class="well">
                        <div class="span5"><h3>Zboží</h3></div>
                        <div class="span7">
                            <input type="text" id="text_find_fulltext" class="input-medium"/>
                            <a class="btn btn-navbar" onclick="itemsFilter('itemsDynamic')" id="link_find_item">Hledej</a>
                        </div>
                    </div>
                    <div id="itemsDynamic">       
                        <table class="table table-striped">
                            <c:forEach items="${items}" var="i">
                                <tr>
                                    <td>
                                        <div class="row-fluid">
                                            <div class="span1"><span class="badge">${i.id}</span></div>
                                            <div class="span8">${i.name}</div>
                                            <div class="span3"><a class="btn btn-navbar" href="unionAdd.htm?id=${i.id}">Přidej</a></div>
                                        </div>
                                    </td>                  
                                </tr>
                            </c:forEach>
                        </table> 
                    </div> 
                    <%-- vynechat při fulltext --%>
                    <div id="paginatorFulltext">
                    <%@include file="../../../parts/paginator.jspf" %>
                    </div>
                </div>
                <div class="span6">
                    <div class="well">
                        <div class="span12">
                        <h3>Vybrané zboží ke sloučení</h3>
                        </div>
                    </div>

                    <form:form commandName="unionForm" method="POST" action="union.htm">
                        <form:errors path="*" cssClass="alert alert-error" element="div"/>
                        <table class="table table-striped">
                            <c:forEach items="${selected}" var="i" varStatus="selectedStatus">
                                <tr>
                                    <td>
                                        <div class="row-fluid">
                                            <div class="span1"><form:radiobutton path="primaryId" value="${i.id}"/></div>
                                            <div class="span1"><span class="badge">${i.id}</span></div>
                                            <div class="span7">${i.name}</div>
                                            <div class="span3"><a href="itemDelete.htm?id=${i.id}">Odstranit</a></div>
                                        </div>
                                    </td>                           
                                </tr>
                            </c:forEach>
                        </table> 

                        <button type="submit" class="btn btn-primary">Sloučit</button>                
                    </form:form>
                </div>

            </div>
        </div>
    </div>
</div>
<%@include file="../../../parts/footer.jspf" %>



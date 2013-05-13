<%-- 
    Document   : show
    Created on : 2.8.2012, 15:37:43
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
                <h2>Administrace štítků</h2>
            </div>
            
             
            <div class="pull-left" style="margin: 0em 0em 2em 0em;">               
               <a href="add.htm" class="btn btn-primary">Vytvořit nový štítek</a>                
            </div>
            
                <c:forEach items="${tags}" var="tag" varStatus="tagStatus">
            <table class="table table-striped">
                    <tr>
                        <td>
                            <div class="row-fluid">
                                <div class="span1"><span class="badge">${tag[0].tag.id}</span></div>
                                <div class="span5"><a data-toggle="collapse" data-target="#all-tag${tagStatus.index}"><i class="icon-chevron-down"></i>${tag[0].tag.name}</a></div>
                                <div class="span2"><a class="btn btn-primary" href="add.htm?id=${tag[0].tag.id}">Přidat</a></div>
                                <div class="span2"><a class="btn btn-navbar" href="edit.htm?id=${tag[0].tag.id}">Upravit</a></div>
                                <div class="span2"><a class="btn btn-danger" href="delete.htm?id=${tag[0].tag.id}">Smazat</a></div>
                            </div>
                        </td>
                    </tr>    
            </table>
            <div class="collapse" id="all-tag${tagStatus.index}">  
            <table class="table table-striped">
                <c:forEach items="${tag}" var="t" begin="1">
                <tr>
                        <td>
                            <div class="row-fluid">
                                <div class="span1"><c:forEach begin="1" end="${t.index}"><i class="icon-chevron-right"></i></c:forEach></div>
                                <div class="span1"><span class="badge">${t.tag.id}</span></div>
                                <div class="span4"><c:forEach begin="1" end="${t.index}">&nbsp;&nbsp;&nbsp;&nbsp;</c:forEach>${t.tag.name}</div>
                                <div class="span2"><a class="btn btn-primary" href="add.htm?id=${t.tag.id}">Přidat</a></div>
                                <div class="span2"><a class="btn btn-navbar" href="edit.htm?id=${t.tag.id}">Upravit</a></div>
                                <div class="span2"><a class="btn btn-danger" href="delete.htm?id=${t.tag.id}">Smazat</a></div>
                            </div>
                        </td>
                    </tr> 
               </c:forEach> 
            </table>         
           </div> 
         </c:forEach>        
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>



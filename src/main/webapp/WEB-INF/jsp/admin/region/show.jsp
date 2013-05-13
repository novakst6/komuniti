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
                <h2>Administrace regionů</h2>
            </div>
            
             
            <div class="pull-left" style="margin: 0em 0em 2em 0em;">               
               <a href="add.htm" class="btn btn-primary">Vytvořit nový region</a>                
            </div>

            
             <c:forEach items="${regions}" var="region" varStatus="regStatus">
             <table class="table table-striped">
                    <tr>
                        <td>
                            <div class="row-fluid">
                                <div class="span1"><span class="badge">${region[0].region.id}</span></div>
                                <div class="span5"><a data-toggle="collapse" data-target="#all-reg${regStatus.index}"><i class="icon-chevron-down"></i>${region[0].region.name}</a></div>
                                <div class="span2"><a class="btn btn-primary" href="add.htm?id=${region[0].region.id}">Přidat</a></div>
                                <div class="span2"><a class="btn btn-navbar" href="edit.htm?id=${region[0].region.id}">Upravit</a></div>
                                <div class="span2"><a class="btn btn-danger" href="delete.htm?id=${region[0].region.id}">Smazat</a></div>
                            </div>
                        </td>
                    </tr>    
            </table>
            <div class="collapse" id="all-reg${regStatus.index}">
            <table class="table table-striped">        
                    <c:forEach items="${region}" var="r" begin="1">
                        <tr>
                        <td>
                            <div class="row-fluid">
                                <div class="span1"><c:forEach begin="1" end="${r.index}"><i class="icon-chevron-right"></i></c:forEach></div>
                                <div class="span1"><span class="badge">${r.region.id}</span></div>
                                <div class="span4"><c:forEach begin="1" end="${r.index}">&nbsp;&nbsp;&nbsp;&nbsp;</c:forEach>${r.region.name}</div>
                                <div class="span2"><a class="btn btn-primary" href="add.htm?id=${r.region.id}">Přidat</a></div>
                                <div class="span2"><a class="btn btn-navbar" href="edit.htm?id=${r.region.id}">Upravit</a></div>
                                <div class="span2"><a class="btn btn-danger" href="delete.htm?id=${r.region.id}">Smazat</a></div>
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



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
        <div class="span8">

            <div class="page-header">
                <h2>Detail centra</h2>
            </div>
            <div class="well">
            <h3>${center.name}</h3>
            </div>
            <br />
            <div class="container-fluid">
                <div class="row-fluid">
                    <span><b>Region:</b></span><span class="badge badge-inverse">${center.region.name}</span>
                </div>
                <br />
                <div class="row-fluid">
                    <span><b> Administrátor:</b></span>
                    <c:if test="${center.admin == null}">
                        <span>Administrátor není určen.</span>
                    </c:if>
                    <c:if test="${center.admin != null}">
                        <span class="badge badge-info">${center.admin.email}</span>
                    </c:if>                   
                </div>
                <br />
                <div class="row-fluid">
                    <b>Informace:</b>
                </div>
                <br />
                <div class="row-fluid">
                    ${center.info}
                </div>
            </div>
             
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>



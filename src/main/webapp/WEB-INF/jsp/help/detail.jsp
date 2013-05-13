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
        <div class="span10">
            <div class="page-header">
                <h2>Nápověda</h2>
            </div>
            <div class="well">
            <h3>${help.title}</h3>
            </div>
            <br />
            <div class="container-fluid">
                <div class="row"><b>Datum vložení: </b> ${help.insertDate}</div>
            </div>
            <br />
            <div class="container-fluid">
                <div class="well">
                <h4>Text:</h4>
                <p>${help.text}</p> 
                </div>
            </div>
          </div>  
        </div>
      </div>
        <%@include file="../../parts/footer.jspf" %>



<%-- 
    Document   : add
    Created on : 2.8.2012, 15:39:49
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="../../../parts/header.jspf" %>

<style type="text/css" media="all">
    <!--
    @import url("<spring:url value="/texteditor/css/widgEditor.css" />");
    -->
</style>
<script>
    var counter = 1;
    var limit = 3;
    function addInput(divName){
        if (counter == limit)  {
            alert("Dosáhli jste maximálního počtu " + counter + " souborů");
        }
        else {
            var newdiv = document.createElement('div');
            newdiv.innerHTML = "<div class=\"control-group\"><span>Popis souboru: </span><span><input id=\"descriptionOfFiles"+(counter + 1)+"\" name=\"descriptionOfFiles["+(counter + 1)+"]\" type=\"text\" value=\"\"/> <input id=\"files"+(counter + 1)+"\" name=\"files["+(counter + 1)+"]\" type=\"file\" value=\"\"/><span></div>";
            document.getElementById(divName).appendChild(newdiv);
            counter++;
        }
    }
</script>
<script src="<spring:url value="/texteditor/scripts/widgEditor.js" />"></script>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="../../../parts/adminmenu.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Přidání nového zboží</h2>
            </div>

            <form:form action="add.htm" method="POST" commandName="addFormModel" enctype="multipart/form-data">
                <div class="row-fluid">
                    <div class="span8">    

                        <form:errors path="*" cssClass="alert alert-error" element="div" />
                        <div class="control-group"> 
                            <form:label path="name" >Název zboží:</form:label>
                            <form:input path="name" cssClass="input-xxlarge"/>
                            <form:errors path="name" cssClass="alert alert-error" element="div" />
                        </div>
                        <div class="control-group">
                            <form:label path="description" >Popis zboží:</form:label>
                            <form:textarea path="description" cols="50" />
                            <form:errors path="description" cssClass="alert alert-error" element="div" />
                        </div>
                        <div class="control-group">

                            <form:label path="text" >Text:</form:label>
                            <form:textarea path="text" cssClass="widgEditor nothing" />
                            <form:errors path="text" cssClass="alert alert-error" element="div" />
                        </div>

                        <div class="well">
                            <h4>Soubory</h4>
                        </div>
                        <div id="dynamicFiles">
                            <div class="control-group">
                                <span>
                                    Popis souboru:                                                      
                                </span>
                                <span>
                                    <form:input path="descriptionOfFiles[0]" />  
                                    <form:input path="files[0]" type="file" />                                      
                                </span>
                            </div> 
                        </div>
                                <div class="control-group">        
                            <a class="btn btn-navbar" onclick="addInput('dynamicFiles')" >Přidat soubor</a>                       
                        </div>
                                <br />
                        <div class="well alert-info">
                            <form:label path="active" >
                                <form:checkbox path="active" /> Přidat zboží do nabídky
                            </form:label>
                            <form:errors path="active" cssClass="alert alert-error" element="div" />
                        </div>
                    </div>
                    <div class="span4">
                        <!-- Tagy -->
                        <div class="well">
                            <h3>Štítky</h3>
                        </div>
                        <ul class="nav nav-tabs nav-stacked">
                            <c:forEach items="${tags}" var="tag" varStatus="tagStatus">
                                <li>
                                    <div>
                                        <span><form:checkbox path="tags" value="${tag[0].tag.id}"/>&nbsp;</span><span><a data-toggle="collapse" data-target="#tags-item${tagStatus.index}"><i class="icon-chevron-down"></i>${tag[0].tag.name}</a></span>
                                    </div>
                                </li>
                                <div class="collapse" id="tags-item${tagStatus.index}">
                                    <c:forEach items="${tag}" var="t" begin="1">
                                        <li>
                                            <div>
                                                <span><c:forEach begin="1" end="${t.index}">&nbsp;&nbsp;&nbsp;&nbsp;</c:forEach></span>
                                                <span><form:checkbox path="tags" value="${t.tag.id}"/>&nbsp;</span><span>${t.tag.name}</span>
                                            </div>
                                        </li>                                    
                                    </c:forEach>
                                </div>                 
                            </c:forEach>     
                        </ul>
                    </div>      
                </div>
                <button class="btn btn-primary">Přidat</button>

            </form:form>    
        </div>

    </div>
</div>

<%@include file="../../../parts/footer.jspf" %>



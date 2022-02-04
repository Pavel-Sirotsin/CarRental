<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/taglib.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="locale">

<fmt:message key="title.registration.form" var="titleRegForm"/>
<fmt:message key="label.contact" var="contact"/>
<fmt:message key="label.home" var="home"/>
<fmt:message key="label.copr" var="copr"/>

<fmt:message key="label.submit" var="submit"/>
<fmt:message key="label.reset" var="reset"/>
<fmt:message key="label.username" var="userName"/>
<fmt:message key="label.password" var="password"/>
<fmt:message key="label.show.password" var="show"/>
<fmt:message key="label.notreg" var="notreg"/>
<fmt:message key="label.lang" var="lang"/>
<fmt:message key="info.registration.form" var="info"/>
<fmt:message key="label.first.name" var="firstName"/>
<fmt:message key="label.last.name" var="lastName"/>
<fmt:message key="label.phone" var="phone"/>
<fmt:message key="label.license" var="license"/>
<fmt:message key="label.marked" var="marked"/>

<fmt:message key="command.registration.ref" var="regRef"/>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="icon" href="img/icon.png"/>
    <title>${titleRegForm}</title>
</head>

<body style="background-image: url(img/background_reg.jpg); background-size: cover; background-attachment: fixed;">

    <!-- Header -->

    <header class="navbar navbar-light bg-secondary bg-gradient p-1">
        <nav class="container-fluid gap-2 d-flex justify-content-between">

            <div class="col-md-auto col-lg-auto">
                <a class="navbar-brand" href="controller?command=home">
                    <img src="img/logo-rentacar.svg" alt="logo image" width="100" height="30" class="d-inline-block align-text-top">
                </a>
            </div>

            <ul class="nav nav-tabs col-md-auto col-lg-auto">
                <li class="nav-item">
                    <a class="nav-link text-white" href="controller?command=home">${home}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="controller?command=contact">${contact}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="controller?command=faq">FAQ</a>
                </li>
            </ul>

            <div class="col-md-auto col-lg-auto">
                <a href=${regRef} class="btn btn-success" aria-current="page">${lang}</a>
            </div>

        </nav>
    </header>

    <!-- Alert -->
    <c:if test="${not empty registrationResult}">
         <div id="resultToast" class="text-center position-fixed start-50 translate-middle-x p-2 w-50 alert alert-warning"aria-live="polite" aria-atomic="true" role="alert" data-bs-delay="3000" style="z-index: 10;">
              <fmt:message key="${registrationResult}"/>
         </div>
    </c:if>

    <!-- Info label (top fixed) -->
    <div class="d-flex justify-content-center mb-4">
        <i class=" text-center d-block bg-primary bg-gradient text-white rounded-bottom" style="width: 20rem;">
            ${info}
        </i>
    </div>

    <!-- Main form container -->
    <div class="container text-white col-lg-7 p-2 mt-5 mb-5 bg-secondary bg-gradient border-start border-5 border-warning rounded">
        <form class="row g-2" action="controller" method="POST">
        <input type="hidden" name="command" value="signUp"/>
            <div class="col-md-4">
                <label for="name" class="form-label">${firstName} <span class="text-warning">*</span></label>
                <input id="name" class="form-control" type="text" name="firstName" placeholder="Billy" value="" maxlength="32" required>
            </div>
            <div class="col-md-4">
                <label for="surname" class="form-label">${lastName} <span class="text-warning">*</span></label>
                <input id="surname" class="form-control" type="text" name="lastName" placeholder="Idol" value="" maxlength="32" required>
            </div>
            <div class="col-md-4">
                <label for="email" class="form-label">E-mail: <span class="text-warning">*</span></label>
                <div class="input-group">
                    <span class="input-group-text">@</span>
                    <input id="email" class="form-control" type="email" name="email" placeholder="e.g.:newclient@gmail.com" value="" maxlength="32" required>
                </div>
            </div><br>
            <div class="col-md-4">
                <label for="phone" class="form-label">${phone} <span class="text-warning">*</span></label>
                <input id="phone" class="form-control" type="tel" name="phone" placeholder="e.g.:+375296535569" value="" maxlength="32" required>
            </div>
            <div class="col-md-4">
                <label for="license" class="form-label">${license} <span class="text-warning">*</span></label>
                <input id="license" class="form-control" type="text" name="license" placeholder="e.g.:MB125698L54" value="" maxlength="32" required>
            </div>

            <hr class="mt-3 text-warning" style="height: 3px;">

            <div class="col-md-4">
                <label for="login" class="form-label">${userName} <span class="text-warning">*</span></label>
                <input id="login" class="form-control" type="text" name="userName" value="" maxlength="32" required>
            </div>
            <div class="col-md-4">
                <label for="password" class="form-label">${password} <span class="text-warning">*</span></label>
                <input id="password" class="form-control" type="password" name="userPassword" value="" maxlength="32" required>
            </div>
            <div class="col form-check form-switch text-warning">
                <input id="switch" class="form-check-input" type="checkbox" role="switch" onclick="myFunction()">
                <label class="form-check-label" for="switch">${show}</label>
            </div>

            <div class="mt-2">
                <button class="btn btn-warning " type="reset">${reset}</button>
                <button class="btn btn-success" type="submit">${submit}</button>
            </div>
            <p>${marked} <span class="text-warning">(*)</span></p>
        </form>
    </div>

    <!-- Footer -->

    <footer class="container-fluid border-top fixed-bottom d-flex flex-wrap justify-content-between align-items-center py-1 bg-secondary bg-gradient ">
        <div class="col-md-4 d-flex align-items-center ">
            <span class="text-white ">${copr}</span>
        </div>
        <ul class="nav col-md-4 justify-content-end list-unstyled d-flex ">
            <li class="ms-3 ">
                <a target="_blank " href="https://twitter.com/epam_belarus?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor ">
                    <img class="bi " width="24 " height="24 " src="img/twitter.png " />
                </a>
            </li>
            <li class="ms-3 ">
                <a target="_blank " href="https://www.instagram.com/epam.belarus/?hl=ru ">
                    <img width="24 " height="24 " src="img/instagram.png " />
                </a>
            </li>
            <li class="ms-3 ">
                <a target="_blank " href="https://www.facebook.com/EPAM.Belarus/ ">
                    <img class="bi " width="24 " height="24 " src="img/facebook.png " />
                </a>
            </li>
        </ul>
    </footer>

</body>
</fmt:bundle>
<script src="js/bootstrap.bundle.js "></script>
<script src="js/toogle.js "></script>
<script src="js/toast.js "></script>
</html>
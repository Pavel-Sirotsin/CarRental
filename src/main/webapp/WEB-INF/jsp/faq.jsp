<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="locale">

<fmt:message key="label.title.faq" var="titleFAQ"/>
<fmt:message key="label.how" var="how"/>
<fmt:message key="label.pay" var="pay"/>
<fmt:message key="label.where" var="where"/>
<fmt:message key="label.points" var="points"/>
<fmt:message key="label.home" var="home"/>
<fmt:message key="label.contact" var="contact"/>
<fmt:message key="label.copr" var="copr"/>
<fmt:message key="label.submit" var="submit"/>
<fmt:message key="label.username" var="username"/>
<fmt:message key="label.password" var="password"/>
<fmt:message key="label.show.password" var="show"/>
<fmt:message key="label.notreg" var="notreg"/>
<fmt:message key="label.login" var="login"/>
<fmt:message key="label.logout" var="logout"/>
<fmt:message key="label.lang" var="lang"/>

<fmt:message key="command.faq.ref" var="faqRef"/>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="icon" href="img/icon.png"/>
    <title>${titleFAQ}</title>
</head>

<body style="background-image: url(img/background_faq.jpg); background-size: cover; background-attachment: fixed;">

    <!-- Header -->

    <header class="navbar navbar-light bg-secondary bg-gradient p-1">
        <nav class="container-fluid gap-2 d-flex justify-content-xs-between justify-content-lg-between">

            <div class="col-md-auto col-lg-auto">
                <a class="navbar-brand" href="controller?command=home">
                    <img src="img\logo-rentacar.svg" alt="logo image" width="100" height="30" class="d-inline-block align-text-top">
                </a>
            </div>

            <ul class="nav nav-tabs col-md-auto col-lg-auto">
                <li class="nav-item">
                    <a class="nav-link text-white" href="controller?command=home" >${home}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="controller?command=contact">${contact}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active text-danger" aria-current="page" >FAQ</a>
                </li>
            </ul>

            <c:choose>
            <c:when test="${empty sessionUser}">
            <div class="btn-group gap-2 col-md-auto col-lg-auto">
                <a href=${faqRef} class="btn btn-success rounded" aria-current="page">${lang}</a>

                <button class="btn btn-warning rounded" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
                ${login}
                </button>

                <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
                    <div class="offcanvas-header">
                        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                    </div>

                    <div class="offcanvas-body bg-info" style="background-image: url(img/sign_up_back.jpg); background-position: center;">

                        <div class="row g-3 d-flex justify-content-center">

                            <form class="row g-3 d-flex justify-content-center" action="controller" method="POST">
                            <input type="hidden" name="command" value="logIn"/>
                            <input type="hidden" name="page" value="faq"/>
                            <div class="col-12 ">
                                <input type="text" class="form-control" name="userLogin" value="" placeholder=${username} maxlength="32" required>
                            </div>
                            <div class="col-12 ">
                                <input id="password" type="password" class="form-control" name="userPassword" value="" placeholder=${password} maxlength="32" required>
                            </div>
                            <div class="d-flex justify-content-center text-black form-check form-switch gap-5">
                                <label class="form-check-label" for="switch">${show}</label>
                                <input id="switch" class="form-check-input" type="checkbox" role="switch" onclick="myFunction()">
                            </div>
                            <button class="btn btn-success w-75" type="submit">${submit}</button>
                            </form>

                            <a class="link-dark fst-italic bg-warning rounded text-center w-75" aria-current="page " href="controller?command=regForm">
                               ${notreg}</a>
                        </div>
                    </div>
                </div>
            </div>
            </c:when>

            <c:when test="${not empty sessionUser}">
            <div class="btn-group gap-2 col-md-auto col-lg-auto">
                <a href=${faqRef} class="btn btn-success rounded" aria-current="page ">${lang}</a>
                <a href="controller?command=profile" class="btn btn-info rounded d-flex justify-content-center" aria-current="page ">
                <img src="img/person-lines-fill.svg" alt="cabinet image">
                </a>
                <a href="controller?command=logOut" class="btn btn-warning rounded" aria-current="page ">${logout}</a>
            </div>
            </c:when>
            </c:choose>

        </nav>
    </header>

    <!-- Authorization alert  -->
    <c:if test="${not empty authorizationResult}">
          <div id="resultToast" class="text-center position-fixed start-50 translate-middle-x p-2 w-50 alert alert-success" role="alert" data-bs-delay="3000" style="z-index: 10;">
              <fmt:message key="${authorizationResult}"/>
         </div>
    </c:if>

    <div class="container col-lg-6 mt-5 mb-5 bg-secondary bg-gradient border-start border-5 border-info rounded">
      <div class="row p-3">
         <ul class="text-white col-xs-auto p-3">
            <li>${how}</li>
            <li>${pay}</li>
            <li>${where}</li>
            <li>
              <a class="link-warning" href="https://www.google.ru/maps/search/airports/@51.4390337,4.8919116,8.78z" target="_blank">
              ${points}
              </a>
            </li>
         </ul>
      </div>
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
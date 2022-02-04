<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/taglib.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="locale">

<fmt:message key="label.title.home" var="titleHome"/>
<fmt:message key="label.home" var="home"/>
<fmt:message key="label.contact" var="contact"/>
<fmt:message key="label.copr" var="copr"/>
<fmt:message key="label.booking" var="booking"/>
<fmt:message key="label.submit" var="submit"/>
<fmt:message key="label.username" var="username"/>
<fmt:message key="label.password" var="password"/>
<fmt:message key="label.show.password" var="show"/>
<fmt:message key="label.notreg" var="notreg"/>
<fmt:message key="label.login" var="login"/>
<fmt:message key="label.logout" var="logout"/>
<fmt:message key="label.lang" var="lang"/>
<fmt:message key="label.sign" var="sign"/>

<fmt:message key="command.home.ref" var="homeRef"/>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="icon" href="img/icon.png"/>
    <title>${titleHome}</title>
</head>

<body style="background-image: url(img/background_home.jpg); background-size: cover; background-attachment: fixed;">

    <!-- Header -->
    <header class="navbar navbar-light bg-secondary bg-gradient p-1 sticky-top">
        <nav class="container-fluid gap-2 d-flex justify-content-between">

            <div class="col-md-auto col-lg-auto">
                <a class="navbar-brand">
                    <img src="img/logo-rentacar.svg" alt="logo image" width="100" height="30" class="d-inline-block align-text-top">
                </a>
            </div>

            <ul class="nav nav-tabs col-md-auto col-lg-auto">
                <li class="nav-item">
                    <a class="nav-link active text-danger" aria-current="page">${home}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="controller?command=contact">${contact}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="controller?command=faq">FAQ</a>
                </li>
            </ul>

            <c:choose>
            <c:when test="${empty sessionUser}">
            <div class="btn-group gap-2 col-md-auto col-lg-auto">
                <a href=${homeRef} class="btn btn-success rounded" aria-current="page">${lang}</a>

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
                            <input type="hidden" name="page" value="home"/>
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
                <a href=${homeRef} class="btn btn-success rounded" aria-current="page ">${lang}</a>
                <a href="controller?command=profile" class="btn btn-info rounded d-flex justify-content-center" aria-current="page ">
                <img src="img/person-lines-fill.svg" alt="cabinet image">
                </a>
                <a href="controller?command=logOut" class="btn btn-warning rounded" aria-current="page ">${logout}</a>
            </div>
            </c:when>
            </c:choose>

        </nav>
    </header>

    <!-- Registration alert  -->
    <c:if test="${not empty registrationResult}">
          <div id="resultToast" class="text-center position-fixed start-50 translate-middle-x p-2 w-50 alert alert-success" role="alert" data-bs-delay="3000" style="z-index: 10;">
              <fmt:message key="${registrationResult}"/>
         </div>
         <c:remove var="registrationResult" scope="session"/>
    </c:if>

    <!-- Authorization alert  -->
    <c:if test="${not empty authorizationResult}">
          <div id="resultToast" class="text-center position-fixed start-50 translate-middle-x p-2 w-50 alert alert-success" role="alert" data-bs-delay="3000" style="z-index: 10;">
              <fmt:message key="${authorizationResult}"/>
          </div>
          <c:remove var="authorizationResult" scope="session"/>
    </c:if>

    <!-- Payment alert  -->
        <c:if test="${not empty paymentResult}">
              <div id="resultToast" class="text-center position-fixed start-50 translate-middle-x p-2 w-50 alert alert-success" role="alert" data-bs-delay="3000" style="z-index: 10;">
                  <fmt:message key="${paymentResult}"/>
              </div>
              <c:remove var="paymentResult" scope="session"/>
        </c:if>

    <!-- Cars table -->
    <div class="container mb-3 p-3 mt-5 d-flex justify-content-center">
        <div class="row">

            <!-- Nissan micra -->
            <div class="col-xs-auto col-sm-auto col-md-auto col-lg-6 col-xl-4 p-0">
                <div class="card shadow p-2 mb-4 bg-body rounded " style="width: 23rem; height: 25rem; ">
                    <img src=<ctg:info>image=1</ctg:info> class="card-img-top" alt="car image">
                    <div class="card-body">
                        <h5 class="card-title"><ctg:info>name=1</ctg:info></h5>
                        <div class="row">
                            <div class="col-7">
                                <ctg:info language="${language}">description=1</ctg:info>
                            </div>
                            <div class="col fs-2 text-center p-1">
                                <div class="col text-center border rounded-pill bg-warning fw-light">
                                <ctg:info language="${language}">price=1</ctg:info><text class="fs-6">${sign}</text>
                                </div>
                                <a class="col-9 btn btn-success mt-3" href="controller?command=bookingForm&carId=1">${booking}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Toyta Yaris -->
            <div class="col-xs-auto col-sm-auto col-md-auto col-lg-6 col-xl-4 p-0">
                <div class="card shadow p-2 mb-4 bg-body rounded " style="width: 23rem; height: 25rem; ">
                    <img src=<ctg:info>image=2</ctg:info> class="card-img-top" alt="car image">
                    <div class="card-body">
                        <h5 class="card-title "><ctg:info>name=2</ctg:info></h5>
                        <div class="row">
                            <div class="col-7">
                                <ctg:info language="${language}">description=2</ctg:info>
                            </div>
                            <div class="col fs-2 text-center p-1">
                                <div class="col text-center border rounded-pill bg-warning fw-light">
                                <ctg:info language="${language}">price=2</ctg:info><text class="fs-6">${sign}</text>
                                </div>
                                <a class="col-9 btn btn-success mt-3" href="controller?command=bookingForm&carId=2">${booking}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Toyota corola -->
            <div class="col-xs-auto col-sm-auto col-md-auto col-lg-6 col-xl-4 p-0">
                <div class="card shadow p-2 mb-4 bg-body rounded " style="width: 23rem; height: 25rem; ">
                    <img src=<ctg:info>image=3</ctg:info> class="card-img-top " alt="car image ">
                    <div class="card-body ">
                        <h5 class="card-title "><ctg:info>name=3</ctg:info></h5>
                        <div class="row">
                            <div class="col-7">
                                <ctg:info language="${language}">description=3</ctg:info>
                            </div>
                            <div class="col fs-2 text-center p-1">
                                <div class="col text-center border rounded-pill bg-warning fw-light">
                                <ctg:info language="${language}">price=3</ctg:info><text class="fs-6">${sign}</text>
                                </div>
                                <a class="col-9 btn btn-success mt-3" href="controller?command=bookingForm&carId=3">${booking}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Toyota camry -->
            <div class="col-xs-auto col-sm-auto col-md-auto col-lg-6 col-xl-4 p-0">
                <div class="card shadow p-2 mb-4 bg-body rounded " style="width: 23rem; height: 25rem; ">
                    <img src=<ctg:info>image=4</ctg:info> class="card-img-top " alt="car image ">
                    <div class="card-body ">
                        <h5 class="card-title "><ctg:info>name=4</ctg:info></h5>
                        <div class="row">
                            <div class="col-7">
                            <ctg:info language="${language}">description=4</ctg:info>
                            </div>
                            <div class="col fs-2 text-center p-1">
                                <div class="col text-center border rounded-pill bg-warning fw-light">
                                <ctg:info language="${language}">price=4</ctg:info><text class="fs-6">${sign}</text>
                                </div>
                                <a class="col-9 btn btn-success mt-3" href="controller?command=bookingForm&carId=4">${booking}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Suzuki jimny -->
            <div class="col-xs-auto col-sm-auto col-md-auto col-lg-6 col-xl-4 p-0">
                <div class="card shadow p-2 mb-4 bg-body rounded " style="width: 23rem; height: 25rem; ">
                    <img src=<ctg:info>image=5</ctg:info> class="card-img-top " alt="car image ">
                    <div class="card-body ">
                        <h5 class="card-title "><ctg:info>name=5</ctg:info></h5>
                        <div class="row">
                            <div class="col-7">
                                <ctg:info language="${language}">description=5</ctg:info>
                            </div>
                            <div class="col fs-2 text-center p-1">
                                <div class="col text-center border rounded-pill bg-warning fw-light">
                                <ctg:info language="${language}">price=5</ctg:info><text class="fs-6">${sign}</text>
                                </div>
                                <a class="col-9 btn btn-success mt-3" href="controller?command=bookingForm&carId=5">${booking}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Hyundai creta -->
            <div class="col-xs-auto col-sm-auto col-md-auto col-lg-6 col-xl-4 p-0">
                <div class="card shadow p-2 mb-4 bg-body rounded " style="width: 23rem; height: 25rem; ">
                    <img src=<ctg:info>image=6</ctg:info> class="card-img-top " alt="car image " />
                    <div class="card-body ">
                        <h5 class="card-title "><ctg:info>name=6</ctg:info></h5>
                        <div class="row">
                            <div class="col-7">
                                <ctg:info language="${language}">description=6</ctg:info>
                            </div>
                            <div class="col fs-2 text-center p-1">
                                <div class="col text-center border rounded-pill bg-warning fw-light">
                                <ctg:info language="${language}">price=6</ctg:info><text class="fs-6">${sign}</text>
                                </div>
                                <a class="col-9 btn btn-success mt-3" href="controller?command=bookingForm&carId=6">${booking}</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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
                    <img class="bi " width="24 " height="24 " src="img/twitter.png" />
                </a>
            </li>
            <li class="ms-3 ">
                <a target="_blank " href="https://www.instagram.com/epam.belarus/?hl=ru">
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
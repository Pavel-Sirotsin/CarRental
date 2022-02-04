<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/taglib.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:if test="${empty car}">
<c:redirect url="index.jsp"/>
</c:if>

<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="locale">

<fmt:message key="title.booking.form" var="titleBookingForm"/>
<fmt:message key="label.home" var="home"/>
<fmt:message key="label.contact" var="contact"/>
<fmt:message key="label.copr" var="copr"/>
<fmt:message key="label.booking" var="booking"/>
<fmt:message key="label.submit" var="submit"/>
<fmt:message key="label.reset" var="reset"/>
<fmt:message key="label.username" var="username"/>
<fmt:message key="label.password" var="password"/>
<fmt:message key="label.first.name" var="firstName"/>
<fmt:message key="label.last.name" var="lastName"/>
<fmt:message key="label.phone" var="phone"/>
<fmt:message key="label.license" var="license"/>
<fmt:message key="booking.form.rental.date" var="rentalDate"/>
<fmt:message key="booking.form.return.date" var="returnDate"/>
<fmt:message key="booking.form.rental.location" var="rentalPlace"/>
<fmt:message key="booking.form.return.location" var="returnPlace"/>
<fmt:message key="booking.form.office" var="office"/>
<fmt:message key="booking.form.bus" var="bus"/>
<fmt:message key="booking.form.railway" var="railway"/>
<fmt:message key="booking.form.airport" var="airport"/>
<fmt:message key="booking.form.another" var="another"/>
<fmt:message key="booking.form.pay.now" var="payNow"/>
<fmt:message key="booking.form.pay.later" var="payLater"/>
<fmt:message key="label.sign" var="sign"/>
<fmt:message key="booking.form.insurance" var="insurance"/>

<fmt:message key="label.show.password" var="show"/>
<fmt:message key="label.notreg" var="notreg"/>
<fmt:message key="label.login" var="login"/>
<fmt:message key="label.logout" var="logout"/>
<fmt:message key="label.lang" var="lang"/>
<fmt:message key="label.marked" var="marked"/>

<fmt:message key="info.booking.form" var="info"/>

<fmt:message key="command.booking.form.ref" var="bookingRef"/>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="icon" href="img/icon.png"/>
    <title>${titleBookingForm}</title>
</head>

<body style="background-image: url(img/background_home.jpg); background-size: cover; background-attachment: fixed;">

    <!-- Header -->

    <header class="navbar navbar-light bg-secondary bg-gradient p-1">
        <nav class="container-fluid gap-2 d-flex justify-content-between">

            <div class="col-sm-auto col-md-auto col-lg-auto">
                <a class="navbar-brand" href="controller?command=home">
                    <img src="img/logo-rentacar.svg" alt="logo image" width="100" height="30" class="d-inline-block align-text-top">
                </a>
            </div>

            <ul class="nav nav-tabs col-sm-auto col-md-auto col-lg-auto">
                <li class="nav-item">
                    <a class="nav-link text-white" aria-current="page" href="controller?command=home">${home}</a>
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
            <div class="btn-group gap-2 col-sm-auto col-md-auto col-lg-auto">
                <a href=${bookingRef} class="btn btn-success rounded" aria-current="page">${lang}</a>

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
                            <div class="col-12">
                                <input type="text" class="form-control" name="userLogin" value="" placeholder=${username} maxlength="32" required>
                            </div>
                            <div class="col-12">
                                <input id="password" type="password" class="form-control" name="userPassword" value="" placeholder=${password} maxlength="32" required>
                            </div>
                            <div class="d-flex justify-content-center text-black form-check form-switch gap-5">
                                <label class="form-check-label" for="switch">${show}</label>
                                <input id="switch" class="form-check-input" type="checkbox" role="switch" onclick="myFunction()">
                            </div>
                            <button class="btn btn-success w-75" type="submit">${submit}</button>
                            </form>

                            <a class="link-dark fst-italic bg-warning rounded text-center w-75" aria-current="page" href="controller?command=regForm">
                               ${notreg}</a>
                        </div>
                    </div>
                </div>
            </div>
            </c:when>

            <c:when test="${not empty sessionUser}">
            <div class="btn-group gap-2 col-md-auto col-lg-auto">
                <a href=${bookingRef} class="btn btn-success rounded" aria-current="page">${lang}</a>
                <a href="controller?command=profile" class="btn btn-info rounded d-flex justify-content-center" aria-current="page">
                <img src="img/person-lines-fill.svg" alt="cabinet image">
                </a>
                <a href="controller?command=logOut" class="btn btn-warning rounded" aria-current="page">${logout}</a>
            </div>
            </c:when>
            </c:choose>

        </nav>
    </header>

    <!-- Alert -->
    <c:if test="${not empty bookingResult}">
         <div id="resultToast" class="text-center position-fixed start-50 translate-middle-x p-2 w-50 alert alert-warning"aria-live="polite" aria-atomic="true" role="alert" data-bs-delay="4000" style="z-index: 10;">
              <fmt:message key="${bookingResult}"/>
         </div>
         <c:remove var="bookingResult" scope="page"/>
    </c:if>

    <!-- Info label (top fixed) -->
    <div class="d-flex justify-content-center mb-4">
        <i class=" text-center d-block bg-success bg-gradient text-white rounded-bottom" style="width: 20rem;">
            ${info}
        </i>
    </div>

<!-- Main form container -->
    <div class="container p-2 mb-5 col-lg-8 bg-secondary bg-gradient border-start border-5 border-success rounded">
        <div class="row gap-2 justify-content-center">

            <!-- Booking info -->
            <form class="row col-md-auto col-lg-auto col-xl-6 " action="controller" method="POST">
            <input type="hidden" name="command" value="book"/>
                <div class="col-sm-6">
                    <label for="takeDate" class="form-label">${rentalDate}<span class="text-warning">*</span></label>
                    <input type="datetime-local" class="form-control" id="takeDate" name="rentalDate" value="" maxlength="32" required>
                </div>
                <div class="col-sm-6">
                    <label for="returnDate" class="form-label">${returnDate}<span class="text-warning">*</span></label>
                    <input type="datetime-local" class="form-control" id="returnDate" name="returnDate" value="" maxlength="32" required>
                </div>
                <div class="col-sm-6">
                    <label for="takePlace" class="form-label">${rentalPlace}</label>
                    <select class="form-select" id="takePlace" name="rentalLocation">
                       <option value="${office}" selected>${office}</option>
                       <option value="${bus}">${bus}</option>
                       <option value="${railway}">${railway}</option>
                       <option value="${airport}">${airport}</option>
                       <option value="${another}">${another}</option>
                    </select>
                </div>
                <div class="col-sm-6">
                    <label for="returnPlace" class="form-label">${returnPlace}</label>
                    <select class="form-select" id="returnPlace" name="returnLocation">
                    <option value="${office}" selected>${office}</option>
                    <option value="${bus}">${bus}</option>
                    <option value="${railway}">${railway}</option>
                    <option value="${airport}">${airport}</option>
                    <option value="${another}">${another}</option>
                </select>
                </div>

                <div class="col-sm-6">
                    <label for="name" class="form-label">${firstName}<span class="text-warning">*</span></label>
                    <input type="text" class="form-control" id="name" maxlength="32" name="firstName" placeholder="Billy" value="${sessionUser.account.name}" required>
                </div>
                <div class="col-sm-6">
                    <label for="surname" class="form-label">${lastName}<span class="text-warning">*</span></label>
                    <input type="text" class="form-control" id="surname" maxlength="32" name="lastName" placeholder="Idol" value="${sessionUser.account.surname}" required>
                </div>
                <div class="col-sm-6">
                    <label for="phone" class="form-label">${phone}<span class="text-warning">*</span></label>
                    <input type="tel" class="form-control" id="phone" maxlength="32" name="phone" placeholder="e.g.:+375296535569" value="${sessionUser.account.phoneNumber}" required>
                </div>
                <div class="col-sm-6">
                    <label for="license" class="form-label">${license}<span class="text-warning">*</span></label>
                    <input type="text" class="form-control" id="license" maxlength="32" name="license" placeholder="e.g.:MB125698L54" value="${sessionUser.account.drivingLicense}" required>
                </div>

                <div class="col-sm-12">
                <label for="email" class="form-label">E-mail: <span class="text-warning">*</span></label>
                <div class="input-group">
                    <span class="input-group-text">@</span>
                    <input id="email" class="form-control" type="email" name="email" placeholder="e.g.:newclient@gmail.com" value="${sessionUser.account.email}" maxlength="32" required>
                </div>
                </div>

                 <c:choose>
                    <c:when test="${not empty sessionUser}">
                       <div class="col btn-group gap-3 mt-3">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="payment" value="now" id="flexRadioDefault1" checked>
                                <label class="form-check-label" for="flexRadioDefault1">${payNow}</label>
                            </div>
                            <div class="form-check">
                                 <input class="form-check-input" type="radio" name="payment" value="later" id="flexRadioDefault2">
                                 <label class="form-check-label" for="flexRadioDefault2">${payLater}</label>
                            </div>
                       </div>
                       </c:when>
                       <c:otherwise>
                       <div class="col btn-group gap-3 mt-3">
                           <div class="form-check">
                                <input class="form-check-input" type="radio" name="payment" value="now" id="flexRadioDefault1" checked>
                                <label class="form-check-label" for="flexRadioDefault1">${payNow}</label>
                           </div>
                       </div>
                    </c:otherwise>
                 </c:choose>

                <div class="mt-2">
                    <hr class="mt-3 text-white" style="height: 3px;">
                    <div class="col btn-group gap-1">
                        <button class="btn btn-warning" type="reset">${reset}</button>
                        <button class="btn btn-success" type="submit">${booking}</button>
                    </div>
                </div>

                <p class="mt-3">${marked}<span class="text-warning">*</span></p>
            </form>

            <!-- Car info -->

            <div class="col-md-auto col-lg-auto col-xl-6 d-flex justify-content-center">
                <div class="card shadow p-1 mb-5 bg-body rounded" style="width: 30rem; height: 30rem;">
                    <img src=<ctg:info>image=${car.id}</ctg:info> class="card-img-top" alt="car image">
                    <div class="card-body">
                        <h5 class="card-title text-center"><ctg:info>name=${car.id}</ctg:info></h5>
                        <div class="row">
                            <div class="col">
                                <ctg:info language="${language}">description=${car.id}</ctg:info>
                            </div>
                            <div class="col fs-3 text-center">
                                <span class="fs-6 top-75 start-25 badge rounded-pill bg-danger">
                                ${insurance}<ctg:info language="${language}">insurancePrice=${car.id}</ctg:info><text>${sign}</text>
                                </span>
                                <div class="text-center border rounded-pill bg-warning fw-light">
                                <ctg:info language="${language}">price=${car.id}</ctg:info><text class="fs-6">${sign}</text>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    <footer class="container-fluid border-top fixed-bottom d-flex flex-wrap justify-content-between align-items-center py-1 bg-secondary bg-gradient">
        <div class="col-md-4 d-flex align-items-center">
            <span class="text-white">${copr}</span>
        </div>
        <ul class="nav col-md-4 justify-content-end list-unstyled d-flex">
            <li class="ms-3">
                <a target="_blank" href="https://twitter.com/epam_belarus?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor">
                    <img class="bi" width="24" height="24" src="img/twitter.png" />
                </a>
            </li>
            <li class="ms-3">
                <a target="_blank" href="https://www.instagram.com/epam.belarus/?hl=ru">
                    <img width="24" height="24" src="img/instagram.png" />
                </a>
            </li>
            <li class="ms-3">
                <a target="_blank" href="https://www.facebook.com/EPAM.Belarus/">
                    <img class="bi" width="24" height="24" src="img/facebook.png" />
                </a>
            </li>
        </ul>
    </footer>

</body>
</fmt:bundle>
<script src="js/bootstrap.bundle.js"></script>
<script src="js/toogle.js"></script>
<script src="js/toast.js"></script>
</html>
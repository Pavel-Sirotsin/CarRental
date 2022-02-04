    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/taglib.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="locale">

<fmt:message key="title.booking.result" var="titleBookingResult"/>
<fmt:message key="label.copr" var="copr"/>
<fmt:message key="label.sign" var="sign"/>
<fmt:message key="label.to.payment" var="toPay"/>
<fmt:message key="label.cancel" var="cancel"/>
<fmt:message key="booking.result.total" var="total"/>
<fmt:message key="booking.result.days" var="days"/>

<fmt:message key="payment.block.holder" var="holder"/>
<fmt:message key="payment.block.expiration" var="expiration"/>
<fmt:message key="payment.block.cvv" var="cvv"/>
<fmt:message key="payment.block.number" var="number"/>


<fmt:message key="label.notreg" var="notreg"/>
<fmt:message key="label.lang" var="lang"/>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="icon" href="img/icon.png"/>
    <title>${titleBookingResult}</title>
</head>

<body style="background-image: url(img/background_payment.jpg); background-size: cover; background-attachment: fixed;">

    <!-- Header -->

    <header class="navbar navbar-light bg-secondary bg-gradient p-1">
        <nav class="container-fluid gap-2 d-flex justify-content-between">
            <div class="col-md-auto col-lg-auto">
                <a class="navbar-brand" href="controller?command=home">
                    <img src="img/logo-rentacar.svg" alt="logo image" width="100" height="30" class="d-inline-block align-text-top">
                </a>
            </div>
        </nav>
    </header>

    <!-- Alert -->
    <c:if test="${not empty paymentResult}">
         <div id="resultToast" class="text-center position-fixed start-50 translate-middle-x p-2 w-50 alert alert-warning"aria-live="polite" aria-atomic="true" role="alert" data-bs-delay="3000" style="z-index: 10;">
              <fmt:message key="${paymentResult}"/>
         </div>
         <c:remove var="paymentResult" scope="page"/>
    </c:if>

    <!-- Main form container -->

    <div class="container col-lg-4 mb-5 mt-5">
        <div class="modal-content bg-gradient bg-secondary">
            <div class="modal-header text-white d-flex justify-content-center p-1">
                <img style="width: 10rem; height: 3%; " src="img\credit-logo.png" alt="credit car img">
            </div>
            <form class="modal-body text-dark" id="cardInfo" action="controller" method="POST">
            <input type="hidden" name="command" value="makePayment"/>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="floatingHolder" name="holder" value="" placeholder="${holder}">
                    <label for="floatingInput">${holder}</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="floatingCard" name="cardNumber" value="" placeholder="${number}">
                    <label for="floatingInput">${number}</label>
                </div>

                <div class="input-group mb-3 gap-3 justify-content-between">
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="floatingExpiration" name="expiration" value="" placeholder="${expiration}">
                        <label for="floatingInput">${expiration}</label>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control" id="floatingCVV" name="cvv" value="" placeholder="${cvv}">
                        <label for="floatingPassword">${cvv}</label>
                    </div>
                </div>

                <div class="d-flex bg-info p-2 rounded text-center ">
                    <div class="col">
                        <label for="days" class="form-label">${days}</label>
                        <text class="fs-6 fst-italic" id="days">${booking.daysAmount}</text>
                    </div>
                    <div class="col">
                        <label for="car" class="form-label">${total}</label>
                        <text class="fs-6 fst-italic" id="car">
                        <ctg:info language="${language}">bookingPrice=${booking.sum}/></ctg:info>${sign}
                        </text>
                    </div>
                </div>

            </form>
            <div class="modal-footer d-flex justify-content-center p-1">
                 <button type="submit" class="w-25 btn btn-success" form="cardInfo">${toPay}</button>
                 <a href="controller?command=index" class="w-25 btn btn-danger">${cancel}</a>
            </div>
        </div>

     <!--Footer-->
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
    <c:remove var="car"/>
    </body>
    </fmt:bundle>
    <script src="js/bootstrap.bundle.js"></script>
    <script src="js/toogle.js"></script>
    <script src="js/toast.js"></script>
    </html>
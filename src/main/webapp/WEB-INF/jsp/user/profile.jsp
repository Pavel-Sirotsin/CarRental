<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/taglib.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<c:if test="${empty sessionUser}">
<c:redirect url="index.jsp"/>
</c:if>

<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="locale">

<fmt:message key="title.profile" var="title"/>
<fmt:message key="label.home" var="home"/>
<fmt:message key="label.contact" var="contact"/>
<fmt:message key="label.copr" var="copr"/>
<fmt:message key="label.submit" var="submit"/>
<fmt:message key="label.notreg" var="notreg"/>
<fmt:message key="label.logout" var="logout"/>
<fmt:message key="label.lang" var="lang"/>

<fmt:message key="info.profile" var="info"/>
<fmt:message key="profile.description" var="profileDescription"/>
<fmt:message key="customer.number" var="customerNumber"/>
<fmt:message key="label.first.name" var="firstName"/>
<fmt:message key="label.last.name" var="lastName"/>
<fmt:message key="profile.phone" var="phone"/>
<fmt:message key="profile.license" var="license"/>

<fmt:message key="profile.rental.date" var="rentalDate"/>
<fmt:message key="profile.rental.place" var="rentalPlace"/>
<fmt:message key="profile.return.date" var="returnDate"/>
<fmt:message key="profile.return.place" var="returnPlace"/>
<fmt:message key="profile.days.amount" var="daysAmount"/>
<fmt:message key="profile.cost" var="cost"/>
<fmt:message key="profile.paid" var="paid"/>
<fmt:message key="profile.car" var="car"/>
<fmt:message key="profile.rejection" var="rejection"/>
<fmt:message key="profile.reclamation" var="reclamation"/>
<fmt:message key="profile.edit.button" var="editButton"/>

<fmt:message key="command.profile.ref" var="profileRef"/>

 <head>
     <meta charset="UTF-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <link href="css/bootstrap.css" rel="stylesheet">
     <link rel="icon" href="img/icon.png"/>
     <link href="css/dataTables.botstrap5.min.css" rel="stylesheet">
     <title>${title}</title>
 </head>

<body style="background-image: url(img/background_profile.jpg); background-size: cover; background-attachment: fixed;">

    <!-- Header -->
    <header class="navbar navbar-light bg-secondary bg-gradient p-1">
        <nav class="container-fluid gap-2 d-flex justify-content-between">

            <div class="col-md-auto col-lg-auto">
                <a class="navbar-brand" href="controller?command=home">
                    <img src="img\logo-rentacar.svg" alt="logo image" width="100" height="30" class="d-inline-block align-text-top" />
                </a>
            </div>

            <ul class="nav nav-tabs col-md-auto col-lg-auto">
                <li class="nav-item">
                    <a class=" nav-link text-white " aria-current="page " href="controller?command=home">${home}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="controller?command=contact">${contact}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white " href="controller?command=faq">FAQ</a>
                </li>
            </ul>

            <div class="btn-group gap-2 col-md-auto col-lg-auto">
                <a href=${profileRef} class="btn btn-success rounded" aria-current="page ">${lang}</a>
                <a href="controller?command=logOut" class="btn btn-warning rounded" aria-current="page ">${logout}</a>
            </div>
        </nav>
    </header>

    <!-- Info label (top fixed) -->
    <div class="d-flex justify-content-center mb-4">
        <i class=" text-center d-block bg-primary bg-gradient text-white rounded-bottom" style="width: 20rem;">
            ${info}
        </i>
    </div>

    <!-- Main container with datatable and info panel -->
        <div class="container">
            <div class="row g-5 mb-5 justify-content-center">
                <!-- Info panel part -->
                <div class="col-auto h-100 text-white p-2 bg-secondary bg-gradient border-start border-5 border-warning rounded">
                    <h5 class="text-white border-bottom">${profileDescription}</h5>
                    <p>
                        <i class="text-warning">${customerNumber}</i>${sessionUser.id}<br>
                        <i class="text-warning">${firstName}</i>${sessionUser.account.name}<br>
                        <i class="text-warning">${lastName}</i>${sessionUser.account.surname}<br>
                        <i class="text-warning"> E-mail: </i>${sessionUser.account.email}<br>
                        <i class="text-warning">${phone}</i>${sessionUser.account.phoneNumber}<br>
                        <i class="text-warning">${license}</i>${sessionUser.account.drivingLicense}<br>
                    </p>

                    <!-- Manage button -->
                    <a class="text-center btn btn-info rounded" href="controller?command=profileEditor" role="button" data-bs-toggle="tooltip" data-bs-placement="right" title=${editButton}>
                        <img src="img/pencil.svg" alt="pencil">
                    </a>

                </div>

                <!-- Datatable part -->
                <div class="col-lg-auto mb-5 table-responsive">
                    <table id="pagination" class="table table-sm table-striped table-success text-center align-middle">
                        <thead>
                            <tr>
                                <th scope="col">№</th>
                                <th scope="col">${rentalDate}</th>
                                <th scope="col">${rentalPlace}</th>
                                <th scope="col">${returnDate}</th>
                                <th scope="col">${returnPlace}</th>
                                <th scope="col">${daysAmount}</th>
                                <th scope="col">${cost}</th>
                                <th scope="col">${paid}</th>
                                <th scope="col">${car}</th>
                                <th scope="col">${rejection}</th>
                                <th scope="col">${reclamation}</th>
                            </tr>
                        </thead>
                        <tbody>
                        <ctg:info language="${language}">userBooking=${sessionUser.account.id}</ctg:info>
                        </tbody>
                    </table>

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
<script src="js/jquery-3.5.1.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/dataTables.bootstrap5.min.js"></script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/taglib.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<c:if test="${empty sessionUser}">
<c:redirect url="index.jsp"/>
</c:if>

<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="locale">

<fmt:message key="title.admin.car" var="title"/>
<fmt:message key="label.home" var="home"/>
<fmt:message key="label.contact" var="contact"/>
<fmt:message key="label.copr" var="copr"/>
<fmt:message key="label.notreg" var="notreg"/>
<fmt:message key="label.logout" var="logout"/>
<fmt:message key="label.lang" var="lang"/>
<fmt:message key="info.admin.car" var="info"/>
<fmt:message key="admin.panel.description" var="description"/>
<fmt:message key="admin.panel.users" var="users"/>
<fmt:message key="admin.panel.orders" var="orders"/>
<fmt:message key="admin.panel.cars" var="cars"/>


<fmt:message key="label.brand" var="brand"/>
<fmt:message key="label.model" var="model"/>
<fmt:message key="label.fuel" var="fuel"/>
<fmt:message key="label.gear" var="gear"/>
<fmt:message key="label.doors" var="doors"/>
<fmt:message key="label.conditioner" var="conditioner"/>
<fmt:message key="label.trunk" var="trunk"/>
<fmt:message key="label.price" var="price"/>
<fmt:message key="label.insurance" var="insurance"/>

<fmt:message key="command.admin.car.ref" var="adminRef"/>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/dataTables.botstrap5.min.css" rel="stylesheet">
    <title>${title}</title>
</head>

<body style="background-image: url(img/background_admin_panel.jpg); background-size: cover; background-attachment: fixed;">
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
                    <a class=" nav-link text-white " aria-current="page" href="controller?command=home">${home}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="controller?command=contact">${contact}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white " href="controller?command=faq">FAQ</a>
                </li>
            </ul>

            <form class="btn-group gap-2 col-md-auto col-lg-auto">
                <a href=${adminRef} class="btn btn-success rounded" aria-current="page">${lang}</a>
                <a href="controller?command=logOut" class="btn btn-warning rounded" aria-current="page">${logout}</a>
            </form>
        </nav>
    </header>

    <!-- Info label (top fixed) -->
    <div class="d-flex justify-content-center mb-4">
        <i class=" text-center d-block bg-danger bg-gradient text-white rounded-bottom" style="width: 20rem;">
            ${info}
        </i>
    </div>

    <!-- Main container with datatable and control panel -->
    <div class="container">
        <div class="row g-5 justify-content-center">
            <!-- Control panel part -->
            <div class="col-auto h-100 p-2 bg-gradient border-start border-end border-5 border-danger rounded">
                <h5 class="text-white border-bottom">${description}</h5>
                <p class="border-bottom p-2">
                    <a class="link-dark fw-bold text-decoration-none" href="controller?command=adminUser">
                        <img src="img/gear.svg" alt="gear image"/>${users}
                    </a>
                    <br>
                    <a class="link-info fw-bold text-decoration-none" href="controller?command=adminBooking">
                        <img src="img/gear.svg" alt="gear image" />${orders}
                    </a>
                    <br>
                    <text class="p-3 fw-bold text-warning">
                        <img src="img/gear.svg" alt="gear image" />${cars}
                    </text>
                </p>
            </div>

            <!-- Datatable part -->
            <div class="col-xl-auto mb-5 table-responsive">
                <table id="pagination" class="table table-sm table-striped table-success text-center align-middle">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">${brand}</th>
                            <th scope="col">${model}</th>
                            <th scope="col">${fuel}</th>
                            <th scope="col">${gear}</th>
                            <th scope="col">${doors}</th>
                            <th scope="col">${conditioner}</th>
                            <th scope="col">${trunk}</th>
                            <th scope="col">${price}</th>
                            <th scope="col">${insurance}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <ctg:info language="${language}">carTable=0</ctg:info>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

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
<script src="js/bootstrap.bundle.js"></script>
<script src="js/jquery-3.5.1.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/dataTables.bootstrap5.min.js"></script>
</html>
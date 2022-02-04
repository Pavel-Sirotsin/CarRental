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

<fmt:message key="title.admin.user" var="title"/>
<fmt:message key="label.home" var="home"/>
<fmt:message key="label.contact" var="contact"/>
<fmt:message key="label.copr" var="copr"/>
<fmt:message key="label.notreg" var="notreg"/>
<fmt:message key="label.logout" var="logout"/>
<fmt:message key="label.lang" var="lang"/>
<fmt:message key="info.admin.user" var="info"/>
<fmt:message key="admin.panel.description" var="description"/>
<fmt:message key="admin.panel.users" var="users"/>
<fmt:message key="admin.panel.orders" var="orders"/>
<fmt:message key="admin.panel.cars" var="cars"/>
<fmt:message key="admin.panel.delete.user" var="deleteUser"/>
<fmt:message key="admin.panel.add.user" var="addUser"/>

<fmt:message key="label.submit" var="submit"/>
<fmt:message key="label.save" var="save"/>
<fmt:message key="label.reset" var="reset"/>

<fmt:message key="label.username" var="userName"/>
<fmt:message key="label.password" var="password"/>
<fmt:message key="label.role" var="role"/>

<fmt:message key="label.first.name" var="firstName"/>
<fmt:message key="label.last.name" var="lastName"/>
<fmt:message key="label.phone" var="phone"/>
<fmt:message key="label.license" var="license"/>
<fmt:message key="admin.panel.name" var="name"/>
<fmt:message key="admin.panel.surname" var="surname"/>
<fmt:message key="admin.panel.license" var="drivingLicense"/>
<fmt:message key="admin.panel.phone" var="phoneNumber"/>

<fmt:message key="command.admin.user.ref" var="adminRef"/>

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
                <a href=${adminRef} class="btn btn-success rounded" aria-current="page">${lang}</a>
                <a href="controller?command=logOut" class="btn btn-warning rounded" aria-current="page">${logout}</a>
            </div>
        </nav>
    </header>

    <!-- Admin action alert  -->
    <c:if test="${not empty adminActionResult}">
          <div id="resultToast" class="text-center position-fixed start-50 translate-middle-x p-2 w-50 alert alert-warning" role="alert" data-bs-delay="3000" style="z-index: 10;">
              <fmt:message key="${adminActionResult}"/>
         </div>
         <c:remove var="adminActionResult" scope="page"/>
    </c:if>

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
                    <text class="p-3 fw-bold text-black">
                        <img src="img/gear.svg" alt="gear image" />${users}
                    </text>
                    <br>
                    <a class="link-info fw-bold text-decoration-none" href="controller?command=adminBooking">
                        <img src="img/gear.svg" alt="gear image" />${orders}
                    </a>
                    <br>
                    <a class="link-warning fw-bold text-decoration-none" href="controller?command=adminCar">
                        <img src="img/gear.svg" alt="gear image" />${cars}
                    </a>
                </p>

                <!-- Manage buttons -->
                <div class="btn-group-vertical gap-1">
                    <button class="btn btn-sm btn-danger" type="submit" data-bs-toggle="modal" data-bs-target="#deleteModal">
                    ${deleteUser}
                    </button>
                    <button type="button" class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#newUserModal">
                    ${addUser}
                    </button>
                </div>

                <!-- Delete user -->
                <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header bg-danger text-white">
                                <h5 class="modal-title" id="deleteModalLabel">${deleteUser}</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="controller" method="POST" id="deleteUser">
                                <input type="hidden" name="command" value="deleteUser"/>
                                    <label for="idDelete" class="form-label">ID: <span class="text-warning">*</span></label>
                                    <input type="number" class="form-control" id="idDelete" name="userId" value="" min="0" max="1000" required>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-danger" form="deleteUser">${submit}</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Add user -->
                <div class="modal fade" id="newUserModal" tabindex="-1" aria-labelledby="newUserModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header bg-gradient bg-warning">
                                <h5 class="modal-title" id="newUserModalLabel">${addUser}</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <form action="controller" method="POST" id="newUserForm" class="row modal-body">
                            <input type="hidden" name="command" value="addUser"/>
                                <div class="col-6">
                                    <label for="name" class="form-label">${firstName}<span
                                            class="text-warning">*</span></label>
                                    <input type="text" class="form-control" id="name" name="firstName" value="" maxlength="32" required>
                                </div>
                                <div class="col-6">
                                    <label for="surname" class="form-label">${lastName}<span
                                            class="text-warning">*</span></label>
                                    <input type="text" class="form-control" id="surname" name="lastName" value="" maxlength="32" required>
                                </div>
                                <div class="col-4">
                                    <label for="role" class="form-label">${role}<span class="text-warning">*</span></label>
                                    <select class="form-select" id="role" name="userRole">
                                       <option value="client" selected>client</option>
                                       <option value="admin">admin</option>
                                    </select>
                                </div>
                                <div class="col-8">
                                    <label for="email" class="form-label">E-mail: <span
                                            class="text-warning">*</span></label>
                                    <div class="input-group">
                                        <span class="input-group-text">@</span>
                                        <input type="email" class="form-control" id="email" type="email" name="email" value="" maxlength="32" required>
                                    </div>
                                </div><br>
                                <div class="col-6">
                                    <label for="phone" class="form-label">${phone}<span
                                            class="text-warning">*</span></label>
                                    <input type="tel" class="form-control" id="phone" name="phone" value="" maxlength="32" required>
                                </div>
                                <div class="col-6">
                                    <label for="license" class="form-label">${license}<span
                                            class="text-warning">*</span></label>
                                    <input type="text" class="form-control" id="license" name="license" value="" maxlength="32" required>
                                </div>
                                <div class="col-6">
                                    <label for="login" class="form-label">${userName}<span
                                            class="text-warning">*</span></label>
                                    <input type="text" class="form-control" id="login" name="userName" value="" maxlength="32" required>
                                </div>
                                <div class="col-6">
                                    <label for="password" class="form-label">${password}<span
                                            class="text-warning">*</span></label>
                                    <input type="text" class="form-control" id="password" name="userPassword" value="" maxlength="32" required>
                                </div>
                            </form>
                            <div class="modal-footer">
                                <button class="btn btn-warning" form="newUserForm" type="reset">${reset}</button>
                                <button class="btn btn-success" form="newUserForm" type="submit">${save}</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Datatable part -->
            <div class="col-xl-auto mb-5 table-responsive">
                <table id="pagination" class="table table-sm table-striped table-success text-center align-middle">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">${name}</th>
                            <th scope="col">${surname}</th>
                            <th scope="col">E-mail</th>
                            <th scope="col">${phoneNumber}</th>
                            <th scope="col">${drivingLicense}</th>
                            <th scope="col">${userName}</th>
                            <th scope="col">${password}</th>
                            <th scope="col">${role}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <ctg:info>userTable=0</ctg:info>
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
<script src="js/bootstrap.bundle.js "></script>
<script src="js/jquery-3.5.1.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/dataTables.bootstrap5.min.js"></script>
<script src="js/toast.js "></script>
</html>
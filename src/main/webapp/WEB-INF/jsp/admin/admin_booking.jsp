<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/taglib.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:if test="${empty sessionUser}">
<c:redirect url="index.jsp"/>
</c:if>

<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="locale">

<fmt:message key="title.admin.booking" var="title"/>
<fmt:message key="label.home" var="home"/>
<fmt:message key="label.contact" var="contact"/>
<fmt:message key="label.copr" var="copr"/>
<fmt:message key="label.sign" var="sign"/>

<fmt:message key="label.submit" var="submit"/>
<fmt:message key="label.reset" var="reset"/>
<fmt:message key="label.save" var="save"/>

<fmt:message key="admin.booking.client" var="client"/>
<fmt:message key="admin.booking.order.id" var="orderId"/>
<fmt:message key="admin.booking.client.id" var="clientId"/>
<fmt:message key="admin.booking.car.id" var="carId"/>
<fmt:message key="admin.booking.rental.date" var="rentalDate"/>
<fmt:message key="admin.booking.return.date" var="returnDate"/>
<fmt:message key="admin.booking.rental.location" var="rentalLocation"/>
<fmt:message key="admin.booking.return.location" var="returnLocation"/>
<fmt:message key="admin.booking.days.amount" var="daysAmount"/>
<fmt:message key="admin.booking.cost" var="cost"/>
<fmt:message key="admin.booking.paid" var="paid"/>
<fmt:message key="admin.booking.rejection" var="rejection"/>
<fmt:message key="admin.booking.car" var="car"/>
<fmt:message key="admin.booking.accident" var="accident"/>

<fmt:message key="booking.form.office" var="office"/>
<fmt:message key="booking.form.bus" var="bus"/>
<fmt:message key="booking.form.railway" var="railway"/>
<fmt:message key="booking.form.airport" var="airport"/>
<fmt:message key="booking.form.another" var="another"/>
<fmt:message key="booking.form.insurance" var="insurance"/>

<fmt:message key="admin.panel.users" var="users"/>
<fmt:message key="admin.panel.orders" var="orders"/>
<fmt:message key="admin.panel.cars" var="cars"/>
<fmt:message key="admin.panel.description" var="description"/>

<fmt:message key="admin.panel.delete.order" var="deleteOrder"/>
<fmt:message key="admin.panel.add.order" var="addOrder"/>
<fmt:message key="admin.panel.reject.order" var="rejectOrder"/>
<fmt:message key="admin.reject.reason" var="reason"/>
<fmt:message key="admin.panel.add.accident" var="accidentCase"/>
<fmt:message key="admin.accident.description" var="accidentDescription"/>
<fmt:message key="admin.accident.cost" var="accidentCost"/>
<fmt:message key="admin.accident.damage" var="accidentDamage"/>

<fmt:message key="label.logout" var="logout"/>
<fmt:message key="label.lang" var="lang"/>
<fmt:message key="label.marked" var="marked"/>

<fmt:message key="info.admin.booking" var="info"/>
<fmt:message key="command.admin.booking.ref" var="adminRef"/>

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

            <div class="btn-group gap-2 col-md-auto col-lg-auto">
                <a href=${adminRef} class="btn btn-success rounded" aria-current="page">${lang}</a>
                <a href="controller?command=logOut" class="btn btn-warning rounded" aria-current="page">${logout}</a>
            </div>
        </nav>
    </header>

    <!-- Admin action alert  -->
    <c:if test="${not empty adminActionResult}">
          <div id="resultToast" class="text-center position-fixed start-50 translate-middle-x p-2 w-50 alert alert-warning" role="alert" data-bs-delay="4000" style="z-index: 10;">
              <fmt:message key="${adminActionResult}"/>
              <c:remove var="adminActionResult" scope="page"/>
         </div>
    </c:if>

    <!-- Info label (top fixed) -->
    <div class="d-flex justify-content-center mb-4">
        <i class=" text-center d-block bg-danger bg-gradient text-white rounded-bottom" style="width: 20rem;">
            ${info}
        </i>
    </div>

    <!-- Main container with table data and control panel -->
    <div class="container">
        <div class="row g-5 justify-content-center">
            <!-- Control panel part -->
            <div class="col-auto h-100 p-2 bg-gradient border-start border-end border-5 border-danger rounded">
                <h5 class="text-white border-bottom">${description}</h5>
                <p class="border-bottom p-2 g-1">
                    <a class="link-dark fw-bold text-decoration-none" href="controller?command=adminUser">
                        <img src="img/gear.svg" alt="gear image" /> ${users}
                    </a>
                    <br>
                    <text class="text-info p-3 fw-bold">
                        <img src="img/gear.svg" alt="gear image" />${orders}
                    </text>
                    <br>
                    <a class="link-warning fw-bold text-decoration-none" href="controller?command=adminCar">
                        <img src="img/gear.svg" alt="gear image" />${cars}
                    </a>
                </p>

                <!-- Manage buttons -->
                <div class="btn-group-vertical gap-1">
                    <button class="btn btn-sm btn-danger" type="submit" data-bs-toggle="modal" data-bs-target="#deleteModal">
                        ${deleteOrder}
                    </button>
                    <button class="btn btn-sm btn-warning" type="submit" data-bs-toggle="modal" data-bs-target="#rejectModal">
                        ${rejectOrder}
                    </button>
                    <button type="button" class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#accedentCaseModal">
                        ${accidentCase}
                    </button>
                    <button type="button" class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#newOrderModal">
                        ${addOrder}
                    </button>
                </div>

                <!-- Delete order -->
                <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header bg-danger text-white">
                                <h5 class="modal-title" id="deleteModalLabel">${deleteOrder}</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="controller" method="post" id="deleteOrder">
                                <input type="hidden" name="command" value="deleteOrder"/>
                                    <label for="idDelete" class="form-label">${orderId} <span
                                            class="text-warning">*</span></label>
                                    <input type="number" name="bookingId" value="" class="form-control" id="idDelete" min="0" max="1000" required>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-danger" form="deleteOrder">${submit}</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Reject order -->
                <div class="modal modal fade" id="rejectModal" tabindex="-1" aria-labelledby="rejectModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header bg-warning">
                                <h5 class="modal-title" id="rejectModalLabel">${rejectOrder}</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form id="rejectOrder" action="controller" method="POST">
                                <input type="hidden" name="command" value="addRejection">
                                    <div class="mb-3">
                                        <label for="idReject" class="form-label">${orderId} <span
                                                class="text-warning">*</span></label>
                                        <input type="number" class="form-control" id="idReject" name="bookingId" value="" min="0" max="1000" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="rejection-text" class="col-form-label">${reason}<span
                                                class="text-warning">*</span></label>
                                        <textarea class="form-control" name="rejectionReason" id="rejection-text" required></textarea>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-warning" form="rejectOrder">${submit}</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Accedent case -->
                <div class="col modal fade" id="accedentCaseModal" tabindex="-1" aria-labelledby="accedentCaseModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header bg-info">
                                <h5 class="modal-title" id="accedentCaseModalLabel">${accidentDescription}</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>

                            <form class="row modal-body" action="controller" method="POST" id="accedentData">
                            <input type="hidden" name="command" value="addAccident">
                                <div class="col-5">
                                    <label for="bookingId" class="form-label">${orderId}<span
                                            class="text-warning">*</span></label>
                                    <div class="input-group">
                                        <span class="input-group-text">#</span>
                                        <input type="number" class="form-control" name="bookingId" value="" id="bookingId" min="0" max="1000" required>
                                    </div>
                                </div>
                                <div class="col-7">
                                    <label for="addCost" class="form-label">${accidentCost}<span
                                            class="text-warning">*</span></label>
                                    <div class="input-group">
                                        <span class="input-group-text">$</span>
                                        <input type="text" class="form-control" name="accidentCost" value="" id="addCost" min="0" max="10000" required>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="message-text" class="col-form-label">${accidentDamage}<span
                                            class="text-warning">*</span></label>
                                    <textarea class="form-control" name="accidentDescription" id="message-text" required></textarea>
                                </div>
                            </form>
                            <div class="modal-footer">
                                <button class="btn btn-info" type="submit" form="accedentData">${submit}</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- New order editing -->
                <div class="col modal fade" id="newOrderModal" tabindex="-1" aria-labelledby="newOrderModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header bg-warning bg-gradient">
                                <h5 class="modal-title" id="newOrderModalLabel">New order</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>

                            <form id="newOrderForm" class="row g-1 modal-body" action="controller" method="POST">
                            <input type="hidden" name="command" value="addOrder">
                                <div class="col-6">
                                    <label for="takeDate" class="form-label">${rentalDate}<span
                                            class="text-warning">*</span></label>
                                    <input type="datetime-local" class="form-control" name="rentalDate" value="" id="takeDate" maxlength="32" required>
                                </div>
                                <div class="col-6">
                                    <label for="returnDate" class="form-label">${returnDate}<span
                                            class="text-warning">*</span></label>
                                    <input type="datetime-local" class="form-control" name="returnDate" value="" id="returnDate" maxlength="32" required>
                                </div>

                                <div class="col-6">
                                    <label for="takePlace" class="form-label">${rentalPlace}</label>
                                    <select class="form-select" id="takePlace" name="rentalLocation">
                                    <option value="${office}" selected>${office}</option>
                                    <option value="${bus}">${bus}</option>
                                    <option value="${railway}">${railway}</option>
                                    <option value="${airport}">${airport}</option>
                                    <option value="${another}">${another}</option>
                                </select>
                                </div>
                                <div class="col-6">
                                    <label for="returnPlace" class="form-label">${returnPlace}</label>
                                    <select class="form-select" id="returnPlace" name="returnLocation">
                                    <option value="${office}" selected>${office}</option>
                                    <option value="${bus}">${bus}</option>
                                    <option value="${railway}">${railway}</option>
                                    <option value="${airport}">${airport}</option>
                                    <option value="${another}">${another}</option>
                                </select>
                                </div>

                                <div class="col-6 ">
                                    <label for="clientId " class="form-label ">${clientId}<span
                                            class="text-warning ">*</span></label>
                                    <input type="number " class="form-control" name="userId" value="" id="clientId " min="0 " max=" 1000 " required>
                                </div>
                                <div class="col-6 ">
                                    <label for="carId " class="form-label ">${carId}<span
                                            class="text-warning ">*</span></label>
                                    <input type="number " class="form-control" name="carId" value="" id="carId " min="0 " max="1000 " required>
                                </div>
                            </form>

                            <div class="modal-footer ">
                                <button class="btn btn-warning" form="newOrderForm" type="reset">${reset}</button>
                                <button class="btn btn-success" form="newOrderForm" type="submit ">${save}</button>
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
                            <th scope="col ">ID</th>
                            <th scope="col ">${client}</th>
                            <th scope="col ">${rentalDate}</th>
                            <th scope="col ">${rentalLocation}</th>
                            <th scope="col ">${returnDate}</th>
                            <th scope="col ">${returnLocation}</th>
                            <th scope="col ">${daysAmount}</th>
                            <th scope="col ">${cost}</th>
                            <th scope="col ">${paid}</th>
                            <th scope="col ">${rejection}</th>
                            <th scope="col ">${car}</th>
                            <th scope="col ">${accident}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <ctg:info language="${language}">bookingTable=0</ctg:info>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- Footer -->
    <footer class="container-fluid border-top fixed-bottom d-flex flex-wrap justify-content-between align-items-center py-1 bg-secondary bg-gradient ">
        <div class="col-md-4 d-flex align-items-center ">
            <span class="text-white ">2021 Company, Inc</span>
        </div>

        <ul class="nav col-md-4 justify-content-end list-unstyled d-flex ">
            <li class="ms-3 ">
                <a class="text-muted " target="_blank " href="https://twitter.com/epam_belarus?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor ">
                    <img class="bi " width="24 " height="24 " src="img/twitter.png " />
                </a>
            </li>
            <li class="ms-3 ">
                <a class="text-muted " target="_blank " href="https://www.instagram.com/epam.belarus/?hl=ru ">
                    <img width="24 " height="24 " src="img/instagram.png " />
                </a>
            </li>
            <li class="ms-3 ">
                <a class="text-white " target="_blank " href="https://www.facebook.com/EPAM.Belarus/ ">
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
<script src="js/toast.js"></script>
</html>

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

<fmt:message key="title.edit.profile" var="titleEditProfile"/>
<fmt:message key="label.home" var="home"/>
<fmt:message key="label.contact" var="contact"/>
<fmt:message key="label.copr" var="copr"/>
<fmt:message key="label.logout" var="logout"/>
<fmt:message key="label.lang" var="lang"/>

<fmt:message key="edit.profile.info" var="info"/>
<fmt:message key="profile.description" var="profileDescription"/>
<fmt:message key="label.submit" var="submit"/>
<fmt:message key="label.reset" var="reset"/>
<fmt:message key="label.save" var="save"/>
<fmt:message key="label.username" var="userName"/>
<fmt:message key="label.password" var="password"/>
<fmt:message key="edit.profile.current.password" var="currentPassword"/>
<fmt:message key="edit.profile.new.password" var="newPassword"/>
<fmt:message key="label.notreg" var="notreg"/>
<fmt:message key="label.lang" var="lang"/>
<fmt:message key="label.first.name" var="firstName"/>
<fmt:message key="label.last.name" var="lastName"/>
<fmt:message key="label.phone" var="phone"/>
<fmt:message key="label.license" var="license"/>
<fmt:message key="edit.profile.marked" var="marked"/>

<fmt:message key="command.edit.profile.ref" var="editRef"/>

 <head>
     <meta charset="UTF-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <link href="css/bootstrap.css" rel="stylesheet">
     <link rel="icon" href="img/icon.png"/>
     <link href="css/dataTables.botstrap5.min.css" rel="stylesheet">
     <title>${titleEditProfile}</title>
 </head>

<body style="background-image: url(img/background_edit_profile.jpg); background-size: cover; background-attachment: fixed;">

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
                <a href=${editRef} class="btn btn-success rounded" aria-current="page">${lang}</a>
                <a href="controller?command=profile" class="btn btn-info rounded d-flex justify-content-center" aria-current="page">
                <img src="img/person-lines-fill.svg" alt="cabinet image">
                </a>
                <a href="controller?command=logOut" class="btn btn-warning rounded" aria-current="page">${logout}</a>
            </div>
        </nav>
    </header>

    <!-- Editing alert -->
    <c:if test="${not empty editingResult}">
         <div id="resultToast" class="text-center position-fixed start-50 translate-middle-x p-2 w-50 alert alert-info"aria-live="polite" aria-atomic="true" role="alert" data-bs-delay="4000" style="z-index: 10;">
              <fmt:message key="${editingResult}"/>
         </div>
    </c:if>

    <!-- Info label (top fixed) -->
    <div class="d-flex justify-content-center mb-4">
        <i class=" text-center d-block bg-primary bg-gradient text-white rounded-bottom" style="width: 20rem;">
            ${info}
        </i>
    </div>

    <!-- Main forms -->
    <div class="container text-white col-lg-7 mb-5 p-2 bg-secondary bg-gradient border-start border-5 border-warning rounded">
        <form class="row g-2">
        <input type="hidden" name="command" value="editProfile"/>
        <input type="hidden" name="block" value="first"/>
            <div class="col-md-4">
                <label for="name" class="form-label">${firstName}<span class="text-warning">*</span></label>
                <input type="text" class="form-control" id="name" maxlength="32" name="firstName" value="${sessionUser.account.name}" >
            </div>
            <div class="col-md-4">
                <label for="surname" class="form-label">${lastName}<span class="text-warning">*</span></label>
                <input type="text" class="form-control" id="surname" maxlength="32" name="lastName" value="${sessionUser.account.surname}" >
            </div>
            <div class="col-md-4">
                <label for="email" class="form-label">E-mail: <span class="text-warning">*</span></label>
                <div class="input-group">
                    <span class="input-group-text">@</span>
                    <input type="text" class="form-control" id="email" maxlength="32" name="email" value="${sessionUser.account.email}" >
                </div>
            </div>
            <div class="mt-2">
                <button class="btn btn-warning " type="reset">${reset}</button>
                <button class="btn btn-success" type="submit">${save}</button>
            </div>
        </form>
        <form class="row g-2">
        <input type="hidden" name="command" value="editProfile"/>
        <input type="hidden" name="block" value="second"/>
            <hr class="mt-3 text-warning" style="height: 3px;">
            <div class="col-md-4">
                <label for="phone" class="form-label">${phone}<span class="text-warning">*</span></label>
                <input type="text" class="form-control" id="phone" maxlength="16" name="phone" value="${sessionUser.account.phoneNumber}" >
            </div>
            <div class="col-md-4">
                <label for="license" class="form-label">${license}<span class="text-warning">*</span></label>
                <input type="text" class="form-control" id="license" maxlength="32" name="license" value="${sessionUser.account.drivingLicense}" >
            </div>
            <div class="mt-2">
                <button class="btn btn-warning " type="reset">${reset}</button>
                <button class="btn btn-success" type="submit">${save}</button>
            </div>
        </form>

        <form class="row g-2 mb-3">
        <input type="hidden" name="command" value="editProfile"/>
        <input type="hidden" name="block" value="third"/>
            <hr class="mt-3 text-warning" style="height: 3px;">
            <div class="col-md-4">
                <label for="login" class="form-label">${userName}<span class="text-warning">*</span></label>
                <input type="text" class="form-control" id="login" maxlength="32" name="userName" value="${sessionUser.login}">
            </div>
            <div class="col-md-4">
                <label for="passwordOld" class="form-label">${currentPassword}<span class="text-warning">*</span></label>
                <input type="text" class="form-control" id="passwordOld" maxlength="32" name="userPassword" value="" required>
            </div>
            <div class="col-md-4">
                <label for="passwordNew" class="form-label">${newPassword}<span class="text-warning">*</span></label>
                <input type="text" class="form-control" id="passwordNew" maxlength="32" name="newPassword" value="" required>
            </div>

            <div class="mt-2">
                <button class="btn btn-warning " type="reset">${reset}</button>
                <button class="btn btn-success" type="submit">${save}</button>
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
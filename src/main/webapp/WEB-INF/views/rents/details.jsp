<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center"> Reservation : ${reservationComplete.reservation.id}</h3>
                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Debut</b> <a class="pull-right">${reservationComplete.reservation.prettyDebut}</a>
                                </li>
                                <li class="list-group-item">
                                    <b>Fin</b> <a class="pull-right">${reservationComplete.reservation.prettyFin}</a>
                                </li>
                            </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <div class="col-md-9">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#users" data-toggle="tab">Client</a></li>
                            <li><a href="#cars" data-toggle="tab">Voiture</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="users">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Prenom</th>
                                            <th>Nom</th>
                                            <th>email</th>
                                        </tr>
                                        <tr>
                                            <td>${reservationComplete.client.id}</td>
                                            <td>${reservationComplete.client.prenom} </td>
                                            <td>${reservationComplete.client.nom}</td>
                                            <td>${reservationComplete.client.email}</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="cars">
                                <!-- /.box-header -->
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Constructeur</th>
                                            <th>modele</th>
                                            <th>nb de places</th>
                                        </tr>
                                        <tr>
                                            <td>${reservationComplete.vehicle.id}</td>
                                            <td>${reservationComplete.vehicle.constructeur} </td>
                                            <td>${reservationComplete.vehicle.modele}</td>
                                            <td>${reservationComplete.vehicle.nb_places}</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>

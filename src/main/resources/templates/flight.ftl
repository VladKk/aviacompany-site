<#import "parts/layout.ftl" as pg>
<@pg.page>
    <div class="container align-self-center">
        <div class="row">
            <div class="col-lg-8 mx-auto">
                <!-- List group-->
                <ul class="list-group shadow">
                    <!-- list group item-->
                    <#list flights as flight>
                        <li class="list-group-item">
                            <!-- Custom content-->
                            <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                                <div class="media-body order-2 order-lg-1">
                                    <h5 class="mt-0 font-weight-bold mb-2">From: ${flight.departure}</h5>
                                    <h5 class="mt-0 font-weight-bold mb-2">To: ${flight.destination}</h5>
                                    <div class="d-flex align-items-center justify-content-between mt-1">
                                        <h6 class="font-weight-bold my-2">Flight Time: ${flight.flight_time}</h6>
                                    </div>
                                </div>
                                <img src="../static/flight_thumbnail.jpeg" alt="Flight image" style="width: 200px;"
                                     class="ml-lg-5 order-1 order-lg-2">
                            </div> <!-- End -->
                        </li> <!-- End -->
                    </#list>
                </ul> <!-- End -->
            </div>
        </div>
    </div>
</@pg.page>
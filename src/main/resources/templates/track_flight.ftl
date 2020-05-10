<#import "parts/layout.ftl" as pg>
<@pg.page>
    <div class="container align-self-center">
        <form method="post" action="track_flight">
            <div class="form-group">
                <label for="id">Flight ID</label>
                <input type="number" class="form-control" name="id" id="id" aria-describedby="idHelp"
                       placeholder="Enter Flight ID">
            </div>
            <button type="submit" class="btn btn-primary">Track</button>
        </form>
        <#if flight??>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">From</th>
                    <th scope="col">To</th>
                    <th scope="col">Flight Time</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">${flight.id}</th>
                    <td>${flight.departure}</td>
                    <td>${flight.destination}</td>
                    <td>${flight.flight_time}</td>
                </tr>
                </tbody>
            </table>
        </#if>
    </div>
</@pg.page>
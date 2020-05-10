<#import "parts/layout.ftl" as pg>
<@pg.page>
    <div class="container align-self-center">
        <a href="/employee_login/admin_login">Admin Login</a>
        <br>
        <a href="/employee_login/dispatch_login">Dispatch Login</a>
        <br>
        <a href="/employee_login/flight_brigade_login">Flight Brigade Login</a>
    </div>

    <#if employee??>
        <div class="container align-self-center">
            <form method="post" action="${employee}_login">
                <fieldset class="form-group">
                    <legend class="border-bottom mb-4">Sign In</legend>
                    <div class="form-group">
                        <label for="id">ID</label>
                        <input type="number" class="form-control" name="id" id="id" placeholder="Enter ID">
                    </div>
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" class="form-control" name="username" id="username"
                               placeholder="Enter Username">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" name="password" id="password"
                               placeholder="Enter Password">
                    </div>
                </fieldset>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Sign In As Employee</button>
                </div>
            </form>
        </div>
    </#if>
</@pg.page>

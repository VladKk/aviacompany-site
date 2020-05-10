<#import "parts/layout.ftl" as pg>
<#import "/spring.ftl" as spring>
<@pg.page>
    <div class="container align-self-center">
        <@spring.bind "user"/>
        <form method="post" action="register">
            <fieldset class="form-group">
                <legend class="border-bottom mb-4">Join Today</legend>
                <div class="form-group">
                    <label for="email">Login</label>
                    <@spring.formInput "user.login"/><br>
                    <@spring.showErrors "<br>"/><br>
                    <#if loginError??>
                        ${loginError}
                    </#if>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <@spring.formInput "user.password"/><br>
                    <@spring.showErrors "<br>"/><br>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm Password</label>
                    <@spring.formInput "user.confirmPass"/><br>
                    <@spring.showErrors "<br>"/><br>
                    <#if passwordError??>
                        ${passwordError}
                    </#if>
                </div>
                <@spring.bind "client"/>
                <div class="form-group">
                    <label for="first_name">First Name</label>
                    <@spring.formInput "client.first_name"/><br>
                    <@spring.showErrors "<br>"/><br>
                </div>
                <div class="form-group">
                    <label for="last_name">Last Name</label>
                    <@spring.formInput "client.last_name"/><br>
                    <@spring.showErrors "<br>"/><br>
                </div>
                <div class="form-group">
                    <label for="age">Age</label>
                    <@spring.formInput "client.age"/><br>
                    <@spring.showErrors "<br>"/><br>
                </div>
            </fieldset>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Sign Up</button>
            </div>
        </form>
        <div class="border-top pt-3">
            <small class="text-muted">
                Already Have An Account? <a class="ml-2" href="/login">Sign In</a>
            </small>
        </div>
    </div>
</@pg.page>

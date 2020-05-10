<#import "parts/layout.ftl" as pg>
<@pg.page>
    <div class="container align-self-center">
        <form method="post" action="login">
            <fieldset class="form-group">
                <legend class="border-bottom mb-4">Sign In</legend>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" name="email" id="email" placeholder="Enter Email">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" name="password" id="password"
                           placeholder="Enter Password">
                </div>
            </fieldset>
            <button type="submit" class="btn btn-primary">Sign In</button>
        </form>
        <div class="border-top pt-3">
            <small class="text-muted">
                Want To Create Account? <a class="ml-2" href="/register">Sign Up</a>
            </small>
        </div>
    </div>
</@pg.page>

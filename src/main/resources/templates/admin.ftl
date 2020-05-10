<#import "parts/layout.ftl" as pg>
<@pg.page>
    <div class="container align-self-center">
        <table>
            <tr>
                <th>ID</th>
                <th>UserName</th>
                <th>Password</th>
                <th>Roles</th>
            </tr>
            <#if allUsers??>
                <#list all_users as user>
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.password}</td>
                        <td>
                            <#list user.roles  as role>${role.name}; </#list>
                        </td>
                        <td>
                            <form action="admin" method="post">
                                <input type="hidden" name="userId" value="${user.id}"/>
                                <input type="hidden" name="action" value="delete"/>
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                </#list>
            </#if>

        </table>

        <#if user??>
            ${user}
        </#if>

        <a href="/home">Home</a>
    </div>
</@pg.page>
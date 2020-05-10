<#import "parts/layout.ftl" as pg>
<@pg.page>
    <div class="container align-self-center">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col"></th>
                <th scope="col">Product</th>
                <th scope="col" class="text-right">Price</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list order as ord>
                <tr>
                    <td><img src="../static/ticket${ord.id}.jpeg" alt="${ord.id}"></td>
                    <td>Flight To: ${ord.destination}</td>
                    <td class="text-right"><#if hasAddServices == "1">${ord.getFullPrice()}<#else>${ord.price}</#if>
                        $
                    </td>
                    <td class="text-right">
                        <form method="post" action="deleteTicket">
                            <label for="id" hidden></label>
                            <input type="number" name="id" id="id" value="${ord.id}" hidden>
                            <button type="submit" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i></button>
                        </form>
                    </td>
                </tr>
            </#list>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td><strong>Total</strong></td>
                <#if total < 80>
                    <td class="text-right"><strong>0$</strong></td>
                <#else>
                    <td class="text-right"><strong>${total}$</strong></td>
                </#if>
            </tr>
            </tbody>
        </table>
        <a href="/thanks">
            <button class="btn btn-primary float-right">Buy</button>
        </a>
        <form method="post" action="clearAll">
            <button type="submit" class="btn btn-danger">Clear All</button>
        </form>
    </div>
</@pg.page>
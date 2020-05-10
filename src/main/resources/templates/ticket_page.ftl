<#import "parts/layout.ftl" as pg>
<@pg.page>
    <div class="container align-self-center" xmlns="http://www.w3.org/1999/html">
        <!-- Portfolio Item Heading -->
        <h1 class="my-4">Ticket To:
            <small>${ticket.destination}</small>
        </h1>

        <!-- Portfolio Item Row -->
        <div class="row">

            <div class="col-md-8">
                <img class="img-fluid" src="../static/ticket${ticket.id}.jpeg" alt="Ticket ${ticket.id}"
                     style="width: 700px; height: 500px; display: block;">
            </div>

            <div class="col-md-4">
                <h3 class="my-3">Ticket Description</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam viverra euismod odio, gravida
                    pellentesque urna varius vitae. Sed dui lorem, adipiscing in adipiscing et, interdum nec metus.
                    Mauris ultricies, justo eu convallis placerat, felis enim.</p>
                <h3 class="my-3">Ticket Price</h3>
                <strong><span class="tPrice">${ticket.price}</span>$</strong>
                <h3 class="my-3">Additional Services</h3>
                <div class="form-group form-checkbox">
                    <#list addServices as addService>
                        <input type="checkbox" id="${addService.id}" class="form-control-input">
                        <label class="form-control-label" for="${addService.id}">${addService.description}
                            - <span class="price">${addService.price}</span>$</label>
                        <br>
                    </#list>
                </div>
                <form method="post" action="/order">
                    <input type="submit" class="btn btn-primary" value="Buy">
                    <label for="hasAddService" hidden></label>
                    <input type="text" name="hasAddService" id="hasAddService" value="0" hidden>
                    <label for="id" hidden></label>
                    <input type="number" name="id" id="id" value="${ticket.id}" hidden>
                    <label id="total-show" for="total-show">Total: ${ticket.price}$</label>
                    <input type="number" step="0.01" name="total" id="total-show" value="${ticket.price}" hidden>
                </form>
            </div>

        </div>
        <!-- /.row -->
    </div>

    <script>
        $(document).ready(function () {
            let total = parseFloat($(".tPrice").text().replace(',', '.'));

            $('input[type="checkbox"]').click(function () {
                if (total == $(".tPrice").text().replace(',', '.')) {
                    $("input[id='hasAddService']").val("1");
                } else {
                    $("input[id='hasAddService']").val("0");
                }

                if ($(this).is(":checked"))
                    total += parseFloat($(".price").text().replace(',', '.'));
                else
                    total -= parseFloat($(".price").text().replace(',', '.'));

                $("input[id='total-show']").val(total.toFixed(2));
                $("#total-show").text("Total: " + total.toFixed(2) + "$");
            });
        });
    </script>
</@pg.page>
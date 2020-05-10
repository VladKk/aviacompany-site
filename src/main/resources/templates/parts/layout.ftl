<#macro page>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>${label} | Aviacompany</title>
    <#include "bootstrap.ftl">
</head>
<body style="background-color: #dedede;">
<#include "navbar.ftl">
<h1 class="text-center">${label} Page</h1>
<div class="container align-self-center">
    <#nested>
</div>
<#include "footer.ftl">
</body>
</html>

</#macro>
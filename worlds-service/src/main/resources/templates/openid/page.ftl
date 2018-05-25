<#import "/spring.ftl" as spring/>

<#macro page>
<!DOCTYPE html>
<html>
<head>
    <title><@spring.message "openid.title" /></title>

    <link rel="stylesheet" type="text/css" href="/semantic-ui/semantic.min.css"/>
</head>
<body>
<div class="ui fluid container">
    <div class="ui top fixed inverted borderless menu">
        <div class="header item">
            <@spring.message "openid.title" />
        </div>
    </div>

    <div class="ui text container" style="padding-top: 4em;">
        <#nested />
    </div>
</div>
</body>
</html>
</#macro>

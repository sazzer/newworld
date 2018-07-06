<#import "/spring.ftl" as spring/>
<#import "./page.ftl" as page />

<@page.page>
    <h2 class="ui center aligned icon header">
        <i class="circular user outline icon"></i>
        <@spring.message "openid.authorize.start.header" />
    </h2>
    <div class="ui segment">
        <form action="<@spring.url "/openid/authorize/continue" />" method="post" class="ui form" data-test="startAuth">
            <input type="hidden" name="response_type" value="${parameters.responseType}" />
            <input type="hidden" name="client_id" value="${parameters.clientId}" />
            <input type="hidden" name="scope" value="${parameters.scope}" />
            <input type="hidden" name="redirect_uri" value="${parameters.redirectUri}" />
            <input type="hidden" name="nonce" value="${parameters.nonce}" />
            <input type="hidden" name="state" value="${parameters.state!}" />
            <div class="required field">
                <label>
                    <@spring.message "openid.authorize.email.label" />
                </label>
                <input type="email" name="email" placeholder="<@spring.message "openid.authorize.email.placeholder" />" required autofocus>
            </div>
            <button class="ui primary button" type="submit"><@spring.message "openid.authorize.start.action" /></button>
        </form>
    </div>
</@page.page>

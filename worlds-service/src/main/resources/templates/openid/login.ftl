<#import "/spring.ftl" as spring/>
<#import "./page.ftl" as page />

<@page.page>
    <h2 class="ui center aligned icon header">
        <i class="circular key icon"></i>
        <@spring.message "openid.authorize.login.header" />
    </h2>
    <div class="ui segment">
        <#if blank_password!false>
            <#assign formError>error</#assign>
            <#assign passwordError>error</#assign>
        </#if>
        <#if invalid_password!false>
            <#assign formError>error</#assign>
            <#assign passwordError>error</#assign>
        </#if>
        <#if email_exists_error!false>
            <div class="ui warning message">
                <@spring.message "openid.authorize.login.error.duplicate_email" />
            </div>
        </#if>
        <form action="<@spring.url "/openid/authorize/login" />" method="post" class="ui form ${formError!}" data-test="loginForm">
            <input type="hidden" name="response_type" value="${parameters.responseType}" />
            <input type="hidden" name="client_id" value="${parameters.clientId}" />
            <input type="hidden" name="scope" value="${parameters.scope}" />
            <input type="hidden" name="redirect_uri" value="${parameters.redirectUri}" />
            <input type="hidden" name="nonce" value="${parameters.nonce}" />
            <input type="hidden" name="state" value="${parameters.state!}" />
            <div class="read-only required field">
                <label>
                    <@spring.message "openid.authorize.email.label" />
                </label>
                <input type="email" name="email" value="${email}" readonly>
            </div>
            <div class="required field">
                <label>
                    <@spring.message "openid.authorize.password.label" />
                </label>
                <input type="password" name="password" required autofocus>
                <#if blank_password!false>
                    <div class="ui error message">
                        <@spring.message "openid.authorize.password.error.blank_password" />
                    </div>
                </#if>
                <#if invalid_password!false>
                    <div class="ui error message">
                        <@spring.message "openid.authorize.password.error.invalid_password" />
                    </div>
                </#if>
            </div>
            <button class="ui primary button" type="submit"><@spring.message "openid.authorize.login.action" /></button>
        </form>
    </div>
</@page.page>

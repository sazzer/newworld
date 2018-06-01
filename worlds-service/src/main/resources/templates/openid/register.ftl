<#import "/spring.ftl" as spring/>
<#import "./page.ftl" as page />

<@page.page>
    <h2 class="ui center aligned icon header">
        <i class="circular id badge outline icon"></i>
        <@spring.message "openid.authorize.register.header" />
    </h2>
    <div class="ui segment">
        <#if blank_password!false>
            <#assign formError>error</#assign>
            <#assign passwordError>error</#assign>
        </#if>
        <#if password_mismatch!false>
            <#assign formError>error</#assign>
            <#assign password2Error>error</#assign>
        </#if>
        <#if blank_username!false>
            <#assign formError>error</#assign>
            <#assign usernameError>error</#assign>
        </#if>
        <#if duplicate_username!false>
            <#assign formError>error</#assign>
            <#assign usernameError>error</#assign>
        </#if>
        <form action="<@spring.url "/openid/authorize/register" />" method="post" class="ui form ${formError!}">
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

            <div class="required field ${passwordError!}">
                <label>
                    <@spring.message "openid.authorize.password.label" />
                </label>
                <input type="password" name="password" required autofocus>
                <#if blank_password!false>
                    <div class="ui error message">
                        <@spring.message "openid.authorize.password.error.blank_password" />
                    </div>
                </#if>
            </div>

            <div class="required field ${password2Error!}">
                <label>
                    <@spring.message "openid.authorize.password2.label" />
                </label>
                <input type="password" name="password2" required>
                <#if password_mismatch!false>
                    <div class="ui error message">
                        <@spring.message "openid.authorize.password2.error.password_mismatch" />
                    </div>
                </#if>
            </div>

            <div class="required field ${usernameError!}">
                <label>
                    <@spring.message "openid.authorize.username.label" />
                </label>
                <input type="text" name="username" value="${username!}" placeholder="<@spring.message "openid.authorize.username.placeholder" />" required>
                <#if blank_username!false>
                    <div class="ui error message">
                        <@spring.message "openid.authorize.username.error.blank_username" />
                    </div>
                </#if>
                <#if duplicate_username!false>
                    <div class="ui error message">
                        <@spring.message "openid.authorize.username.error.duplicate_username" />
                    </div>
                </#if>
            </div>

            <div class="field">
                <label>
                    <@spring.message "openid.authorize.displayName.label" />
                </label>
                <input type="text" name="display_name" value="${displayName!}" placeholder="<@spring.message "openid.authorize.displayName.placeholder" />">
            </div>

            <button class="ui primary button" type="submit"><@spring.message "openid.authorize.register.action" /></button>
        </form>
    </div>
</@page.page>

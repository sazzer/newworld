<#import "/spring.ftl" as spring/>
<#import "./page.ftl" as page />

<@page.page>
    <h2 class="ui center aligned icon header">
        <i class="circular frown outline icon"></i>
        <@spring.message "openid.authorize.malformed.header" />
    </h2>
    <div class="ui segment">
        <p>
            <@spring.message "openid.authorize.malformed.text" />
        </p>

        <div class="ui bulleted list">
            <#if unsupported_response_type??>
                <div class="item">
                    <#assign args = [unsupported_response_type] />
                    <@spring.messageArgs "openid.authorize.malformed.unsupportedResponseType" args />
                </div>
            </#if>
            <#if missing_parameters??>
                <#list missing_parameters as param>
                    <div class="item">
                        <#assign args = [param] />
                        <@spring.messageArgs "openid.authorize.malformed.missingParameter" args />
                    </div>
                </#list>
            </#if>
        </div>
    </div>
</@page.page>

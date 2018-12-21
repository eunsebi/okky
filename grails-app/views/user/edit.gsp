<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />

    <asset:stylesheet src="style.css"/>
    <asset:stylesheet src="APW-style.css"/>
</head>
<body>
<g:sidebar/>
<div id="create-user" class="content clearfix" role="main">
    <h3 class="content-header">회원 정보 수정</h3>
    <div class="col-md-6 main-block-left">
        <div class="panel panel-default">
            <div class="panel-heading">
                <g:avatar avatar="${user.avatar}" size="medium" />
            </div>
            <g:form url="[resource:user, action:'update']" class="form-signup form-user panel-body" method='PUT' id='loginForm' autocomplete='off'>
                <g:hasErrors model="[user:user, userAvatar:user.avatar, userPerson:user.person]">
                    <div  class="alert alert-danger" role="alert">
                        <ul>
                            <g:eachError bean="${user}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                            <g:eachError bean="${user.avatar}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                            <g:eachError bean="${user.person}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </div>
                </g:hasErrors>

                <fieldset>
                    <div class="form-group">
                        <label class="control-label" for="person.email">이메일 주소</label>
                        <g:textField name="person.email" class="form-control input-sm" placeholder="${message(code: "person.email.label", default: '이메일')}" required="" value="${user?.person?.email}"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="person.fullName">이름</label>
                        <g:textField name="person.fullName" class="form-control input-sm" placeholder="${message(code: "person.fullName.label", default: '이름')}" required="" value="${user?.person?.fullName}"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="avatar.nickname">닉네임</label>
                        <g:textField name="avatar.nickname" class="form-control input-sm" placeholder="${message(code: "person.nickname.label", default: '닉네임')}" required="" value="${user?.avatar?.nickname}"/>
                    </div>
                    <div class="checkbox">
                        <label>
                            <g:checkBox name="person.dmAllowed" value="${user?.person?.dmAllowed}"  /> <g:message code="person.dm.allow.label" default="이메일 수신 동의"/>
                        </label>
                    </div>
                </fieldset>
                <button class="btn btn-primary btn-block" type="submit"><g:message code="user.button.edit.label" default="정보 수정"/></button>
            </g:form>

        </div>
        <g:if test="${company != null}">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-header">회사정보</h4>
                </div>
                <div class="panel-body">

                    <fieldset>
                        <div class="form-group">
                            <div class="avatar avatar-big text-center">
                                <a href="${request.contextPath}/company/info/${company.id}" class="avatar-photo avatar-company">
                                    <g:if test="${company?.logo}">
                                        <img src="${grailsApplication.config.grails.fileURL}/logo/${company.logo}"></a>
                                    </g:if>
                                    <g:else>
                                        <img src="${assetPath(src: 'company-default.png')}">
                                    </g:else>
                                </a>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="person.email">회사명</label>
                            <input type="text" value="${company.name}" class="form-control" disabled />
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="person.email">사업자등록번호</label>
                            <input type="text" value="${company.registerNumber}" class="form-control" disabled />
                        </div>
                    </fieldset>
                    <g:link uri="/company/edit" class="btn btn-info btn-block" ><g:message code="user.button.edit.label" default="회사 정보 수정"/></g:link>
                </div>
            </div>
        </g:if>
    </div>
    <div class="col-md-6 main-block-right">
        %{--<div class="panel panel-default">
            <div class="panel-heading">
                <h5 class="panel-header">SNS 연결</h5>
            </div>
            <div class="panel-body panel-margin sns-buttons">
                <g:if test="${user.oAuthIDs.find{ it.provider == 'facebook' }}">
                    <div id="facebook-connect-link" class="btn btn-disconnect btn-block"><i class="fa fa-chain-broken fa-fw"></i> Facebook 연결 끊기</div>
                </g:if>
                <g:else>
                    <oauth:connect provider="facebook" id="facebook-connect-link" class="btn btn-facebook btn-block"><i class="fa fa-facebook fa-fw"></i> Facebook 연결하기</oauth:connect>
                </g:else>
                --}%%{--<g:if test="${user.oAuthIDs.find{ it.provider == 'twitter' }}">
                    <div id="facebook-connect-link" class="btn btn-disconnect btn-block"><i class="fa fa-chain-broken fa-fw"></i> Twitter 연결 끊기</div>
                </g:if>
                <g:else>
                    <oauth:connect provider="twitter" id="twitter-connect-link" class="btn btn-twitter btn-block"><i class="fa fa-twitter fa-fw"></i> Twitter 연결하기</oauth:connect>
                </g:else>--}%%{--
                <g:if test="${user.oAuthIDs.find{ it.provider == 'google' }}">
                    <div id="facebook-connect-link" class="btn btn-disconnect btn-block"><i class="fa fa-chain-broken fa-fw"></i> Google 연결 끊기</div>
                </g:if>
                <g:else>
                    <oauth:connect provider="google" id="google-connect-link" class="btn btn-google btn-block"><i class="fa fa-google fa-fw"></i> Google 연결하기</oauth:connect>
                </g:else>
            </div>
        </div>--}%
        <div class="panel panel-default">
            <div class="panel-body panel-margin">
                <g:link controller="user" action="passwordChange" class="btn btn-info btn-block"><g:message code="user.button.passwordChange.label" default="비밀번호 변경"/></g:link>

                <g:link controller="user" action="withdrawConfirm" class="btn btn-default btn-block"><g:message code="user.button.withdraw.label" default="회원 탈퇴"/></g:link>
            </div>
        </div>
    </div>
    %{--<g:if test="${user.oid == null}">
    <div class="col-md-12 APW-setting">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h5 class="panel-header">AutoPassword™ 설정</h5>
            </div>
            <div class="panel-body panel-margin">
                <div class="APW-setting-panel">
                    <p>비밀번호 관리가 힘들다면 <span class="APW-blue">AutoPassword™</span>를 사용하세요!</p>
                    <p>사용자 대신 스마트폰에서 일회용 비밀번호를 생성하고 입력해주는 <span class="APW-underline">비밀번호 대체 서비스</span>입니다.</p>
                </div>
                <button class="btn btn-primary" type="button" id="btnAdd">설정하기</button>
            </div>
        </div>
    </div>
    </g:if>
    <g:if test="${user.oid != null}">
    <!-- AutoPassword 사용중일때 -->
    <div class="col-md-12 APW-setting">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h5 class="panel-header">AutoPassword™ 설정</h5>
            </div>
            <div class="panel-body panel-margin">
                <div class="APW-setting-panel">
                    <p><span class="APW-blue">AutoPassword™</span>를 사용중입니다.</p>
                    <p>스마트폰을 교체하거나 스마트폰에서 AutoPassword™ 애플리케이션을
                    재설치 했을 경우 사용 해지 후 애플리케이션에서 웹사이트 재등록이 필요합니다.</p>
                </div>
                <button class="btn btn-primary APW-disabled" type="button" id="btnCancel">해지</button>
            </div>
        </div>
    </div>
    </g:if>--}%

</div>


<content tag="script">
    <script>

      $(function($) {

        $("[id=btnAdd]").click(function(e) {
          location.href = "${request.contextPath}/autoPassword/joinStep";
        });
        $("[id=btnCancel]").click(function(e) {
          location.href = "${request.contextPath}/autoPassword/delUserAutopassword";
        });

      })

    </script>
</content>

</body>
</html>

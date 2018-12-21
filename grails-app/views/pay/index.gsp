<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main_with_banner"/>
    <title>All That Community</title>
    <meta name="google-site-verification" content="DkGncyJVqYFVekHithdbYnKgklkyKVwruPZ18WUDjr0" />
</head>
<body>
<g:sidebar/>

%{--<g:banner type="MAIN" />--}%

<div id="index" class="content scaffold-list clearfix" role="main">
    <iframe width="1300" height="900" src="https://ekkor.xyz/pay/home/payMain.do?email=${pay}" name="test" id="test" frameborder="0" scrolling="auto" align="middle">
    이 브라우저는 iframe을 지원하지 않습니다.</iframe>
</div>
<content tag="script">
    <script>
        $(function () {
            $('.timeago').timeago();
        });
    </script>
</content>
</body>
</html>

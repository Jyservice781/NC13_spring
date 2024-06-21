<%@page language="java" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>인덱스</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid">
    <div class="main h-100">
        <div class="row justify-content-center">
            <div class="col-7">
                <img src="/uploads/LEGO_logo.svg.png" alt="이미지로고" style="width:80px; height:auto;">
            </div>
        </div>
        <form action="/user/auth" method="post">
            <%-- user/auth 라는 곳에 post 방식으로 보내서 처리하겠다는 의미가 됨. -> PostMapping으로 받음--%>
            <div class="row justify-content-center">
                <div class="col-4">
                    <label for="username">아이디</label>
                    <%--label 태그는 input의 아이디 값을 for로 받아옴--%>
                    <input type="text" class="form-control" name="username" id="username">
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-4">
                    <label for="password">비밀번호</label>
                    <%--label 태그는 input의 아이디 값을 for로 받아옴--%>
                    <input type="password" class="form-control" name="password" id="password">
                </div>
            </div>
            <div class="row justify-content-center mt-5">
                <div class="col-2 text-center">
                    <input type="submit" class="btn btn-outline-primary" value="로그인">
                </div>
                <div class="col-2 text-center">
                    <a href="/user/register" class="btn btn-outline-secondary">회원가입</a>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
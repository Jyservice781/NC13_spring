<%@page language="java" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>회원가입</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container-fluid h-100">
        <form action="/user/register" method="POST">
            <div class="row justify-content-center">
                <div class="col-4">
                    <label for="username">아이디</label>
                    <input type="text" name="username" id="username" class="form-control">
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-4">
                    <label for="password">비밀번호</label>
                    <input type="password" name="password" id="password" class="form-control">
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-4">
                    <label for="nickname">닉네임</label>
                    <input type="text" name="nickname" id="nickname" class="form-control">
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-4 text-center">
                    <input type="submit" class="btn btn-outline-primary" value="회원가입">
                </div>
            </div>
        </form>
    </div>
</body>
</html>
<%@page language="java" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>에러</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="main h-100">
        <div class="row justify-content-center mt-5">
            <div class="col-4 text-center">
                <h1>${message}</h1>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-4 text-center btn btn-outline-danger mt-5" onclick="javascript:history.go(-1)">뒤로가기</div>
        </div>
    </div>
</div>

</body>
</html>
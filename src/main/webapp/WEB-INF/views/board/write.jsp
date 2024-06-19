<%@page language="java" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>새 글 작성하기</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid">
    <form action="/board/write" method="post">
        <div class="table">
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="input_title" name="title" placeholder="title">
                        <label for="input_title">title</label>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <textarea name="content" id="input_content" class="form-control" placeholder="내용"></textarea>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-6">
                    <input type="submit" class="btn btn-outline-primary w-100" value="작성하기">
                </div>
            </div>
        </div>

    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
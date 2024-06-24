<%@page language="java" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>새 글 작성하기</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.ckeditor.com/ckeditor5/41.4.2/classic/ckeditor.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>

</head>
<body>
<div class="container-fluid">
    <%--  여러파트로 나눠서 업로드 할때 multipart 라고 반드시 명시를 해줘야함. enctype="multipart/form-data" --%>
    <form method="post" action="/board/write" enctype="multipart/form-data">
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
                    <textarea name="content" id="input_content"></textarea>
                </div>
            </div>

            <div class="row justify-content-center">
                <div class="col-6">
                    <label for="input_file">첨부 파일</label>
                    <%--  <input type="file" class="form-control" id="input_file" name="file">--%>
                    <input type="file" class="form-control" id="input_file" name="file" multiple>
                    <%-- multiple 속성이 있으면 여러개의 파일업로드가 가능하다--%>
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


<script>
    ClassicEditor
        .create(document.querySelector('#input_content'), {
            ckfinder : {
                uploadUrl: '/board/uploads'
            }
        })
        .catch(error => {
            console.log(error)
        })

</script>
</body>
</html>
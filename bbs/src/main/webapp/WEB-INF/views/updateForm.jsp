<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">	
<title>Insert title here</title>
</head>
<body>
	<form action="/bbs/update.bbs" method="post">
	<input type="hidden" name="articleNum" value="${article.articleNum}">
		<table class="table">
			<tr>
				<td>글쓴이 : </td>
				<td>${id }</td>
			</tr>
			<tr>
				<td>제목 : </td>
				<td><input type="text" name="title" value="${article.title}"></td>				
			</tr>
			<tr>
				<td colspan="2">
				<textarea name="content" rows="10" cols="60">${article.content}</textarea>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="수정하기"></td>
				<td><input type="reset" value="취소"></td>
			</tr>
		</table>
	</form>
</body>
</html>
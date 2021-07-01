<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<form action="/bbs/login.bbs" method="post" >
		<label for="id"> ID : <input type="text" name="id" id="id" required="required"></label>
		<label for="pass">PASS : <input type="text" name="pass" id="pass" required="required"></label>
		<input type="submit" value="로그인"> 
		<input type="reset"  value="취소">  
		<input type="button" value="회원가입">

	</form>
</body>
</html>
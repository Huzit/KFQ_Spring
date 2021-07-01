<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 	 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>콘텐츠</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<h3>
		글번호 : ${article.articleNum }<br/>
		아이디 : ${article.id }<br/>
		제목 : ${article.title}<br/>
		본문 : ${article.content}<br/>
		날짜 : ${article.writeDate}<br/>
	</h3>
	
	<div>
		<c:forEach var="file" items="${fileList }">
			<li><a href="/bbs/download.bbs?savedFileName=${file.savedFileName }">${file.originalFileName }</a></li>
		</c:forEach>
	</div>
	<hr>
	<!-- 토일요일과제 : 수정하기를 누르면 이 글을 읽어와서 라이트 폼.jsp를 적절히 조절해서 타이틀하고 본문내용은 수정할 수 있도록 본문내용은 바꿀수 있어야 한다. 타이틀과 컨텐트문장은 바꿀 수 있도록 해야된다. 업데이트하고나면 content.jsp를 다시 읽을 수 있어야한다. -->
	<!-- 삭제하기도 구현 파일 업로드와 다운로드 찾아보기 axios공부 -->
	
	<!-- 로그인 안됬을 떄 -->
	<c:if test="${id != null }">
		
		<!-- 자기 글일 떄 -->
		<c:if test="${id==article.id}">
			<input type="button" value="수정하기" onclick="document.location.href='/bbs/update.bbs?articleNum=${article.articleNum}'">	
			<input type="button" value="삭제하기" onclick="document.location.href='/bbs/delete.bbs?articleNum=${article.articleNum}'">	
		</c:if>
		
		<!-- 자기글 아닐때 -->
		<c:if test="${id!=article.id}">
			<input type="button" value="수정하기" disabled="disabled">	
			<input type="button" value="삭제하기" disabled="disabled">	
		</c:if>
		<input type="button" value="목록으로" onclick="document.location.href='/bbs/list.bbs'">
		
	</c:if>
	
	<!-- 아이디 없을 떄 -->
	<c:if test="${id==null }">
		<input type="button" value="수정하기" disabled="disabled">	
		<input type="button" value="삭제하기" disabled="disabled">
		<input type="button" value="목록으로" onclick="document.location.href='/bbs/list.bbs'">
	</c:if>
	
	<div>
		<textarea rows="5" cols="70"></textarea><br>
		
		<c:if test="${id == null }">
			<input type="button" value="comment 쓰기" disabled="disabled">
		</c:if>
		<c:if test="${id != null }">
			<input type="button" value="comment 쓰기" id="commentWrite">
		</c:if>
		<input type="button" value="comment 읽기()" onclick="getComment(1, event)" id="commentRead"></td>
	</div>
	
	<script type="text/javascript">
		/* # : id, . : class,  : tag */
		$(document).ready(function(){
			$("#commentWrite").on("click", function(){
				alert('버튼이 동작합니다.');
			})		
		})
	</script>
</body>
</html>
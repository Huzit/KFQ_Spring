<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>콘텐츠</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- 공통으로 요구하는 코드라서 .ajaxSetup로 뺴놨다 -->
<script type="text/javascript">
	$.ajaxSetup({
		type:"POST",
		async : true,
		dataType:"json",
		error : function(xhrs){
			alert("error html = "+xhr.statusText)
		}
	})	
</script>
</head>
<body>
	<h3>
		글번호 : ${article.articleNum }<br /> 
		아이디 : ${article.id }<br /> 
		제목 : ${article.title}<br /> 
		본문 : ${article.content}<br /> 
		날짜 : ${article.writeDate}<br />
	</h3>

	<div>
		<c:forEach var="file" items="${fileList }">
			<li><a
				href="/bbs/download.bbs?savedFileName=${file.savedFileName }">${file.originalFileName }</a></li>
		</c:forEach>
	</div>
	<hr>

	<!-- 로그인 안됬을 떄 -->
	<c:if test="${id != null }">

		<!-- 자기 글일 떄 -->
		<c:if test="${id==article.id}">
			<input type="button" value="수정하기"
				onclick="document.location.href='/bbs/update.bbs?articleNum=${article.articleNum}'">
			<input type="button" value="삭제하기"
				onclick="document.location.href='/bbs/delete.bbs?articleNum=${article.articleNum}'">
		</c:if>

		<!-- 자기글 아닐때 -->
		<c:if test="${id!=article.id}">
			<input type="button" value="수정하기" disabled="disabled">
			<input type="button" value="삭제하기" disabled="disabled">
		</c:if>
		<input type="button" value="목록으로"
			onclick="document.location.href='/bbs/list.bbs'">

	</c:if>

	<!-- 아이디 없을 떄 -->
	<c:if test="${id==null }">
		<input type="button" value="수정하기" disabled="disabled">
		<input type="button" value="삭제하기" disabled="disabled">
		<input type="button" value="목록으로"
			onclick="document.location.href='/bbs/list.bbs'">
	</c:if>

	<div>
		<textarea rows="5" cols="70" id="commentContent"></textarea>
		<br>

		<c:if test="${id == null }">
			<input type="button" value="comment 쓰기" disabled="disabled">
		</c:if>
		<c:if test="${id != null }">
			<input type="button" value="comment 쓰기" id="commentWrite">
		</c:if>
		<input type="button" value="comment 읽기(${commtentContent })"
			onclick="getComment(1, event)" id="commentRead">
	</div>

	<script type="text/javascript">
		/* # : id, . : class,  : tag */
		//쓰기버튼 클릭시 이벤트
		$(document).ready(function(){
			$("#commentWrite").on("click", function(){
				$.ajax({
					url:"/bbs/commentWrite.comment",
					//data{}에서는 EL을 ""로 감싸야한다 그 외에는 그냥 사용하면 됨
					data:{
						commentContent: $("#commentContent").val(), //<- id로 가져옴
						articleNum:"${article.articleNum}" // <- EL
					},
					/* beforeSend : function(){
						alert("시작전");
					},
					complete : function(){
						alert("완료후");
					}, */
					success : function(data){
						showHtml(data, 1)
					}
				})
			})		
		})
		
		//글 번호랑 페이지번호 가져옴
		function getComment(commPageNum, event) {
			/* event.preventDefault(); //원래 이벤트를 없애고 함수를 동작시킨다. <a href="#" onclick="">
			event.stopPropagation();//중첩된 이벤트가 있을 때 이벤트의 상위 또는 하위로의 전파를 막기위함 */
			$.ajax({
				url:"/bbs/commentRead.comment",
				data:{
					articleNum:"${article.articleNum}",
					//숫자와 문자 연산에서 +를 제외하고는 숫자를 우선시한다. 따라서 아래는 1*10으로 '계산'이 됨
					commentRow:commPageNum*10
				},
				success:function(data){
					showHtml(data, commPageNum);
				}
			})
		}
	
		function showHtml(data, commPageNum){
			let html="<table border='1' width='500' align='center'>";
			$.each(data, function(index, item){
				html +="<tr>";
				html +="<td>"+(index+1)+"</td>";
				html +="<td>"+item.id+"</td>";
				html +="<td>"+item.commentContent+"</td>"
				html +="<td>"+item.commentDate+"</td>";
				html +="</tr>";
				
			});
			
			html+="</table>";

			if("${commentCount}" > commPageNum*10){
				nextPageNum = commPageNum + 1;
				html += "<br /><input type='button' onclick='getComment(nextPageNum, event)' value='다음comment보기'<br>"
			}
			
			$("#showComment").html(html);
			$("#commentContent").val("");
			$("#commentContent").focus();
		}
	</script>
	<div id="showComment"></div>
</body>
</html>
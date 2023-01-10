<%@ page import="com.liferay.asset.kernel.model.AssetCategory" %>
<%@ page import="java.util.List" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/init.jsp" %>


<div id="" class="section" style="background-color: rgb(243, 243, 243);">
	<div class="section-center">
		<div class="container">
			<div class="row justify-content-center" >
				<div class="col-4">
					<h3>Newsletter</h3>
					<liferay-ui:error key="error-email" message="error-message-mail" />
					<br>
					<div class="booking-form">
						<form action="<portlet:actionURL />" method="post">
							<div class="form-group">
								<span class="form-label">Email</span>
								<input id="email" name="<portlet:namespace />email" required class="form-control" type="text" placeholder="Email">
							</div>

							<h5>Categorias</h5>

							<label class="container1">Todas
								<input type="checkbox" name="<portlet:namespace />name" value="Todas" checked="checked">
								<span class="checkmark"></span>
							</label>

							<c:forEach items="${names}" var="name">
								<label class="container1">${name}
									<input type="checkbox" id="name" name="<portlet:namespace />name" value="${name}">
									<span class="checkmark"></span>
								</label>
							</c:forEach>

							<div class="data">
								<span class="form-label">Email</span>
								<input id="body" name="<portlet:namespace />body" class="form-control" type="text" value="Subscrição feita com sucesso agora vai passar a receber actualizações de conteúdo e publicações das seguintes categorias selecionadas">
							</div>

							<div class="data">
								<span class="form-label">Subject</span>
								<input id="subject" name="<portlet:namespace />subject" class="form-control" type="text" value="Subscrição  feita com sucesso">
							</div>

							<br><br>

							<div class="form-btn">
								<button class="submit-btn">Subscrever</button>
							</div>
						</form>

						<liferay-ui:success key="key" message="message" />
						<liferay-ui:error key="error-key" message="error-message" />

					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	var url = window.location.pathname;
	//var id = url.substring(url.lastIndexOf('/') + 1);

	let params = (new URL(document.location)).searchParams;
	let email = params.get("email");

	let inputEmail = document.getElementById("email")
	inputEmail.value = email

	console.log(email)

</script>
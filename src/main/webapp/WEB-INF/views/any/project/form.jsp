<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.project.form.label.code" path="code" readonly="true"/>
	<acme:input-textbox code="any.project.form.label.title" path="title" readonly="true"/>
	<acme:input-textbox code="any.project.form.label.projectAbstract" path="projectAbstract" readonly="true"/>	
	<acme:input-integer code="any.project.form.label.cost" path="cost" readonly="true"/>	
	<acme:input-url code="any.project.form.label.link" path="link" readonly="true"/>
	<acme:input-checkbox code="any.project.form.label.indication" path="indication" readonly="true"/>
	<acme:input-textbox code="any.project.form.label.draftMode" path="draftMode" readonly="true"/>	
</acme:form>
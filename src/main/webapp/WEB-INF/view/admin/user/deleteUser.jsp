<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>user ${newUser.id}</title>
        </head>

        <body>
            ARE YOU WANT DELETE? <span>ID: ${newUser.id}</span>

            <form:form action="/admin/dashboard/delete" modelAttribute="newUser" method="post">
                <div>
                    <label class="form-label">id:</label>
                    <form:input type="text" class="form-control" path="id" />
                </div>
                <button type="submit">confirm</button>
            </form:form>
        </body>

        </html>
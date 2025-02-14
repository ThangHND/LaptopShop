<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    </head>

    <body>
        <table class="table table-hover container mt-5">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Email</th>
                    <th scope="col">address</th>
                    <th scope="col">phone</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${showUser}">

                    <tr>
                        <th>${user.id}</th>
                        <td>${user.email}</td>
                        <td>${user.address}</td>
                        <td>${user.phone}</td>
                        <td>
                            <a href="/admin/detail/${user.id}">View</a>
                            <a href="/admin/user" class="btn btn-primary">Create</a>
                            <a href="/admin/updateUser/${user.id}" class="btn btn-warning">Update</a>
                            <button class="btn btn-danger">Remove</button>
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </body>

    </html>
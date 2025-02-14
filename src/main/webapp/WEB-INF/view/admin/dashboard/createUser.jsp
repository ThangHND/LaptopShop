<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
        <div class="container mt-5">

            <form:form action="/admin/dashboard/createUser" method="post" modelAttribute="newUser">
                <div class="mb-3">
                    <label class="form-label">Email address</label>
                    <form:input type="email" class="form-control" name="email" path="email" />
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <form:input type="password" class="form-control" name="password" path="password" />
                </div>
                <div class="mb-3">
                    <label class="form-label">Full Name</label>
                    <form:input type="text" class="form-control" name="fullName" path="fullName" />
                </div>
                <div class="mb-3">
                    <label class="form-label">Address</label>
                    <form:input type="text" class="form-control" name="address" path="address" />
                </div>
                <div class="mb-3">
                    <label class="form-label">Phone</label>
                    <form:input type="text" class="form-control" name="phone" path="phone" />
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form:form>

        </div>
    </body>

    </html>
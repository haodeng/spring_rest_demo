# SpringBoot Rest Api. Hao's demo
Spring Boot for Rest basic structure, a good starting point to write complex Apis. The following features are included.

## 1. Rest CRUD
A user resource to create, read, update and delete.

## 2. Swagger doc
Create rest doc in the code. 

## 3. Rest exception handling
Customize the exception of validation, user specific exception and generic exceptions. For example, a descriptive response:
```json
{
    "timestamp": "2018-11-25T20:42:22.976+0000",
    "message": "Validation Failed",
    "details": "org.springframework.validation.BeanPropertyBindingResult: 1 errors\nField error in object 'user' on field 'firstName': rejected value [z]; codes [Size.user.firstName,Size.firstName,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [user.firstName,firstName]; arguments []; default message [firstName],2147483647,2]; default message [Name should have at least 2 characters]"
}
```

## 4. Validation
Validate the request in a nice way.

## 5. Api security
How to set user/role to protect the Api?

## 6. Hypermedia links
How to add hypermedia links to the Api response nicely? For example:
```json
{
    "id": 3,
    "firstName": "Jan",
    "lastName": "Smith",
    "_links": {
        "all-users": {
            "href": "http://localhost:8080/api/users"
        },
        "self": {
            "href": "http://localhost:8080/api/user/3"
        }
    }
}
```

## 7. Rest Api version
```javascript
Example URL:
http://localhost:8080/api/v1/users
http://localhost:8080/api/v2/users
```

## 8. JPA support
Use mem database as data privider.

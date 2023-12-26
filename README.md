Examples

-Registration:
axios.post("http://localhost:8080/api/v1/auth/register",{
  "name": "abderrahmen",
  "email": "jouini@mail.com",
  "password": "pass"
})
// trajaa token

-Login
axios.post(http://localhost:8080/api/v1/auth/authenticate,{
  "email": "jouini@mail.com",
  "password": "pass"
})
// trajaa token


-add Cours
axios.post("http://localhost:8080/api/v1/cours/add",{
  "title": "java",
  "price": 120,
  "imageUrl": "https://picsum.photos/200/300"
})

-get all cours
axios.get("localhost:8080/api/v1/cours")

-get cours by id
axios.get("localhost:8080/api/v1/cours/1")

-delete course
axios.delete("localhost:8080/api/v1/cours/delete/1")

-update cours
axios.patch("localhost:8080/api/v1/cours/update",{
    "id":1,
    "title":"java ee",
    "price":1500,
    "imageUrl":"imageUrl": "https://picsum.photos/200/300"
})

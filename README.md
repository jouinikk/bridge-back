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
//trajaa objet cour

-get all cours
axios.get("localhost:8080/api/v1/cours")
//trajaa liste


-get cours by id
axios.get("localhost:8080/api/v1/cours/1")
//trajaa objet cours


-delete course
axios.delete("localhost:8080/api/v1/cours/delete/1")
// void


-update cours
axios.patch("localhost:8080/api/v1/cours/update",{
    "id":1,
    "title":"java ee",
    "price":1500,
    "imageUrl":"imageUrl": "https://picsum.photos/200/300"
})
// void



p.s.  l token tnajem taamlelha decode bl jwt-decoder,
-npm install jwt-decode

for example:

let response = axios.post("http://localhost:8080/api/v1/auth/authenticate",{
      "email": email,
      "password": password
    })
let token = response.data.token;
const decoded = jwtDecode(token);
   id : decoded.id,
   name : decoded.name
   email : decoded.sub,

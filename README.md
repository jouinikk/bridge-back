# API Usage Examples

## Registration


axios.post("http://localhost:8080/api/v1/auth/register", {
  "name": "abderrahmen",
  "email": "jouini@mail.com",
  "password": "pass"
})
// Returns a token

## Login
axios.post("http://localhost:8080/api/v1/auth/authenticate", {
  "email": "jouini@mail.com",
  "password": "pass"
})
// Returns a token

## Add Course
axios.post("http://localhost:8080/api/v1/cours/add", {
  "title": "java",
  "price": 120,
  "imageUrl": "https://picsum.photos/200/300"
})
// Returns the added course object

## Get All Courses
axios.get("http://localhost:8080/api/v1/cours")
// Returns a list of courses
## Get Course by ID
axios.get("http://localhost:8080/api/v1/cours/1")
// Returns the course object with the specified ID
## Delete Course
axios.delete("http://localhost:8080/api/v1/cours/delete/1")
// Returns void
## Update Course
axios.patch("http://localhost:8080/api/v1/cours/update", {
    "id": 1,
    "title": "java ee",
    "price": 1500,
    "imageUrl": "https://picsum.photos/200/300"
})
// Returns void
## Additional Note
To decode the token using jwt-decode, install the package using:
npm install jwt-decode

## Example usage:

let response = axios.post("http://localhost:8080/api/v1/auth/authenticate", {
  "email": email,
  "password": password
})
let token = response.data.token;
const decoded = jwtDecode(token);
// Access decoded information
console.log({
  id: decoded.id,
  name: decoded.name,
  email: decoded.sub
});

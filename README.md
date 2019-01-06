# Project ?
> by Mateusz Ożóg & Patryk Kozarski

First Collab project!

Meistertask: https://www.meistertask.com/app/project/rontVLI6/project-collab

## Room-User:
#### Add new user to room by id with POST
  >/room/\<roomId\>/add/user?userId=\<userId\>
  
  With parameters:  
  \<roomId\> Long;  
  \<userId\> Long;
  
  Return:  
  HttpStatus OK (200)- If user has been added;  
  HttpStatus BAD_REQUEST (400)- If user hasn't been added;
  
  #### Add new user to room by login with POST
  >/room/\<roomId\>/add?login=\<login\>
  
  With parameters:  
  \<roomId\> Long;  
  \<login\> String;
  
  Return:  
  HttpStatus OK (200)- If user has been added;  
  HttpStatus BAD_REQUEST (400)- If user hasn't been added;
  
#### Select all users from room with GET
  >/room/\<roomId\>/users
  
  With parameters:  
  \<roomId\> Long;
  
  Return:  
  Optional\<List\<RoomUser\>\>;  
  
#### Select all rooms where is user with GET
  >/user/\<userId\>/rooms
  
  With parameters:  
  \<userId\> Long;
  
  Return:  
  Optional\<List\<RoomUser\>\>;  
  
## Message:
#### Create new message with POST
  >/room/\<roomId\>/message?senderId=\<senderId\>&message=\<message\>
  
  With parameters:  
  \<roomId\> Long;  
  \<senderId\> Integer;  
  \<message\> String;  
  
  Return:  
  HttpStatus CREATED (201)- If message has been created;  
  HttpStatus BAD_REQUEST (400)- If message hasn't been created;  
  
#### Delete message with DELETE
  >/room/\<roomId\>?messageId=\<messageId\>
  
  With parameters:  
  \<roomId\> Long;  
  \<messageId\> Long;  
  
  Return:  
  HttpStatus OK (200)- If message has been deleted;  
  HttpStatus BAD_REQUEST (400)- If message hasn't been deleted;  
  
#### Get messages from room with GET
  >/room/\<roomId\>?count=\<count\>
  
  With parameters:  
  \<roomId\> Long;  
  \<count\>(default value= 20) Integer;  
  
  Return:  
  List<Message>;  
  
#### Edit message with PUT
  >/room/\<roomId\>?message=\<Message\>
  
  With parameters:  
  \<roomId\> Long;  
  \<Message\> Message;  
  
  Return:  
  HttpStatus OK (200)- If message has been edited;  
  HttpStatus BAD_REQUEST (400)- If message hasn't been edited;  
  
## Room:
#### Create new room with POST
  >/room/new?creatorId=\<creatorId\>&name=\<name\>
  
  With parameters:  
  \<creatorId\> Long;  
  \<name\> String;  
  
  Return:  
  HttpStatus OK (200)- If room has been created;  
  HttpStatus BAD_REQUEST (400)- If room hasn't been created;  
  
#### Select all rooms with GET
  >/root/rooms
  
  Return:  
  Iterable<Room> with all rooms;  
  
## User:
#### Select all users with GET
  >/root/users
  
  Return:  
  Iterable<User> with all users;  

#### Create new user with GET
  >/auth/register?email=\<email\>&login=\<login\>&password=\<password\>
  
  With parameters:  
  \<email\> String;  
  \<login\> String;  
  \<password\> String;  
  
  Return:  
  HttpStatus CREATED (201)- If user has been created;  
  HttpStatus BAD_REQUEST (400)- If user hasn't been created;  
  
#### Login with GET
  >/auth/login?login=\<login\>&password=\<password\>
  
  With parameters:  
  \<login\> String;  
  \<password\> String;  
  
  Return:  
  User;  

#### Edit user password with PUT
  >/user/\<userId\>/password?password=\<password\>&newPassword=\<newPassword\>
  
  With parameters:  
  \<userId\> long;  
  \<password\> String;  
  \<newPassword\> String;  
  
  Return:  
  HttpStatus OK (200)- If password has been edited;  
  HttpStatus BAD_REQUEST (400)- If password hasn't been edited;
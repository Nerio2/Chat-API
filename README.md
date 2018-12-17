# Project ?
> by Mateusz Ożóg & Patryk Kozarski

First Collab project!

Meistertask: https://www.meistertask.com/app/project/rontVLI6/project-collab


## TODO:
//for every method: <user_token> as first parametr by POST

### RoomUser:
#### add new user \<id\> to room:
  >/room/\<name\>/add?user_id=\<id\>           //user id or login?
  
  return:
  
#### select all users from room \<name\>
  >/room/\<name\>/users
  
  return:
  
### Message:
#### enter to room and get \<limit (default=10)\> messages
  >/room/<name>?limit=\<limit\>
  
  return:
  
#### send message \<msg\> to room \<name\> from user \<id\>
  >/room/\<name\>?sender_id=\<id\>&message=\<msg\>
  
  return:
  
### Room:
#### add new room \<name\> with creator id: \<id\>
  >/room/new?creator_id=\<id\>&name=\<name\>
  
  return:
  
#### select all rooms
  >/rooms
  
  return:
  
### User:
#### add new user with: \<login\>, \<password\>, \<email\>
  >/register?login=\<login\>$password=\<password\>&email=\<email\>
  
  return:
  
#### login with \<login\>, \<password\>
  >/login?login=\<login\>&password=\<password\>
  
  return: user_token
  
#### select all users
  >/users
  
  return:
  
#### select info from user \<id\>      //or login?
  >/user/\<id\>
  
  return:
  

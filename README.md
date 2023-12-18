# Social Media API

## Description

The social Media platform aims to create a user-friendly application to intract with the user and connect with them,share their thoughts post and also like and comment on the post.  

## User

### User Registration and Profiles

- **API Endpoint:** `POST /api/user/new`
- **_Roles:_**
  - ROLE_USER
  - ROLE_ADMIN
- **API Endpoint:** `PUT /api/user/update`

  - To Edit and update user profiles pic and bio.

- **API Endpoint:** `Delete /api/user/remove/{userId}`

  -To delete a user Login using a username and password.

- **API Endpoint:**

- **Admin Operations:**
  - **API Endpoint:** `Delete /api/user/remove/{userId}` : To delete user accounts.
  - **API Endpoint:** `GET /api/users/all`: To View all user accounts.
  - **API Endpoint:** `Post/api/user/disable/{userId}`: To Disable user account.
- **Authorization and Authentication:**
  - Uses basic Auth for Authentication.Needs username and password for every endpoints.

## Content Management

### Posting and Sharing

- **API Endpoint:** `POST /api/post/new`

  - Create posts with text, images, and videos.

- **API Endpoint:** `Delete/api/post/remove/{PostId}`

  - To Delete post.

- **API Endpoint:** `POST /api/comment/new/{postId}/comment`
  - To Add comments to posts.
- **API Endpoint:** `Delete /api/comment/remove/{postId}`

  - To Add comments on posts.

- **API Endpoint:** `POST /api/post/reshare/{postId)`

  - To Reshare a post.

- **API Endpoint:** `POST /api/like/new/{postId)`
  - To Like a post.
- **API Endpoint:** `Delete /api/like/remove/{postId)`
  - To remove Like from post.

### Privacy Settings

- Set privacy settings for user posts (public, private).

### Media Storage

- **S3 Integration:**
  - Save files in S3, and store URLs in the required locations.

## Social Features

### Friendship and Following

- **API Endpoint:** `POST /api/follow/new/{username}`
  - Start Following.
- **API Endpoint:** `Delete /api/unfollow/{username}`
  - stop Following.

### Activity Feed

- **API Endpoint:** `GET /api/post/by/{userId}`
  - Get personalized activity feed.

## Analytics

### User Analytics

- **API Endpoint:** `GET /api/analytic/likeCount/{postId}`
  - Get user interaction analytics likes.
- **API Endpoint:** `GET /api/analytic/shareCount/{postId}`
  - Get user interaction analytics shares.
- **API Endpoint:** `GET /api/analytic/commentCount/{postId}`
  - Get user interaction analytics comments.

## Technology Stack

### Backend

- Java (Spring Boot)

### Database

- Use PostgreSQL 

Happy coding!

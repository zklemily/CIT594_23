// Define the Post class with the given properties
class Post {
  private String postId;
  private User user;
  private String content;
  private String timestamp;

  // Constructor to initialize the Post object with the given properties
  public Post(String postId, User user, String content, String timestamp) {
    this.postId = postId;
    this.user = user;
    this.content = content;
    this.timestamp = timestamp;
  }

  // Getters and setters for all properties
  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
    this.postId = postId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }
}

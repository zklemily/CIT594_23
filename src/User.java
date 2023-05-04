import java.util.*;

class User {
  private String userId;
  private String username;
  private String password;
  private String email;
  private String dateOfBirth;
  private Set<User> followerSet;
  private Set<User> followingSet;
  private List<Post> postList;

  // Constructor to initialize the User object with the given properties
  public User(String userId, String username, String password, String email, String dateOfBirth, String profilePicture) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
    this.followerSet = new HashSet<>();
    this.followingSet = new HashSet<>();
    this.postList = new ArrayList<>();
  }

  // Getters and setters for all properties
  public String getUserId() { return userId;}

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Set<User> getFollowers() {
      return followerSet;
  }

  public Set<User> getFollowings() {
      return followingSet;
  }

  public List<Post> getPostList() {
    return postList;
  }

  public void setPostList(List<Post> postList) {
    this.postList = postList;
  }

  // Method to add a friend to the friend list
  public void follow(User user) {
    this.followingSet.add(user);
    user.addFollower(this);
  }

  public void addFollower(User user) {
    this.followerSet.add(user);
  }

  // Method to remove a friend from the friend list
  public void removeFollower(User user) {
    this.followerSet.remove(user);
  }

  // Method to create a post and add it to the post list
  public void createPost(Post post) {
    postList.add(post);
  }

  // Method to delete a post from the post list
  public void deletePost(Post post) {
    postList.remove(post);
  }
}

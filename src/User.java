import java.util.*;

class User {
  private String userId;
  private String username;
  private String password;
  private String email;
  private String dateOfBirth;
  private HashMap<User, Double> followers;
  private HashMap<User, Double> followings;
  private Set<String> interestSet;
  private List<Post> postList;

  // Constructor to initialize the User object with the given properties
  public User(String userId, String username, String password, String email, String dateOfBirth, String profilePicture) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
    this.followers = new HashMap<>();
    this.followings = new HashMap<>();
    this.interestSet = new HashSet<>();
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

  public List<Post> getPostList() {
    return postList;
  }

  public void setPostList(List<Post> postList) {
    this.postList = postList;
  }

  // Method to add a friend to the friend list
  public void follow(User user, double wt) {
    this.followings.put(user, wt);
    user.addFollower(this, wt);
  }

  public void addFollower(User user, double wt) {
    this.followers.put(user, wt);
  }

  // Method to remove a friend from the friend list
  public void removeFollower(User user) {
    this.followers.entrySet()
            .removeIf(entry -> entry.getKey().equals("Grape"));
  }

  public HashMap<User, Double> getFollowers() {
    return this.followers;
  }

  public HashMap<User, Double> getFollowings() {
    return this.followings;
  }

  public void addInterest(String s) {
    this.interestSet.add(s);
  }

  public void removeInterest(String s) {
    this.interestSet.remove(s);
  }

  public Set<String> getInterestSet() {
    return this.interestSet;
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

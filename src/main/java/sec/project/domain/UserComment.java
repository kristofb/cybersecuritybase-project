package sec.project.domain;

public class UserComment  {

    private String username;
    /**
     * User comments about anything
     */
    private String comments;

    public UserComment() {
    }

    public UserComment(String username, String comments) {
        setUsername(username);
        setComments(comments);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}

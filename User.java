

public class User{

    private String userId;
    private String name;
    private String emailId;
    private String mobileNumber;
    public User(String userId,String name,String emailId,String mobileNumber){
        this.userId=userId;
        this.name=name;
        this.emailId=emailId;
        this.mobileNumber=mobileNumber;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    

    
}
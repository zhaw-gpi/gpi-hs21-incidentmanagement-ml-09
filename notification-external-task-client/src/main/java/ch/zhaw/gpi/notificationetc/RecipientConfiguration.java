package ch.zhaw.gpi.notificationetc;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RecipientConfiguration {
    @Id
    private String userId;
    private String mailAddress;

    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getMailAddress() {
        return mailAddress;
    }
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    
}

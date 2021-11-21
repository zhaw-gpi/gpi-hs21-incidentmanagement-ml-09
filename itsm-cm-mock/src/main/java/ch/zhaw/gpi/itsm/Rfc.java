package ch.zhaw.gpi.itsm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rfc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rId;
    private String rTitle;
    private String rDescription;
    private String rReasons;
    private String rState;
    
    public Long getrId() {
        return rId;
    }
    public void setrId(Long rId) {
        this.rId = rId;
    }
    public String getrTitle() {
        return rTitle;
    }
    public void setrTitle(String rTitle) {
        this.rTitle = rTitle;
    }
    public String getrDescription() {
        return rDescription;
    }
    public void setrDescription(String rDescription) {
        this.rDescription = rDescription;
    }
    public String getrReasons() {
        return rReasons;
    }
    public void setrReasons(String rReasons) {
        this.rReasons = rReasons;
    }
    public String getrState() {
        return rState;
    }
    public void setrState(String rState) {
        this.rState = rState;
    }


    



}

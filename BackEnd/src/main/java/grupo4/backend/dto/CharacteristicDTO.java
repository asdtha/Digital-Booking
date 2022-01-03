package grupo4.backend.dto;
import grupo4.backend.model.Characteristic;

import java.io.Serializable;

public class CharacteristicDTO implements Serializable {

    //Attributes
    private Long id;
    private String name;
    private String icon;

    //Constructors
    public CharacteristicDTO() {
    }

    public CharacteristicDTO(Characteristic characteristic){
        this.id= characteristic.getId();
        this.name= characteristic.getName();
        this.icon=characteristic.getIcon();

    }
    public CharacteristicDTO(String name, String icon) {
        this.name= name;
        this.icon= icon;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Long getId() {
        return id;
    }
}

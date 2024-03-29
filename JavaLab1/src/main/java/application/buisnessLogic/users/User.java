package application.buisnessLogic.users;

import application.contracts.users.IUser;
import application.models.bankAccounts.Notification;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Realization of user
 */
@NoArgsConstructor
public class User implements IUser {
    private ArrayList<Notification> notifications = new ArrayList<>();
    private String name;
    private String secondName;
    private String address;
    private String passportNumber;

    /**
     * Method to login user
     */
    @Override
    public void login(String name, String secondName, String address, String passportNumber) {
        this.address = address;
        this.name = name;
        this.secondName = secondName;
        this.passportNumber = passportNumber;
    }
    @Override
    public void getNotification(Notification notification) {
        notifications.add(notification);
    }

    @Override
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getSecondName() {
        return secondName;
    }
    @Override
    public String getAddress() {
        return address;
    }
    @Override
    public String getPassportNumber() {
        return passportNumber;
    }
}

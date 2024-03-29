package application.contracts.users;

import application.models.bankAccounts.Notification;

public interface IUser {
    void login(String name, String secondName, String address, String passportNumber);
    void getNotification(Notification notification);
    void setPassportNumber(String passportNumber);
    void setAddress(String address);
    String getName();
    String getSecondName();
    String getAddress();
    String getPassportNumber();
}

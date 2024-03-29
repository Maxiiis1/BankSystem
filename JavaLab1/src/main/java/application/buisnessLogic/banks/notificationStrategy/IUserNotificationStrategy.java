package application.buisnessLogic.banks.notificationStrategy;

import application.contracts.users.IUser;
import application.models.bankAccounts.Notification;

import java.util.ArrayList;

public interface IUserNotificationStrategy {
    void execute(ArrayList<IUser> users, Notification notification);
}

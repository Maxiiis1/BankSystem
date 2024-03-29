package application.buisnessLogic.banks.notificationStrategy;

import application.contracts.users.IUser;
import application.models.bankAccounts.Notification;

import java.util.ArrayList;

/**
 * Local realization of sending notification to user
 */
public class LocalUserNotification implements IUserNotificationStrategy {

    /**
     * Sends notification to users
     * @param users all subscribed users
     */
    @Override
    public void execute(ArrayList<IUser> users, Notification notification) {
        for (var user: users) {
            user.getNotification(notification);
        }
    }
}

package application.services.verificationChain;

import application.contracts.users.IUser;
/**
 * Class to verify user`s address
 */
public class AddressVerificationHandler extends VerificationHandlerBase {

    /**
     * verify address in User
     * @param user user to check
     * @return result - have or not address info
     */
    @Override
    public boolean verify(IUser user) {
        if (user.getAddress() == null){
            return false;
        }

        return super.verify(user);
    }
}

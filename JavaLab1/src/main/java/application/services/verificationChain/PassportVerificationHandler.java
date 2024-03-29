package application.services.verificationChain;

import application.contracts.users.IUser;

/**
 * Class to verify user`s passport
 */
public class PassportVerificationHandler extends VerificationHandlerBase{
    /**
     * verify passport in User
     * @param user user to check
     * @return result - have or not passport info
     */
    @Override
    public boolean verify(IUser user) {
        if (user.getPassportNumber() == null){
            return false;
        }

        return super.verify(user);
    }
}

package application.services.verificationChain;

import application.contracts.users.IUser;

public interface IVerificationHandler {
    IVerificationHandler setNext(IVerificationHandler handler);
    boolean verify(IUser user);
}

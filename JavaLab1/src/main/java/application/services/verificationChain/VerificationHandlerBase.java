package application.services.verificationChain;

import application.contracts.users.IUser;

public abstract class VerificationHandlerBase implements IVerificationHandler{
    protected IVerificationHandler nextVerificator;

    public IVerificationHandler setNext(IVerificationHandler handler){
        this.nextVerificator = handler;
        return handler;
    }
    public boolean verify(IUser user){
        if (this.nextVerificator != null){
            this.nextVerificator.verify(user);
        }

        return true;
    }
}

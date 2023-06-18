package a04.e1;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class BankAccountFactoryImpl implements BankAccountFactory {

    private static abstract class AbstractBankAccount implements BankAccount {
        protected abstract void setBalance(int amount);
    }

    private static class BankAccountDecorator implements BankAccount {

        private final AbstractBankAccount base;

        public BankAccountDecorator(AbstractBankAccount base) {
            this.base = base;
        }

        @Override
        public int getBalance() {
            return base.getBalance();
        }

        @Override
        public void deposit(int amount) {
            base.deposit(amount);
        }

        @Override
        public boolean withdraw(int amount) {
            return base.withdraw(amount);
        }

        protected void setBalance(int balance) {
            base.setBalance(balance);
        }
    }

    @Override
    public BankAccount createBasic() {
        return new AbstractBankAccount() {
            private int balance = 0;

            @Override
            public int getBalance() {
                return this.balance;
            }

            @Override
            public void deposit(int amount) {
                this.setBalance(balance + amount);
            }

            @Override
            public boolean withdraw(int amount) {
                if(this.balance < amount) {
                    return false;
                }
                this.setBalance(balance - amount);
                return true;
            }

            @Override
            protected void setBalance(int amount) {
                this.balance = amount;
            }

        };
    }

    @Override
    public BankAccount createWithFee(UnaryOperator<Integer> feeFunction) {
        return new AbstractBankAccount() {

            @Override
            public int getBalance() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getBalance'");
            }

            @Override
            public void deposit(int amount) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'deposit'");
            }

            @Override
            public boolean withdraw(int amount) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
            }

            @Override
            protected void setBalance(int amount) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setBalance'");
            }
            
        };
    }

    @Override
    public BankAccount createWithCredit(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWithCredit'");
    }

    @Override
    public BankAccount createWithBlock(BiPredicate<Integer, Integer> blockingPolicy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWithBlock'");
    }

    @Override
    public BankAccount createWithFeeAndCredit(UnaryOperator<Integer> feeFunction, Predicate<Integer> allowedCredit,
            UnaryOperator<Integer> rateFunction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWithFeeAndCredit'");
    }

}

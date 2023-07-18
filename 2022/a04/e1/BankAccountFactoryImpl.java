package a04.e1;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class BankAccountFactoryImpl implements BankAccountFactory {

    @Override
    public BankAccount createBasic() {
        return new BankAccount() {
            private int balance = 0;

            @Override
            public int getBalance() {
                return this.balance;
            }

            @Override
            public void deposit(int amount) {
                this.balance += amount;
            }

            @Override
            public boolean withdraw(int amount) {
                if (balance >= amount) {
                    balance -= amount;
                    return true;
                }
                return false;
            }

        };
    }

    @Override
    public BankAccount createWithFee(UnaryOperator<Integer> feeFunction) {
        return new BankAccount() {
            private final BankAccount base = createBasic();

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
                final var fee = feeFunction.apply(amount);
                return base.withdraw(amount + fee);
            }
        };
    }

    @Override
    public BankAccount createWithCredit(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction) {
        return new BankAccount() {
            private final BankAccount base = createBasic();

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
                if (base.withdraw(amount)) {
                    return true;
                }
                final var credit = amount - this.getBalance();
                if (allowedCredit.test(credit)) {
                    this.withdraw(base.getBalance());
                    this.deposit(-credit - rateFunction.apply(-credit));
                    return true;
                }
                return false;
            }

        };
    }

    @Override
    public BankAccount createWithBlock(BiPredicate<Integer, Integer> blockingPolicy) {
        return new BankAccount() {
            private final BankAccount base = createBasic();
            private static boolean blk = false;

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
                if (blockingPolicy.test(amount, getBalance())) {
                    blk = true;
                }
                return blk ? false : base.withdraw(amount);
            }

        };
    }

    @Override
    public BankAccount createWithFeeAndCredit(UnaryOperator<Integer> feeFunction, Predicate<Integer> allowedCredit,
            UnaryOperator<Integer> rateFunction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWithFeeAndCredit'");
    }

}

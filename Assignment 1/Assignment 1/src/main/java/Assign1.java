/// Assign1.java
/// A simple command-line program to compute:
///   Fibonacci numbers in the range [0, 40]
///   Factorials in the range [0, 2147483647] (Integer.MAX_VALUE)
///   The constant "e" using a Taylor series approximation,
///   with iteration counts in the range [1, 2147483647]
/// Usage:
///   java Assign1 -fib 6
///   java Assign1 -fac 10
///   java Assign1 -e 24
///   java Assign1 -fib 6 -fac 10 -e 10
/// SP 2025
/// CS 3100
/// Hans Gamlien
public class Assign1 {
    /// The entry point of the program. Parses the command-line arguments
    /// and calls the appropriate functions to compute fib, factorial, or e.
    /// @param args The command-line arguments
    public static void main(String[] args) {
        // If there are no arguments, display the help and exit immediately.
        if (args.length == 0) {
            printHelp();
            return;
        }
        /* Use a loop to process each argument in order.
         Manually increment "i" when we expect a following numeric argument*/
        int i = 0;
        while (i < args.length) {
            String currentArg = args[i];
            /*Check which type of request this argument represents.
              Handle each possible option using if-else statements.*/
            if ("-fib".equals(currentArg)) {
                /*Need one additional argument specifying "n" */
                i++;
                if (i >= args.length) {
                    /*There's no number after "-fib", print help and stop. */
                    printHelp();
                    return;
                }
                /*Parse the next argument as an integer for Fibonacci*/
                String fibArg = args[i];
                int n;
                try {
                    n = Integer.parseInt(fibArg);
                } catch (NumberFormatException e) {
                    /* If this is not a valid integer, show help and stop. */
                    printHelp();
                    return;
                }
                /* Validate the range for Fib */
                if (n < 0 || n > 40) {
                    System.out.println("Fibonacci valid range is [0, 40]");
                } else {
                    /* Compute and display the Fib result */
                    long fibValue = computeFibonacci(n);
                    System.out.println("Fibonacci of " + n + " is " + fibValue);
                }
            } else if ("-fac".equals(currentArg)) {
                /* Expect one additional argument for the factorial number */
                i++;
                if (i >= args.length) {
                    /* Not enough args for factorial */
                    printHelp();
                    return;
                }
                /* Parse the next argument as an integer */
                String facArg = args[i];
                int n;
                try {
                    n = Integer.parseInt(facArg);
                } catch (NumberFormatException e) {
                    /* If not valid integer, show help and stop. */
                    printHelp();
                    return;
                }
                /*Range: [0..2147483647]*/
                if (n < 0) {
                    System.out.println("Factorial valid range is [0, 2147483647]");
                } else {
                    java.math.BigInteger factorialValue = computeFactorial(n);
                    System.out.println("Factorial of " + n + " is " + factorialValue);
                }
            } else if ("-e".equals(currentArg)) {
                /* Expect one additional argument to specify the number of iterations for "e" */
                i++;
                if (i >= args.length) {
                    /* Not enough args for e */
                    printHelp();
                    return;
                }
                /* Parse the next argument as an integer */
                String eArg = args[i];
                int n;
                try {
                    n = Integer.parseInt(eArg);
                } catch (NumberFormatException e) {
                    /* If not a valid integer, show help and stop */
                    printHelp();
                    return;
                }
                /* Range for the 'e' iteration count is [1..2147483647] */
                if (n < 1) {
                    System.out.println("Valid e iterations range is [1, 2147483647]");
                } else {
                    /* Compute the value of e with the specified number of iterations */
                    java.math.BigDecimal eValue = computeE(n);
                    /* Round/set scale to 16 digits after the decimal, matching assignment example */
                    java.math.BigDecimal rounded = eValue.setScale(16, java.math.RoundingMode.HALF_EVEN);
                    System.out.println("Value of e using " + n + " iterations is " + rounded.toPlainString());
                }
            } else {
                /* If we reach here, the argument is not recognized */
                System.out.println("Unknown command line argument: " + currentArg);
            }
            /* Move to the next argument */
            i++;
        }
    }
    /// Print help instructions as outlined in the assignment.
    private static void printHelp() {
        System.out.println("--- Assign 1 Help ---");
        System.out.println("  -fib [n] : Compute the Fibonacci of [n]; valid range [0, 40]");
        System.out.println("  -fac [n] : Compute the factorial of [n]; valid range [0, 2147483647]");
        System.out.println("  -e [n]   : Compute the value of 'e' using [n] iterations; valid range [1, 2147483647]");
    }
    /// Computes the Fibonacci number at position n in the sequence
    /// F(0) = 1, F(1) = 1, F(2) = 2, etc.
    /// @param n The index in the Fibonacci sequence
    /// @return The Fibonacci number at index n
    private static long computeFibonacci(int n) {
        /*
         Assignment:
         F(0) = 1, F(1) = 1, F(2) = 2,
         So for n=0 => 1, n=1 => 1
        */
        if (n == 0) {
            return 1L;
        } else if (n == 1) {
            return 1L;
        }
        /* Iterative approach. */
        long fibNMinus2 = 1L;
        long fibNMinus1 = 1L;
        long fibN = 0L;// Placeholder for current fib value
        /* Start from index 2 up to n */
        for (int i = 2; i <= n; i++) {
            fibN = fibNMinus1 + fibNMinus2;
            fibNMinus2 = fibNMinus1;
            fibNMinus1 = fibN;
        }
        return fibN;
    }
    /// Computes the factorial of n using BigInteger to avoid overflow.
    /// @param n The number whose factorial is to be computed
    /// @return The factorial of n (n!)
    private static java.math.BigInteger computeFactorial(int n) {
        /* 0! = 1 */
        java.math.BigInteger result = java.math.BigInteger.ONE;
        /* Multiply by each integer from 1 to n */
        for (int i = 1; i <= n; i++) {
            result = result.multiply(java.math.BigInteger.valueOf(i));
        }
        return result;
    }
    /// Estimates the value of "e" using a Taylor series approach:
    ///   e = sum(k=0 to n-1) of (1 / k!)
    /// This uses BigDecimal for precision.
    /// @param iterations The number of terms to sum in the series (>= 1)
    /// @return An approximation of e as a BigDecimal
    private static java.math.BigDecimal computeE(int iterations) {
        /* We'll keep an internal precision higher than we finally print */
        final int INTERNAL_PRECISION = 64;
        /* Start sum at 0 */
        java.math.BigDecimal sum = java.math.BigDecimal.ZERO;
        /* The first term: 1/0! = 1 */
        java.math.BigDecimal term = java.math.BigDecimal.ONE;
        /* Add the initial term for k=0 */
        sum = sum.add(term);
        /* Now compute terms from k=1 to k=(iterations-1) */
        for (int k = 1; k < iterations; k++) {
            /* Next term in the series is (previous term / k) */
            java.math.BigDecimal divisor = new java.math.BigDecimal(k);
            term = term.divide(divisor, INTERNAL_PRECISION, java.math.RoundingMode.HALF_EVEN);
            sum = sum.add(term);
        }
        return sum;
    }
}

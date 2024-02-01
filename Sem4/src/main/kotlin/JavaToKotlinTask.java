import java.util.Scanner;

public class JavaToKotlinTask {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first complex number");
        ComplexNumber a = ComplexNumber.create(
                scanner.nextDouble(), scanner.nextDouble()
        );
        System.out.println("Enter second complex number");
        ComplexNumber b = ComplexNumber.create(
                scanner.nextDouble(), scanner.nextDouble()
        );

        ComplexNumber c;
        try {
            c = ComplexNumber.div(a, b);
        } catch (DivisionByZeroException e) {
            System.out.println("Cannot divide by zero!");
            return;
        }

        double abs = ComplexNumber.abs(c);
        System.out.println(
                "first / second = " + c.re + " + " + c.im + "i"
        );
        System.out.println("|first / second| = " + abs);
    }
}
import java.util.Scanner;

public class Start {

    public static void main(String[] args) {
        // Pokretanje jediničnog testa prilikom pokretanja programa
        runCalculatorUnitTest();

        String Expression;
        boolean active = true;
        System.out.println("Enter expression here (type 'exit' to quit):");
        Scanner scanIn;

        while (active) {
            scanIn = new Scanner(System.in);
            Expression = scanIn.nextLine();

            if (Expression.equals("exit")) {
                scanIn.close();
                active = false;
            } else {
                System.out.println(Calculator.Run(Expression));
            }
        }
    }

    // Jedinični test (Unit Test) koji provjerava ispravnost metode za računanje
    public static void runCalculatorUnitTest() {
        System.out.println("--- POKRETANJE JEDINIČNOG TESTA ---");
        
        String testIzraz = "2+3*4";
        String stvarniRezultat = Calculator.Run(testIzraz);

        // Provjera radi li prioritet operacija ispravno (3*4=12, +2=14)
        if (stvarniRezultat != null && (stvarniRezultat.contains("14") || stvarniRezultat.equals("14.0"))) {
            System.out.println("REZULTAT: TEST PROŠAO (Metoda ispravno poštuje prioritet operacija).");
        } else {
            System.out.println("REZULTAT: TEST PAO (Očekivano: 14, Dobiveno: " + stvarniRezultat + ")");
        }
        
        System.out.println("-----------------------------------");
    }
}

}

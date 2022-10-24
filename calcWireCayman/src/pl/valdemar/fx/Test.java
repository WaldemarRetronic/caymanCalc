package pl.valdemar.fx;

/**
 * Klasa demonstruje działanie mechanizmu wywołania funkcji start() z nadklasy, oraz wywołanie funkcji
 * przesłoniętej getP() w podklasie.
 */
public class Test {
    public static void main(String[] args) {

        Test test = new Test();

        test.new B().start();

    }

    class A {

        public void start() {

            System.out.println(getP());

        }

        public int getP() {

            return 1;

        }

    }

    class B extends A {

        public int getP() {

            return 2 + super.getP();

        }

    }
}

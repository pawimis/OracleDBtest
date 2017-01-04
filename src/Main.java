import java.sql.SQLException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        int name;
        int surname;
        int agency;
        int car;
        try {
            DataBase db = new DataBase();

            System.out.println("_______________CLIENT____________________");
            db.selectFromClientDataBase().forEach(System.out::println);

            System.out.println("_______________EMPLOYEE____________________");
            db.selectFromEmployeeDataBase().forEach(System.out::println);

            System.out.println("_______________CARS____________________");
            db.selectFromCarDataBase().forEach(System.out::println);

            System.out.println("______________Variation________________");
            db.selectFromVariationDataBase().forEach(System.out::println);

            System.out.println("\n\n\n");
            System.out.println("______________INSERTS________________");

            name = rand.nextInt(Vars.NAME.length);
            surname = rand.nextInt(Vars.NAME.length);
            db.insertToEmployeeDataBase(Vars.NAME[name] + " " + Vars.SURNAME[surname], "BOSS", 1);
            name = rand.nextInt(Vars.NAME.length);
            surname = rand.nextInt(Vars.NAME.length);
            db.insertToEmployeeDataBase(Vars.NAME[name] + " " + Vars.SURNAME[surname], "BOSS", 2);
            name = rand.nextInt(Vars.NAME.length);
            surname = rand.nextInt(Vars.NAME.length);
            db.insertToEmployeeDataBase(Vars.NAME[name] + " " + Vars.SURNAME[surname], "BOSS", 3);
            name = rand.nextInt(Vars.NAME.length);
            surname = rand.nextInt(Vars.NAME.length);
            db.insertToEmployeeDataBase(Vars.NAME[name] + " " + Vars.SURNAME[surname], "BOSS", 4);

            int i = 100;
            System.out.println("_______________EMPLOYEE____________________");

            while (i > 0) {
                name = rand.nextInt(Vars.NAME.length);
                surname = rand.nextInt(Vars.NAME.length);
                agency = rand.nextInt(4) + 1;
                db.insertToEmployeeDataBase(Vars.NAME[name] + " " + Vars.SURNAME[surname], "EMPLOYEE", agency);
                System.out.println(String.valueOf(100 - i) + ". EMPLOYEE INSERT");
                i--;
            }
            System.out.println("_______________CLIENT____________________");

            i = 100;
            while (i > 0) {
                name = rand.nextInt(Vars.NAME.length);
                surname = rand.nextInt(Vars.NAME.length);
                db.insertToClientDataBase(Vars.NAME[name] + " " + Vars.SURNAME[surname]);
                System.out.println(String.valueOf(100 - i) + ". CLIENT INSERT");

                i--;
            }
            i = 100;
            System.out.println("_______________CARS____________________");

            while (i > 0) {
                agency = rand.nextInt(4) + 1;
                car = rand.nextInt(Vars.CAR.length);
                db.insertToCarDataBase(agency, Vars.CAR[car]);
                System.out.println(String.valueOf(100 - i) + ". CAR INSERT");

                i--;
            }
            i = 100;
            System.out.println("______________Variation________________");

            while (i > 0) {
                int insurance = rand.nextInt(2);
                int security = rand.nextInt(2);
                int equipment = rand.nextInt(2);
                db.insertToVariationDataBase(security == 1 ? "YES" : "NO", insurance, equipment == 1 ? "STANDARD" : "EXCLUSIVE");
                System.out.println(String.valueOf(100 - i) + ". Variation INSERT");

                i--;
            }

            System.out.println("\n\n\n");

            System.out.println("_______________CLIENT____________________");
            db.selectFromClientDataBase().forEach(System.out::println);

            System.out.println("_______________EMPLOYEE____________________");
            db.selectFromEmployeeDataBase().forEach(System.out::println);

            System.out.println("_______________CARS____________________");
            db.selectFromCarDataBase().forEach(System.out::println);

            System.out.println("______________Variation________________");
            db.selectFromVariationDataBase().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

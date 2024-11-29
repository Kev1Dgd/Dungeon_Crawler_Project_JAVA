public class Vehicle implements Engine, Drivable {

    @Override
    public void update() {
        System.out.println("Mise à jour du moteur.");
    }

    @Override
    public void drive() {
        System.out.println("Le véhicule roule.");
    }
}

package observers;

import utils.Simulation;

import java.util.Observable;
import java.util.Observer;

public final class DoctorObserver extends Observable implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Simulation simulation = (Simulation) o;
        simulation.handleDoctorsDischarge();
        this.update();
    }

    public void update() {
        this.setChanged();
        this.notifyObservers();
    }
}

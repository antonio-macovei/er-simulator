package observers;

import utils.Simulation;

import java.util.Observable;
import java.util.Observer;

public final class QueueObserver extends Observable implements Observer  {

    @Override
    public void update(Observable o, Object arg) {
        Simulation simulation = (Simulation) o;
        simulation.handleTriageQueue();
        simulation.handleExaminationQueue();
        simulation.handleInvestigationQueue();
        this.update();
    }

    public void update() {
        this.setChanged();
        this.notifyObservers();
    }
}

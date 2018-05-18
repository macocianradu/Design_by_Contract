package ObserverDP;

public interface Observable {

    void addObserver(Observer o);

    void notifyObserver(String s);
}

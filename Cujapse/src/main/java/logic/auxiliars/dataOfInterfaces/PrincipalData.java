package logic.auxiliars.dataOfInterfaces;

import java.util.ArrayList;
import java.util.List;

public class PrincipalData {
    private List <Menssage> messages;
    private ArrayList<Integer> stats;
    private String pathEscenary;

    public PrincipalData(List<Menssage> messages, ArrayList<Integer> stats, String pathEscenary) {
        this.messages = messages;
        this.stats = stats;
        this.pathEscenary = pathEscenary;
    }

    public List<Menssage> getMessages() {
        return messages;
    }

    public void setMessages(List<Menssage> messages) {
        this.messages = messages;
    }

    public ArrayList<Integer> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Integer> stats) {
        this.stats = stats;
    }

    public String getPathEscenary() {
        return pathEscenary;
    }

    public void setPathEscenary(String pathEscenary) {
        this.pathEscenary = pathEscenary;
    }
}

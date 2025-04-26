package part1;

public class Episode {
    private String name;
    private int durationSec;

    public Episode(String name, int durationSec) {
        this.name = name;
        this.durationSec = durationSec;
    }

    public String getTitle() {
        return name;
    }

    public int getRuntimeSec() {
        return durationSec;
    }

    @Override
    public String toString() {
        int mins = durationSec / 60;
        int secs = durationSec % 60;
        return "Show: " + name + " (" + mins + "m " + secs + "s)";
    }
}

package part1;

public class EpisodeView {
    private final Episode showEpisode;
    private final int introOffset;

    public EpisodeView(Episode showEpisode, int introOffset) {
        this.showEpisode = showEpisode;
        this.introOffset = introOffset;
    }

    public Episode getEpisode() {
        return showEpisode;
    }

    public int getStartOffsetSec() {
        return introOffset;
    }

    public void play() {
        System.out.println("Streaming \"" + showEpisode.getTitle() + "\" from timestamp " + 
                timeDisplay(introOffset) + " (intro skipped)");
    }

    private String timeDisplay(int seconds) {
        int mins = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }

    @Override
    public String toString() {
        return "ViewingSession: " + showEpisode.getTitle() + " (begins at " + timeDisplay(introOffset) + ")";
    }
}

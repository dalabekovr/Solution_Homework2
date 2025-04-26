package part1;

public class SkipIntroIterator {
    private final EpisodeIterator baseIterator;
    private final int introSkipSeconds;

    public SkipIntroIterator(EpisodeIterator baseIterator, int introSkipSeconds) {
        this.baseIterator = baseIterator;
        this.introSkipSeconds = introSkipSeconds;
    }

    public boolean hasMoreEpisodes() {
        return baseIterator.hasMoreEpisodes();
    }

    public EpisodeView getNextEpisode() {
        Episode episode = baseIterator.getNextEpisode();
        return new EpisodeView(episode, introSkipSeconds);
    }
}

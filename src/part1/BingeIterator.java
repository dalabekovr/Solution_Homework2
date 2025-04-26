package part1;

public class BingeIterator implements EpisodeIterator {
    private final Series tvShow;
    private int currentSeasonPos = 0;
    private EpisodeIterator activeSeasonIterator;

    public BingeIterator(Series tvShow) {
        this.tvShow = tvShow;
        if (tvShow.getSeasonCount() > 0) {
            activeSeasonIterator = tvShow.getSeason(0).createIterator();
        }
    }

    @Override
    public boolean hasMoreEpisodes() {
        if (tvShow.getSeasonCount() == 0) {
            return false;
        }

        if (activeSeasonIterator.hasMoreEpisodes()) {
            return true;
        }

        int nextSeasonPos = currentSeasonPos + 1;
        return nextSeasonPos < tvShow.getSeasonCount();
    }

    @Override
    public Episode getNextEpisode() {
        if (!activeSeasonIterator.hasMoreEpisodes()) {
            currentSeasonPos++;
            if (currentSeasonPos < tvShow.getSeasonCount()) {
                activeSeasonIterator = tvShow.getSeason(currentSeasonPos).createIterator();
            }
        }

        return activeSeasonIterator.getNextEpisode();
    }
}

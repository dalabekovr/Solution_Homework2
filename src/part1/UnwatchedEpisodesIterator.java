package part1;

import java.util.Set;

public class UnwatchedEpisodesIterator implements EpisodeIterator {
    private final EpisodeIterator sourceIterator;
    private final Set<String> viewingHistory;
    private Episode nextUnwatchedShow;

    public UnwatchedEpisodesIterator(EpisodeIterator sourceIterator, Set<String> viewingHistory) {
        this.sourceIterator = sourceIterator;
        this.viewingHistory = viewingHistory;
        findNextNewEpisode();
    }

    private void findNextNewEpisode() {
        nextUnwatchedShow = null;
        while (nextUnwatchedShow == null && sourceIterator.hasMoreEpisodes()) {
            Episode candidate = sourceIterator.getNextEpisode();
            if (!viewingHistory.contains(candidate.getTitle())) {
                nextUnwatchedShow = candidate;
            }
        }
    }

    @Override
    public boolean hasMoreEpisodes() {
        return nextUnwatchedShow != null;
    }

    @Override
    public Episode getNextEpisode() {
        Episode result = nextUnwatchedShow;
        findNextNewEpisode();
        return result;
    }

    public void markAsWatched(Episode episode) {
        viewingHistory.add(episode.getTitle());
    }
}

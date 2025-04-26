package part1;

import java.util.Random;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Season implements Iterable<Episode> {
    private final List<Episode> showCollection;
    private final int seasonId;

    public Season(int seasonId) {
        this.showCollection = new ArrayList<>();
        this.seasonId = seasonId;
    }

    public void addEpisode(Episode episode) {
        showCollection.add(episode);
    }

    public int getEpisodeCount() {
        return showCollection.size();
    }

    public int getSeasonNumber() {
        return seasonId;
    }

    public EpisodeIterator createIterator() {
        return new StandardIterator(this);
    }

    public EpisodeIterator createReverseIterator() {
        return new BackwardsIterator(this);
    }

    public EpisodeIterator createShuffleIterator(long seed) {
        return new RandomizedIterator(this, seed);
    }

    public Episode getEpisode(int index) {
        return showCollection.get(index);
    }

    @Override
    public Iterator<Episode> iterator() {
        return new StandardIterator(this);
    }

    private static class StandardIterator implements EpisodeIterator, Iterator<Episode> {
        private final Season season;
        private int position = 0;

        public StandardIterator(Season season) {
            this.season = season;
        }

        @Override
        public boolean hasMoreEpisodes() {
            return position < season.getEpisodeCount();
        }

        @Override
        public Episode getNextEpisode() {
            return season.getEpisode(position++);
        }
        
        @Override
        public boolean hasNext() {
            return hasMoreEpisodes();
        }

        @Override
        public Episode next() {
            return getNextEpisode();
        }
    }

    private static class BackwardsIterator implements EpisodeIterator {
        private final Season season;
        private int position;

        public BackwardsIterator(Season season) {
            this.season = season;
            this.position = season.getEpisodeCount() - 1;
        }

        @Override
        public boolean hasMoreEpisodes() {
            return position >= 0;
        }

        @Override
        public Episode getNextEpisode() {
            return season.getEpisode(position--);
        }
    }

    private static class RandomizedIterator implements EpisodeIterator {
        private final List<Episode> mixedEpisodes;
        private int position = 0;

        public RandomizedIterator(Season season, long seed) {
            mixedEpisodes = new ArrayList<>();
            for (int i = 0; i < season.getEpisodeCount(); i++) {
                mixedEpisodes.add(season.getEpisode(i));
            }
            Collections.shuffle(mixedEpisodes, new Random(seed));
        }

        @Override
        public boolean hasMoreEpisodes() {
            return position < mixedEpisodes.size();
        }

        @Override
        public Episode getNextEpisode() {
            return mixedEpisodes.get(position++);
        }
    }
}

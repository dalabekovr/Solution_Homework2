package part1;

import java.util.List;
import java.util.ArrayList;

public class Series {
    private final String seriesName;
    private final List<Season> seasonCollection;

    public Series(String seriesName) {
        this.seriesName = seriesName;
        this.seasonCollection = new ArrayList<>();
    }

    public String getTitle() {
        return seriesName;
    }

    public void addSeason(Season season) {
        seasonCollection.add(season);
    }

    public int getSeasonCount() {
        return seasonCollection.size();
    }

    public Season getSeason(int index) {
        return seasonCollection.get(index);
    }

    public EpisodeIterator createBingeIterator() {
        return new BingeIterator(this);
    }
}

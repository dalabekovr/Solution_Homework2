package part1;

import java.util.Set;
import java.util.HashSet;

public class git init
        Main {
    public static void main(String[] args) {
        Series betterCallSaul = createExampleShow();
        
        System.out.println("=== Standard Episode Playback ===");
        Season firstSeason = betterCallSaul.getSeason(0);
        EpisodeIterator standardIterator = firstSeason.createIterator();
        displayEpisodes(standardIterator);
        
        System.out.println("\n=== Backwards Episode Playback ===");
        EpisodeIterator backwardsIterator = firstSeason.createReverseIterator();
        displayEpisodes(backwardsIterator);
        
        System.out.println("\n=== Randomized Episode Playback ===");
        EpisodeIterator randomIterator = firstSeason.createShuffleIterator(42);
        displayEpisodes(randomIterator);
        
        System.out.println("\n=== Java For-Each Loop Playback ===");
        for (Episode episode : firstSeason) {
            System.out.println(episode.getTitle());
        }
        
        System.out.println("\n=== Full Series Binge Mode ===");
        EpisodeIterator bingeIterator = betterCallSaul.createBingeIterator();
        displayEpisodes(bingeIterator);
        
        System.out.println("\n=== Skip Intro Feature Demo ===");
        SkipIntroIterator skipIntroIterator = new SkipIntroIterator(firstSeason.createIterator(), 30);
        while (skipIntroIterator.hasMoreEpisodes()) {
            EpisodeView view = skipIntroIterator.getNextEpisode();
            view.play();
        }
        
        System.out.println("\n=== Unwatched Episodes Filter Demo ===");
        Set<String> viewHistory = new HashSet<>();

        viewHistory.add("Uno");
        viewHistory.add("Mijo");
        
        UnwatchedEpisodesIterator unwatchedIterator = new UnwatchedEpisodesIterator(
                firstSeason.createIterator(), viewHistory);
        System.out.println("Episodes you haven't seen yet:");
        while (unwatchedIterator.hasMoreEpisodes()) {
            Episode episode = unwatchedIterator.getNextEpisode();
            System.out.println(episode.getTitle());
        }
        
        if (args.length > 0 && args[0].equals("--performance-test")) {
            runPerformanceTest();
        }
    }
    
    private static Series createExampleShow() {
        Series betterCallSaul = new Series("Better Call Saul");
        
        Season firstSeason = new Season(1);
        firstSeason.addEpisode(new Episode("Uno", 53 * 60));
        firstSeason.addEpisode(new Episode("Mijo", 47 * 60));
        firstSeason.addEpisode(new Episode("Nacho", 48 * 60));
        firstSeason.addEpisode(new Episode("Hero", 48 * 60));
        firstSeason.addEpisode(new Episode("Alpine Shepherd Boy", 47 * 60));
        betterCallSaul.addSeason(firstSeason);
        
        Season secondSeason = new Season(2);
        secondSeason.addEpisode(new Episode("Switch", 47 * 60));
        secondSeason.addEpisode(new Episode("Cobbler", 46 * 60));
        secondSeason.addEpisode(new Episode("Amarillo", 46 * 60));
        secondSeason.addEpisode(new Episode("Gloves Off", 45 * 60));
        secondSeason.addEpisode(new Episode("Rebecca", 47 * 60));
        betterCallSaul.addSeason(secondSeason);
        
        return betterCallSaul;
    }
    
    private static void displayEpisodes(EpisodeIterator iterator) {
        while (iterator.hasMoreEpisodes()) {
            Episode episode = iterator.getNextEpisode();
            System.out.println(episode.getTitle());
        }
    }
    
    private static void runPerformanceTest() {
        System.out.println("\n=== Iterator Performance Benchmark (10,000 Episodes) ===");
        
        Season massiveSeason = new Season(1);
        for (int i = 1; i <= 10000; i++) {
            massiveSeason.addEpisode(new Episode("Test Episode " + i, 45 * 60));
        }
        
        long startTime = System.nanoTime();
        EpisodeIterator standardIterator = massiveSeason.createIterator();
        while (standardIterator.hasMoreEpisodes()) {
            standardIterator.getNextEpisode();
        }
        long standardTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        EpisodeIterator backwardsIterator = massiveSeason.createReverseIterator();
        while (backwardsIterator.hasMoreEpisodes()) {
            backwardsIterator.getNextEpisode();
        }
        long backwardsTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        EpisodeIterator randomIterator = massiveSeason.createShuffleIterator(42);
        while (randomIterator.hasMoreEpisodes()) {
            randomIterator.getNextEpisode();
        }
        long randomTime = System.nanoTime() - startTime;
        
        System.out.println("Performance Results (milliseconds):");
        System.out.println("Standard Iterator: " + standardTime / 1_000_000.0 + " ms");
        System.out.println("Backwards Iterator: " + backwardsTime / 1_000_000.0 + " ms");
        System.out.println("Randomized Iterator: " + randomTime / 1_000_000.0 + " ms");
        
        generateBarChart("Standard", standardTime);
        generateBarChart("Backwards", backwardsTime);
        generateBarChart("Random", randomTime);
    }
    
    private static void generateBarChart(String iteratorType, long time) {
        int barLength = (int) (time / 1_000_000 / 5);
        System.out.printf("%-10s |", iteratorType);
        for (int i = 0; i < barLength; i++) {
            System.out.print("█");
        }
        System.out.println(" " + (time / 1_000_000.0) + " ms");
    }
}

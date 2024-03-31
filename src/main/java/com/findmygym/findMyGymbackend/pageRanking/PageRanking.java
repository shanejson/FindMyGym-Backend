package com.findmygym.findMyGymbackend.pageRanking;
import java.util.ArrayList;
import java.util.List;
public class PageRanking {
    private static final double DAMPING_FACTOR = 0.85; // Damping factor, usually set between 0.85 and 0.95
    private static final double EPSILON = 0.0001; // Convergence criterion

    public static void PageRanker() {
        // Create web pages with real URLs
        WebPage fitnessCentrePage = new WebPage("https://www.uwindsor.ca/lancercentre/312/fitness-centre");
        WebPage youtubePage = new WebPage("https://www.youtube.com/");
        WebPage sharepointPage = new WebPage("https://uwin365.sharepoint.com/sites/Section_COMP8117-1-R-2024W");

        // Link web pages
        fitnessCentrePage.addLink(youtubePage);
        youtubePage.addLink(sharepointPage);
        sharepointPage.addLink(fitnessCentrePage);

        // Calculate page rank
        calculatePageRank(fitnessCentrePage, 100); // Number of iterations

        // Display page ranks
        System.out.println("Fitness Centre Page Rank: " + fitnessCentrePage.rank);
        System.out.println("YouTube Page Rank: " + youtubePage.rank);
        System.out.println("SharePoint Page Rank: " + sharepointPage.rank);
    }

    public static void calculatePageRank(WebPage startPage, int iterations) {
        // Initialize page ranks
        for (WebPage page : getAllPages(startPage)) {
            page.rank = 1.0;
        }

        // Perform iterations
        for (int i = 0; i < iterations; i++) {
            double sum = 0.0;
            for (WebPage page : getAllPages(startPage)) {
                double newRank = (1 - DAMPING_FACTOR);
                for (WebPage linkingPage : getAllPages(startPage)) {
                    if (linkingPage.links.contains(page)) {
                        newRank += DAMPING_FACTOR * (linkingPage.rank / linkingPage.links.size());
                    }
                }
                sum += Math.abs(newRank - page.rank);
                page.rank = newRank;
            }
            // Check for convergence
            if (sum < EPSILON) {
                break;
            }
        }
    }

    private static List<WebPage> getAllPages(WebPage startPage) {
        List<WebPage> allPages = new ArrayList<>();
        addPageAndLinks(startPage, allPages);
        return allPages;
    }

    private static void addPageAndLinks(WebPage page, List<WebPage> allPages) {
        if (!allPages.contains(page)) {
            allPages.add(page);
            for (WebPage link : page.links) {
                addPageAndLinks(link, allPages);
            }
        }
    }

}

class WebPage {
    String url;
    List<WebPage> links;
    double rank; // Add rank field

    public WebPage(String url) {
        this.url = url;
        this.links = new ArrayList<>();
        this.rank = 0.0; // Initialize rank
    }

    public void addLink(WebPage page) {
        links.add(page);
    }

    public String getUrl() {
        return url;
    }

    public List<WebPage> getLinks() {
        return links;
    }
}

package gui;

import enums.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;

public class AnalyticsChartPanel extends JPanel {

    private final LinkedList<Integer> totalWaitingHistory = new LinkedList<>();
    private Map<Direction, Integer> latestQueues = new EnumMap<>(Direction.class);
    private static final int MAX_SAMPLES = 40;

    public AnalyticsChartPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(700, 480));
    }

    public void addSample(Map<Direction, Integer> queueByDirection, int totalWaiting, int totalProcessed) {
        this.latestQueues = queueByDirection;

        totalWaitingHistory.add(totalWaiting);
        if (totalWaitingHistory.size() > MAX_SAMPLES) totalWaitingHistory.removeFirst();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 30;

        int barChartHeight = height / 2 - 50;
        drawSectionTitle(g2, "Current Queue by Direction", padding, 24);
        drawBarChart(g2, padding, 40, width - padding * 2, barChartHeight);

        int lineChartY = height / 2 + 20;
        drawSectionTitle(g2, "Total Waiting Vehicles — Live Trend", padding, lineChartY);
        drawLineChart(g2, padding, lineChartY + 16, width - padding * 2, height - lineChartY - 60);

        g2.dispose();
    }

    private void drawSectionTitle(Graphics2D g2, String title, int x, int y) {
        g2.setColor(new Color(60, 64, 70));
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.drawString(title, x, y);
    }

    private void drawBarChart(Graphics2D g2, int x, int y, int w, int h) {
        g2.setColor(new Color(230, 232, 236));
        g2.drawLine(x, y + h, x + w, y + h);

        Direction[] dirs = Direction.values();
        int barGap = 40;
        int barWidth = (w - barGap * (dirs.length + 1)) / dirs.length;

        int maxVal = 5;
        for (Direction d : dirs) {
            maxVal = Math.max(maxVal, latestQueues.getOrDefault(d, 0));
        }

        int i = 0;
        for (Direction d : dirs) {
            int value = latestQueues.getOrDefault(d, 0);
            int barHeight = (int) ((value / (double) maxVal) * (h - 10));
            int barX = x + barGap + i * (barWidth + barGap);
            int barY = y + h - barHeight;

            g2.setColor(colorForDirection(d));
            g2.fillRoundRect(barX, barY, barWidth, Math.max(barHeight, 2), 6, 6);

            g2.setColor(new Color(70, 74, 80));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g2.drawString(d.name(), barX + barWidth / 2 - 16, y + h + 16);
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.drawString(String.valueOf(value), barX + barWidth / 2 - 4, barY - 6);
            i++;
        }
    }

    private void drawLineChart(Graphics2D g2, int x, int y, int w, int h) {
        g2.setColor(new Color(230, 232, 236));
        g2.drawRect(x, y, w, h);

        if (totalWaitingHistory.size() < 2) return;

        int maxVal = 5;
        for (int v : totalWaitingHistory) maxVal = Math.max(maxVal, v);

        int n = totalWaitingHistory.size();
        int stepX = w / Math.max(n - 1, 1);

        g2.setColor(new Color(52, 100, 200));
        g2.setStroke(new BasicStroke(2f));

        Integer prevVal = null;
        int px = x, py = y;
        int i = 0;
        for (int val : totalWaitingHistory) {
            int cx = x + i * stepX;
            int cy = y + h - (int) ((val / (double) maxVal) * (h - 10));
            if (prevVal != null) {
                g2.drawLine(px, py, cx, cy);
            }
            px = cx;
            py = cy;
            prevVal = val;
            i++;
        }
        g2.setStroke(new BasicStroke(1f));

        g2.setColor(new Color(120, 126, 134));
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        g2.drawString("Now", x + w - 30, y + h + 14);
        g2.drawString("Earlier", x, y + h + 14);
    }

    private Color colorForDirection(Direction d) {
        return switch (d) {
            case NORTH -> new Color(52, 100, 200);
            case SOUTH -> new Color(255, 143, 0);
            case EAST -> new Color(40, 167, 69);
            case WEST -> new Color(155, 89, 182);
        };
    }
}
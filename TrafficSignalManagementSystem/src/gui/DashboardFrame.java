package gui;

import model.Intersection;
import model.Lane;
import model.TrafficSignal;
import model.TrafficStatistics;
import enums.Direction;
import enums.SignalColor;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class DashboardFrame extends JFrame {

    private final Intersection intersection;
    private final TrafficStatistics statistics;

    private final IntersectionCanvas intersectionCanvas;
    private final AnalyticsChartPanel analyticsChartPanel;
    private final EventLogPanel eventLogPanel;
    private final StatisticsPanel statisticsPanel;

    private final JLabel clockLabel;
    private final JLabel emergencyBanner;

    private Timer refreshTimer;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final Map<Direction, SignalColor> lastColors = new EnumMap<>(Direction.class);
    private final Set<Direction> lastEmergencyDirs = EnumSet.noneOf(Direction.class);

    public DashboardFrame(Intersection intersection, TrafficStatistics statistics) {
        this.intersection = intersection;
        this.statistics = statistics;

        setTitle("Traffic Signal Dashboard — " + intersection.getIntersectionId());
        setSize(820, 700);
        setMinimumSize(new Dimension(700, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        intersectionCanvas = new IntersectionCanvas(intersection);
        analyticsChartPanel = new AnalyticsChartPanel();
        eventLogPanel = new EventLogPanel();
        statisticsPanel = new StatisticsPanel();

        clockLabel = new JLabel();
        emergencyBanner = new JLabel("", SwingConstants.CENTER);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildTabs(), BorderLayout.CENTER);
        add(statisticsPanel, BorderLayout.SOUTH);

        initLastState();
        startAutoRefresh();
    }

    private void initLastState() {
        for (Direction dir : Direction.values()) {
            TrafficSignal signal = intersection.getSignal(dir);
            if (signal != null) {
                lastColors.put(dir, signal.getCurrentState().getColor());
            }
        }
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(33, 37, 41));
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("Traffic Signal Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Intersection: " + intersection.getIntersectionId());
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(new Color(180, 185, 191));

        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.add(title);
        titleBlock.add(subtitle);

        clockLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        clockLabel.setForeground(new Color(180, 185, 191));

        header.add(titleBlock, BorderLayout.WEST);
        header.add(clockLabel, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(header, BorderLayout.NORTH);

        emergencyBanner.setFont(new Font("SansSerif", Font.BOLD, 13));
        emergencyBanner.setOpaque(true);
        emergencyBanner.setBackground(new Color(220, 53, 69));
        emergencyBanner.setForeground(Color.WHITE);
        emergencyBanner.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        emergencyBanner.setVisible(false);
        wrapper.add(emergencyBanner, BorderLayout.SOUTH);

        return wrapper;
    }

    private JTabbedPane buildTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.BOLD, 12));

        tabs.addTab("Live View", intersectionCanvas);
        tabs.addTab("Analytics", analyticsChartPanel);
        tabs.addTab("Event Log", eventLogPanel);

        return tabs;
    }

    private void startAutoRefresh() {
        refreshTimer = new Timer(1000, e -> refreshDashboard());
        refreshTimer.start();
        refreshDashboard();
    }

    private void refreshDashboard() {
        clockLabel.setText(LocalTime.now().format(TIME_FORMAT));

        boolean emergencyActive = false;
        int totalWaiting = 0;
        Map<Direction, Integer> queueByDirection = new EnumMap<>(Direction.class);

        for (Direction dir : Direction.values()) {
            Lane lane = intersection.getLane(dir);
            TrafficSignal signal = intersection.getSignal(dir);
            if (lane == null || signal == null) continue;

            int queueLength = lane.getQueueLength();
            queueByDirection.put(dir, queueLength);
            totalWaiting += queueLength;

            boolean hasEmergency = lane.hasEmergencyVehicle();
            emergencyActive = emergencyActive || hasEmergency;

            detectAndLogChanges(dir, signal.getCurrentState().getColor(), hasEmergency, queueLength);
        }

        emergencyBanner.setVisible(emergencyActive);
        if (emergencyActive) {
            emergencyBanner.setText("⚠ EMERGENCY VEHICLE PRIORITY OVERRIDE ACTIVE");
        }

        statisticsPanel.updateStats(statistics);
        analyticsChartPanel.addSample(queueByDirection, totalWaiting, statistics.getTotalVehiclesProcessed());
    }

    private void detectAndLogChanges(Direction dir, SignalColor currentColor, boolean hasEmergency, int queueLength) {
        SignalColor previousColor = lastColors.get(dir);
        if (previousColor != currentColor) {
            String reason = hasEmergency ? "EMERGENCY OVERRIDE" : "scheduled";
            eventLogPanel.log("Signal " + dir + " -> " + currentColor + "  (" + reason + ", queue=" + queueLength + ")");
            lastColors.put(dir, currentColor);
        }

        boolean wasEmergency = lastEmergencyDirs.contains(dir);
        if (hasEmergency && !wasEmergency) {
            eventLogPanel.log("Emergency vehicle detected at " + dir + " -- requesting signal override");
            lastEmergencyDirs.add(dir);
        } else if (!hasEmergency && wasEmergency) {
            eventLogPanel.log("Emergency cleared at " + dir + " -- resuming normal scheduling");
            lastEmergencyDirs.remove(dir);
        }
    }

    public void stopRefreshing() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
        intersectionCanvas.stopAnimation();
    }
}
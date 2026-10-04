package gui;

import model.TrafficStatistics;

import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {

    private final JLabel processedValue;
    private final JLabel maxQueueValue;
    private final JLabel emergencyValue;

    public StatisticsPanel() {
        setLayout(new GridLayout(1, 3, 20, 0));
        setBackground(new Color(248, 249, 251));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(225, 228, 232)),
                BorderFactory.createEmptyBorder(14, 20, 14, 20)));

        processedValue = new JLabel();
        maxQueueValue = new JLabel();
        emergencyValue = new JLabel();

        add(buildMetric("VEHICLES PROCESSED", processedValue, new Color(40, 167, 69)));
        add(buildMetric("MAX QUEUE OBSERVED", maxQueueValue, new Color(255, 143, 0)));
        add(buildMetric("EMERGENCY OVERRIDES", emergencyValue, new Color(220, 53, 69)));

        updateStats(new TrafficStatistics());
    }

    private JPanel buildMetric(String title, JLabel valueLabel, Color accentColor) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        titleLabel.setForeground(new Color(130, 136, 145));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(valueLabel);
        return panel;
    }

    public void updateStats(TrafficStatistics stats) {
        processedValue.setText(String.valueOf(stats.getTotalVehiclesProcessed()));
        maxQueueValue.setText(String.valueOf(stats.getMaxQueueLength()));
        emergencyValue.setText(String.valueOf(stats.getEmergencyOverrideCount()));
    }
}
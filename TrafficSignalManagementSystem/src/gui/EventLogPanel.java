package gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventLogPanel extends JPanel {

    private final JTextArea logArea;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final int MAX_LINES = 300;

    public EventLogPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setBackground(new Color(250, 250, 251));
        logArea.setForeground(new Color(50, 54, 60));

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 223, 228)));
        add(scrollPane, BorderLayout.CENTER);

        log("Dashboard initialized. Monitoring started.");
    }

    public void log(String message) {
        String timestamp = LocalTime.now().format(TIME_FORMAT);
        logArea.append("[" + timestamp + "]  " + message + "\n");

        if (logArea.getLineCount() > MAX_LINES) {
            try {
                int endOfFirstLine = logArea.getLineEndOffset(0);
                logArea.replaceRange("", 0, endOfFirstLine);
            } catch (Exception ignored) {}
        }

        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}
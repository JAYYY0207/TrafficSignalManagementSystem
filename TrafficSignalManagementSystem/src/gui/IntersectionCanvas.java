package gui;

import model.Intersection;
import model.Lane;
import model.TrafficSignal;
import model.Vehicle;
import enums.Direction;
import enums.SignalColor;
import enums.VehicleCategory;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IntersectionCanvas extends JPanel {

    private final Intersection intersection;
    private int phase = 0;
    private Timer animationTimer;

    private static final int LANE_WIDTH = 90;
    private static final int LANE_LENGTH = 190;
    private static final int INTERSECTION_HALF = 60;
    private static final int MAX_VISIBLE_VEHICLES = 7;
    private static final double EASE_FACTOR = 0.18;

    private final Map<String, Double> vehicleOffsets = new HashMap<>();

    public IntersectionCanvas(Intersection intersection) {
        this.intersection = intersection;
        setBackground(new Color(245, 246, 248));
        setPreferredSize(new Dimension(720, 500));

        animationTimer = new Timer(90, e -> {
            phase++;
            advancePositions();
            repaint();
        });
        animationTimer.start();
    }

    public void stopAnimation() {
        animationTimer.stop();
    }

    private void advancePositions() {
        Set<String> currentIds = new HashSet<>();

        for (Direction dir : Direction.values()) {
            Lane lane = intersection.getLane(dir);
            TrafficSignal signal = intersection.getSignal(dir);
            if (lane == null || signal == null) continue;

            List<Vehicle> vehicles = new ArrayList<>(lane.getVehicleQueue());
            boolean isGreen = signal.getCurrentState().getColor() == SignalColor.GREEN;

            double cumulative = isGreen ? 8 : 24;
            for (Vehicle v : vehicles) {
                double length = lengthFor(v.getCategory());
                double target = cumulative + length / 2.0;
                cumulative += length + 8;

                currentIds.add(v.getId());
                double current = vehicleOffsets.getOrDefault(v.getId(), target);
                double updated = current + (target - current) * EASE_FACTOR;
                vehicleOffsets.put(v.getId(), updated);
            }
        }

        vehicleOffsets.keySet().retainAll(currentIds);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        drawIntersectionCore(g2, cx, cy);

        drawApproach(g2, Direction.NORTH, cx, cy - INTERSECTION_HALF, 0);
        drawApproach(g2, Direction.SOUTH, cx, cy + INTERSECTION_HALF, 180);
        drawApproach(g2, Direction.EAST, cx + INTERSECTION_HALF, cy, 90);
        drawApproach(g2, Direction.WEST, cx - INTERSECTION_HALF, cy, -90);

        g2.dispose();
    }

    private void drawIntersectionCore(Graphics2D g2, int cx, int cy) {
        g2.setColor(new Color(70, 74, 80));
        g2.fillRect(cx - INTERSECTION_HALF, cy - INTERSECTION_HALF, INTERSECTION_HALF * 2, INTERSECTION_HALF * 2);

        g2.setColor(new Color(255, 255, 255, 140));
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1, new float[]{6, 6}, 0));
        g2.drawRect(cx - INTERSECTION_HALF, cy - INTERSECTION_HALF, INTERSECTION_HALF * 2, INTERSECTION_HALF * 2);
        g2.setStroke(new BasicStroke(1));

        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        g2.setColor(new Color(200, 205, 212));
        g2.drawString(intersection.getIntersectionId(), cx - 14, cy + 4);
    }

    private void drawApproach(Graphics2D g2, Direction direction, int stopX, int stopY, int rotationDegrees) {
        Lane lane = intersection.getLane(direction);
        TrafficSignal signal = intersection.getSignal(direction);
        if (lane == null || signal == null) return;

        AffineTransform original = g2.getTransform();
        g2.translate(stopX, stopY);
        g2.rotate(Math.toRadians(rotationDegrees));

        g2.setColor(new Color(60, 63, 68));
        g2.fillRect(-LANE_WIDTH / 2, -LANE_LENGTH, LANE_WIDTH, LANE_LENGTH);

        g2.setColor(new Color(255, 255, 255, 90));
        for (int y = -LANE_LENGTH + 10; y < 0; y += 20) {
            g2.fillRect(-2, y, 4, 10);
        }

        g2.setColor(Color.WHITE);
        g2.fillRect(-LANE_WIDTH / 2 + 6, -6, LANE_WIDTH - 12, 4);

        Color signalColor = mapColor(signal.getCurrentState().getColor());
        int lightX = LANE_WIDTH / 2 + 10;
        int lightY = -24;
        g2.setColor(new Color(30, 30, 30));
        g2.fillOval(lightX - 2, lightY - 2, 26, 26);
        g2.setColor(signalColor);
        g2.fillOval(lightX, lightY, 22, 22);

        List<Vehicle> vehicles = new ArrayList<>(lane.getVehicleQueue());
        int visibleCount = Math.min(vehicles.size(), MAX_VISIBLE_VEHICLES);

        for (int i = 0; i < visibleCount; i++) {
            Vehicle v = vehicles.get(i);
            double offset = vehicleOffsets.getOrDefault(v.getId(), 30.0 + i * 26);
            double wobble = Math.sin((phase + i * 3) * 0.3) * 1.3;
            int carY = (int) -offset;
            int carX = (int) wobble;
            drawVehicle(g2, carX, carY, v.getCategory());
        }

        if (vehicles.size() > MAX_VISIBLE_VEHICLES) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            g2.drawString("+" + (vehicles.size() - MAX_VISIBLE_VEHICLES), -12, -LANE_LENGTH + 14);
        }

        g2.setTransform(original);

        drawUprightLabel(g2, stopX, stopY, rotationDegrees, signal.getCurrentState().getRemainingSeconds() + "s");

        if (lane.hasEmergencyVehicle()) {
            drawEmergencyLabel(g2, stopX, stopY, rotationDegrees);
        }
    }

    private double lengthFor(VehicleCategory category) {
        return switch (category) {
            case BIKE -> 14;
            case CAR -> 24;
            case BUS -> 34;
            case AMBULANCE -> 26;
        };
    }

    private void drawVehicle(Graphics2D g2, int x, int y, VehicleCategory category) {
        switch (category) {
            case BIKE -> drawBike(g2, x, y);
            case CAR -> drawCar(g2, x, y);
            case BUS -> drawBus(g2, x, y);
            case AMBULANCE -> drawAmbulance(g2, x, y);
        }
    }

    private void drawCar(Graphics2D g2, int x, int y) {
        int w = 24, h = 15;
        g2.setColor(new Color(52, 100, 200));
        g2.fillRoundRect(x - w / 2, y - h / 2, w, h, 5, 5);
        g2.setColor(new Color(210, 225, 250));
        g2.fillRoundRect(x - w / 2 + 4, y - h / 2 + 3, w - 8, h - 8, 3, 3);
        g2.setColor(new Color(30, 60, 130));
        g2.drawRoundRect(x - w / 2, y - h / 2, w, h, 5, 5);
    }

    private void drawBike(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(60, 63, 68));
        g2.fillOval(x - 6, y - 5, 5, 5);
        g2.fillOval(x + 1, y - 5, 5, 5);
        g2.setColor(new Color(230, 126, 34));
        g2.fillRect(x - 3, y - 7, 6, 4);
    }

    private void drawBus(Graphics2D g2, int x, int y) {
        int w = 30, h = 18;
        g2.setColor(new Color(255, 174, 0));
        g2.fillRoundRect(x - w / 2, y - h / 2, w, h, 4, 4);
        g2.setColor(new Color(60, 60, 60));
        for (int wx = -w / 2 + 4; wx < w / 2 - 2; wx += 7) {
            g2.fillRect(x + wx, y - h / 2 + 3, 4, h - 6);
        }
        g2.setColor(new Color(150, 100, 0));
        g2.drawRoundRect(x - w / 2, y - h / 2, w, h, 4, 4);
    }

    private void drawAmbulance(Graphics2D g2, int x, int y) {
        int w = 26, h = 16;
        boolean blinkOn = (phase / 3) % 2 == 0;
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x - w / 2, y - h / 2, w, h, 5, 5);
        g2.setColor(blinkOn ? new Color(220, 53, 69) : new Color(150, 30, 40));
        g2.fillRect(x - 3, y - h / 2 + 2, 6, h - 4);
        g2.fillRect(x - w / 2 + 2, y - 2, w - 4, 4);
        g2.setColor(blinkOn ? new Color(52, 100, 220) : new Color(30, 60, 130));
        g2.fillOval(x - w / 2 - 2, y - h / 2 - 2, 5, 5);
        g2.fillOval(x + w / 2 - 3, y - h / 2 - 2, 5, 5);
        g2.setColor(new Color(40, 40, 40));
        g2.drawRoundRect(x - w / 2, y - h / 2, w, h, 5, 5);
    }

    private void drawUprightLabel(Graphics2D g2, int stopX, int stopY, int rotationDegrees, String text) {
        int offset = 34;
        int lx, ly;
        switch (rotationDegrees) {
            case 0 -> { lx = stopX - 10; ly = stopY - offset; }
            case 180 -> { lx = stopX - 10; ly = stopY + offset + 10; }
            case 90 -> { lx = stopX + offset - 10; ly = stopY + 4; }
            default -> { lx = stopX - offset - 20; ly = stopY + 4; }
        }
        g2.setColor(new Color(90, 96, 105));
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        g2.drawString(text, lx, ly);
    }

    private void drawEmergencyLabel(Graphics2D g2, int stopX, int stopY, int rotationDegrees) {
        int lx, ly;
        switch (rotationDegrees) {
            case 0 -> { lx = stopX - 45; ly = stopY - 110; }
            case 180 -> { lx = stopX - 45; ly = stopY + 130; }
            case 90 -> { lx = stopX + 70; ly = stopY - 70; }
            default -> { lx = stopX - 195; ly = stopY - 70; }
        }
        g2.setColor(new Color(220, 53, 69));
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.drawString("EMERGENCY", lx, ly);
    }

    private Color mapColor(SignalColor color) {
        return switch (color) {
            case RED -> new Color(220, 53, 69);
            case YELLOW -> new Color(255, 193, 7);
            case GREEN -> new Color(40, 167, 69);
        };
    }
}
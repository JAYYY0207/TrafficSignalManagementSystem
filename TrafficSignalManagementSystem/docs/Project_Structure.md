# Traffic Signal Management System — Project Structure

**IDE:** IntelliJ IDEA (plain Java project, no build tool)
**Language:** Java
**Type:** Simulation-based desktop application (Swing GUI)

---

## 1. Folder Structure

```
TrafficSignalManagementSystem/
│
├── src/
│   ├── main/
│   │   └── Main.java
│   │
│   ├── model/
│   │   ├── Vehicle.java
│   │   ├── VehicleType.java
│   │   ├── Lane.java
│   │   ├── TrafficSignal.java
│   │   ├── Intersection.java
│   │   ├── TrafficStatistics.java
│   │   └── SignalState.java
│   │
│   ├── repository/
│   │   ├── VehicleRepository.java
│   │   ├── LaneRepository.java
│   │   ├── SignalRepository.java
│   │   └── ReportRepository.java
│   │
│   ├── service/
│   │   ├── VehicleService.java
│   │   ├── TrafficService.java
│   │   ├── SignalService.java
│   │   ├── EmergencyService.java
│   │   ├── PeakHourService.java
│   │   ├── DensityService.java
│   │   └── SimulationService.java
│   │
│   ├── controller/
│   │   ├── SimulationController.java
│   │   ├── SignalController.java
│   │   ├── TrafficController.java
│   │   └── DashboardController.java
│   │
│   ├── threads/
│   │   ├── VehicleGeneratorThread.java
│   │   ├── SignalTimerThread.java
│   │   ├── SimulationClock.java
│   │   └── AnalyticsThread.java
│   │
│   ├── algorithm/
│   │   ├── DensityCalculator.java
│   │   ├── SignalScheduler.java
│   │   ├── EmergencyPriorityAlgorithm.java
│   │   └── PeakHourAlgorithm.java
│   │
│   ├── gui/
│   │   ├── SimulationFrame.java
│   │   ├── DashboardFrame.java
│   │   ├── IntersectionPanel.java
│   │   ├── VehiclePanel.java
│   │   ├── ControlPanel.java
│   │   ├── StatisticsPanel.java
│   │   └── SettingsPanel.java
│   │
│   ├── utils/
│   │   ├── Constants.java
│   │   ├── RandomUtils.java
│   │   ├── TimeUtils.java
│   │   ├── ValidationUtils.java
│   │   └── FileUtils.java
│   │
│   ├── exception/
│   │   ├── InvalidSignalException.java
│   │   ├── VehicleNotFoundException.java
│   │   └── TrafficException.java
│   │
│   ├── analytics/
│   │   ├── WaitingTimeAnalyzer.java
│   │   ├── QueueAnalyzer.java
│   │   ├── CongestionAnalyzer.java
│   │   ├── ReportGenerator.java
│   │   └── Logger.java
│   │
│   └── enums/
│       ├── Direction.java
│       ├── VehicleCategory.java
│       └── SignalColor.java
│
├── test/
│   ├── model/
│   │   └── VehicleTest.java
│   ├── service/
│   │   ├── VehicleServiceTest.java
│   │   ├── SignalServiceTest.java
│   │   └── EmergencyServiceTest.java
│   ├── algorithm/
│   │   ├── DensityCalculatorTest.java
│   │   └── EmergencyPriorityAlgorithmTest.java
│   └── controller/
│       └── TrafficControllerTest.java
│
├── resources/
│   ├── icons/
│   │   ├── car.png
│   │   ├── bike.png
│   │   ├── bus.png
│   │   ├── ambulance.png
│   │   ├── signal_red.png
│   │   ├── signal_yellow.png
│   │   └── signal_green.png
│   └── config.properties
│
├── docs/
│   ├── SRS.pdf
│   ├── UML_Diagram.png
│   ├── Flowchart.png
│   └── Project_Report.pdf
│
├── out/                      (IntelliJ build output — gitignored)
├── README.md
├── LICENSE
└── .gitignore
```

---

## 2. Package Responsibility Table

| Package | Responsibility | Contains "Rules"? |
|---|---|---|
| `model` | Plain data objects representing real-world entities (Vehicle, Lane, Signal, etc.) | No — data only |
| `repository` | Holds current in-memory state (active vehicles, lanes, signals, saved reports) | No — storage only |
| `service` | Orchestrates business logic; the "glue" between algorithm, repository, and threads | Yes — coordination logic |
| `controller` | Bridges GUI actions to the service layer | No — delegation only |
| `threads` | Background execution units (clock ticks, vehicle spawning, signal countdowns) | No — execution only |
| `algorithm` | Pure decision-making logic (what should happen next) | Yes — decision logic |
| `analytics` | Pure calculation & reporting logic (what has happened / measurements) | Yes — measurement logic |
| `gui` | Swing UI components (frames, panels) | No — presentation only |
| `utils` | Stateless helper/utility functions | No |
| `exception` | Custom checked/unchecked exceptions | No |
| `enums` | Fixed constant sets (Direction, VehicleCategory, SignalColor) | No |

**Golden rule to avoid overlap:**
- Decides *what happens next* → `algorithm/`
- Measures *what already happened* → `analytics/`
- Holds *current state* → `repository/`
- Coordinates *the above three* → `service/`

---

## 3. Data Flow

```
GUI (Panels)
     ↓ user actions
Controller  (translates UI events into service calls)
     ↓
Service  (orchestrates logic, owns background threads)
     ↓                              ↓
Algorithm                     Analytics
(decision logic)          (measurement/reporting)
     ↓                              ↓
Repository  (current in-memory state)
     ↓
Model  (data objects)
```

---

## 4. IntelliJ Setup Checklist

| Step | Action |
|---|---|
| 1 | Right-click `src` → **Mark Directory as → Sources Root** |
| 2 | Right-click `test` → **Mark Directory as → Test Sources Root** |
| 3 | Right-click `resources` → **Mark Directory as → Resources Root** |
| 4 | File → Project Structure → Libraries → attach **JUnit** (bundled with IntelliJ) |
| 5 | Load icons via `getClass().getResourceAsStream("/icons/car.png")` — never hardcoded paths |
| 6 | Add `.idea/` and `out/` to `.gitignore` |
| 7 | Ensure every class's `package` statement matches its folder (e.g., `package service;` for files inside `src/service/`) |

---

## 5. Notes

- No Maven/Gradle used — plain IntelliJ Java project, per class submission requirement.
- `test/` package structure mirrors `src/` so JUnit can auto-discover tests correctly.
- `lib/` folder removed — not needed unless external `.jar` files are added manually.
- `.vscode/` folder removed — irrelevant once developing in IntelliJ.

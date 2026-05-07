# 1st meeting with "product owner"

### ⚙️ Struktur & Daten-Konzept
*   **Grundaufbau:** Reales System und Simulation sollen parallel nebeneinander laufen können.
*   **Design Patterns:** Suche nach passenden Mustern (z.B. **Adapter** oder **Decorator**), um verschiedene Statusvarianten und Datenquellen sauber zu trennen.
*   **Datengrundlage:** 
    *   Nutzung der Daten/Analysen vom Vorjahr (ca. 20-Minuten-Datensatz).
    *   Ergänzung durch virtuelle Werte ist erlaubt.
*   **Daten-Wiedergabe:** Monitoring erfolgt über eine Endlosschleife; Daten werden neu abgespielt (ggf. mit geänderter Reihenfolge) und mit aktuellen Timestamps versehen.
*   **Performance:** Keine harten Echtzeitanforderungen; Anfragen eher auf Echtdaten oder aggregierte Daten vom Digitalen Zwilling (nicht direkt auf die Rohdaten).

### 🖥️ UI: Monitoring
*   **Ansicht:** High-Level-Überblick über die gesamte Anlage (Wo ist gerade was?).
*   **Details:** Status des aktuellen Prozesses und Sensordaten einzelner Maschinen anzeigen.
*   **Umsetzung:** Gestaltung ist offen (Popups, neue Fenster etc.), Hauptsache die Infos sind zugänglich.

### 🧪 UI: Simulation
*   **Steuerung:** Parameter für die Simulation vorgeben und Starten.
*   **Feedback:** Klare Anzeige, dass eine Simulation gerade läuft.
*   **Analyse:** Möglichkeit, Ergebnisse verschiedener Simulationsläufe (mit unterschiedlichen Parametern) zu vergleichen.

### 🛠️ UI: Maintenance & Job Scheduling
*   **Dateneingabe:** Entweder direkt in die Daten oder über Eingabefelder in der Oberfläche.
*   **Visualisierung:** Zeitplan mit Slots und Maschineninfos anzeigen.
*   **Funktion:** Button zum Starten der Optimierung und Anzeige der Ergebnisse.
*   **Transfer:** Historische Maintenance-Daten speichern, um sie ins reale System zu übernehmen.

### 👤 Nutzer-Interaktion & Rechte
*   **Klärungsbedarf:** Festlegen, welche Elemente nur visualisiert werden, wo Input möglich ist und wo aktive Steuerungselemente (z.B. Löschen) sitzen.
*   **Berechtigung:** Kein komplexes Rechtesystem nötig; ein einfaches Panel mit Log-In reicht aus (Single-User Fokus).

### 📅 Projekt-Orga (Backlog & Termine)
*   **Milestone:** Product Backlog erstellen (kann später noch verfeinert werden).
*   **Workflow:** Sprints so vorbereiten, dass der Übergang flüssig ist.
*   **Deadline:** Liste der Anforderungen muss bis zum **04.05.** fertig sein.
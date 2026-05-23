### Zwischenstand

- **Sprint 0:** Meeting-Termine festgelegt, Gradle und MontiGem laufen bei allen, mit Git vertraut gemacht, Einlesen ins MontiGem-Tutorial.
- **Product Backlog:** aktuell noch kurz.
- **Sprint 1:** Architekturüberblick, UI-Mockups, Problem beim Login-Modul.
- **Data Output:** funktioniert grundsätzlich, Docker-Themen sollen gezeigt werden.

### Offene Fragen und geklärte Punkte

- **Dokumentation für Plugins/Module (insbesondere Login-Modul):**
  - Wunsch nach allgemeinem Zugriff auf UMLP-Repos, damit wir selbst in den Code schauen können.
  - Wunsch nach einem größeren Beispiel bzw. Beispielcode.

- **Sensordaten:**
  - Frage: Sensordaten nur für Simulationen erfinden oder auch fürs Monitoring echter Daten?
  - Antwort: Nur für Simulation ist okay.

- **Übernahme in echte Daten (Scheduling):**
  - Frage: Wie ist das genau gemeint, z. B. als CSV-Export zum späteren Import?
  - Antwort: Export als CSV reicht.

- **Rückblick auf Gruppe vom letzten Jahr (Monitoring und Simulation nicht parallel):**
  - Frage nach dem genauen Problem.
  - Antwort: Schritt für Schritt vorgehen, zunächst nur Monitoring oder Simulation-Runs.

### Risiken und Einschätzung

- Im Team gibt es unterschiedlich intensive Java-Erfahrung (verschiedene Programmierkurse) und unterschiedliche Voraussetzungen bei Tools wie Git.
- Es ist aktuell unklar, ob in einer relativ komplexen Umgebung schnell genug Lernfortschritt und Umsetzungsgeschwindigkeit aufgebaut werden können, um alle Anforderungen vollständig umzusetzen.
- Das wird sich mit der Zeit zeigen. Wir halten den PO dazu auf dem Laufenden.
- Aktueller Plan: Fokus auf Monitoring, dann Simulation, den dritten Aspekt später betrachten.

### TODO

- Architektur anpassen.
- Datenstruktur erweiterbar gestalten.
- Implementierungsaufgaben in kleinere Aufgaben runterbrechen.
- Pair Programming einsetzen.
- Umfang klären: gar kein Scheduling oder nur Teilbereich ist okay. Ziel ist insgesamt etwas Nettes und Rundes mit schöner Funktionalität sowie klaren Vor- und Nachteilen von Designentscheidungen. Simulation etwas ausbauen ist schon mehr als genug.
- Fokus auf Monitoring, Klassendiagramm fürs Monitoring als erster Durchstich.

### Weitere Hinweise

- Es gibt keine Erwartung, dass von Anfang an alles funktioniert, Einarbeitung ist eingeplant.
- Kein Login mehr.

Requirements fürs Seminar

Anforderungen in drei Blöcken:
Allgemein
Eher Single-User, kein Permissions-System notwendig
Grundlage: MQTT-Aufnahme der Daten aus echter Produktionsanlage → Datenanalyse mit Ergebnissen vom letzten Jahr darf wiederverwendet werden
Parallel Simulation und. Monitoring: Design Pattern dafür noch suchen
Monitoring
Aktuellen Status sehen, wo sind Pakete in den Maschinen, Schritte verfolgen können
Aufnahme von echter Produktionsanlage neu abspielen (Endlosschleife, evtl. In anderer Reihenfolge)
UI: Gesamtüberblick, einzelne Sensordaten für Maschinen genauer
Simulation
Vergleichen von Vorherigen Durchläufen mit aktuellen
Wartung/Ausfall von Maschinen simulieren
UI:
Parameter-Eingabe
Anzeige laufender Simulation (also, dass man sieht, dass es läuft(?))
Speicherung von Simulationsergebnissen
Ergebnisvergleich
Scheduling
Zeitplan slot die mit buttons aktivierbar und zum starten nach zeit sind
Wartungsfenster in Zeitplan mit aufnehmen, Parameter usw. Ändern
Nachfrage, weitere externe Parameter die den Zeitplan und Parameter der Anlage beeinflussen
Zeitpläne/Intervalle in speicherbaren Format, Möglichkeit zur Übernahme in die echten Daten

Wichtigste Learnings aus der Sprint Retrospektive
Mehr Zeit notwendig zum Einrichten der Entwicklungsumgebung und v.a. MontiGem als ursprünglich erwartet
Unterschiedliche Erfahrung der Teammitglieder bzgl. verschiedener Technologien (z.B. Gradle, Git, usw.)
MontiGem ist ein externer Faktor, schwer beeinflussbar → Problem mit den Zugangsdaten lässt sich nicht von uns lösen, nur extern
An vorhandenen Systemen orientieren, falls diese existieren, Ergebnisse nutzen für Verbesserungen und Vermeidung von Fehlern (siehe Projekt letztes Jahr, keine Möglichkeit, Monitoring/Simulation parallel auszuführen, usw.)


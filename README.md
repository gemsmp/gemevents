# GemEvents

## Übersicht

GemEvents ist ein Minecraft-Plugin, das automatische Events für Spieler auf deinem Server bereitstellt. Diese Events bieten Abwechslung und Spaß für deine Spieler und fördern die Interaktion zwischen ihnen. Aktuell bietet das Plugin ein Assassinen-Event, bei dem Spieler gegeneinander antreten.

## Events

### Assassinen-Event

Beim Assassinen-Event geht es darum, als erster Spieler einen anderen Spieler zu besiegen. Der Gewinner erhält eine Belohnung.

**Ablauf:**
1. Das Event wird angekündigt und ein 60-sekündiger Countdown beginnt.
2. Nach dem Countdown startet das eigentliche Event, das 60 Minuten dauert.
3. Während des Events wird ein roter Boss-Balken angezeigt, der die verbleibende Zeit zeigt.
4. Der erste Spieler, der einen anderen Spieler besiegt, gewinnt das Event.
5. Alle Spieler werden über den Gewinner informiert.

## Befehle

Das Plugin bietet folgende Befehle:

| Befehl | Beschreibung | Berechtigung |
|--------|--------------|--------------|
| `/startassassinationevent` | Startet manuell ein Assassinen-Event | Operator |

## Automatische Event-Auslösung

Events können auf zwei Arten ausgelöst werden:

1. **Manuell**: Ein Server-Operator kann jederzeit ein Event mit dem Befehl `/startassassinationevent` starten.

2. **Automatisch**: Das Plugin prüft alle 5 Minuten, ob ein Event gestartet werden soll. Dabei gelten folgende Bedingungen:
   - Es müssen mindestens 4 Spieler online sein.
   - Es darf kein anderes Event bereits laufen.
   - Es gibt eine 5% Chance, dass ein Event startet.

## Event-Ablauf

Wenn ein Event ausgelöst wird (manuell oder automatisch), läuft es wie folgt ab:

1. **Ankündigung**: Ein 60-sekündiger Countdown beginnt, der allen Spielern als gelber Boss-Balken angezeigt wird.
2. **Event-Start**: Nach dem Countdown beginnt das eigentliche Event (aktuell nur das Assassinen-Event).
3. **Event-Ende**: Das Event endet, wenn entweder ein Spieler gewinnt oder die Zeit abläuft (60 Minuten).

## Hinweise

- Während ein Event läuft, kann kein weiteres Event gestartet werden.
- Spieler, die während eines Events den Server betreten, werden über das laufende Event informiert.
- Der Boss-Balken zeigt immer die verbleibende Zeit des Events an.
- Beim Assassinen-Event muss ein Spieler direkt von einem anderen Spieler besiegt werden. Todesfälle durch andere Ursachen (z.B. Fallschaden, Monster) zählen nicht.
# Fuel Consumption Calculator - Database Integration

Tämä on JavaFX-pohjainen polttoainelaskuri, joka tukee neljää eri kieltä (EN, FR, JP, IR). 
Sovellus hakee kaikki käyttöliittymän tekstit MariaDB-tietokannasta ja tallentaa jokaisen 
[cite_start]laskutoimituksen historian tietokantaan[cite: 5, 8].

## Vaatimukset
* Java 17
* Maven
* [cite_start]MariaDB / MySQL palvelin tai Docker [cite: 108, 111]

## Tietokannan asennus
1. Varmista, että MariaDB-palvelimesi on käynnissä.
2. [cite_start]Aja projektin juuresta löytyvä `schema.sql` tiedosto SQL-asiakasohjelmallasi (esim. HeidiSQL tai MariaDB CLI)[cite: 107, 119].
   - Tämä luo tietokannan `fuel_calculator_localization`.
   - [cite_start]Se luo taulut `localization_strings` ja `calculation_records`[cite: 13, 19, 29].
   - [cite_start]Se lisää tarvittavat kielikäännökset automaattisesti neljälle kielelle[cite: 115].

## Sovelluksen konfigurointi
Tietokantayhteyden asetukset löytyvät tiedostosta:
[cite_start]`src/main/java/org/example/DatabaseConnection.java`[cite: 56, 120].

Oletusasetukset:
* [cite_start]**URL:** jdbc:mariadb://localhost:3306/fuel_calculator_localization [cite: 58, 65]
* [cite_start]**Käyttäjä:** root [cite: 59]
* [cite_start]**Salasana:** Abbuusi (vaihda tämä tarvittaessa omaasi) [cite: 60, 66]

## Käynnistys
Voit ajaa sovelluksen Mavenilla komennolla:
```bash
mvn javafx:run

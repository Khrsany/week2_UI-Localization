# Käytetään virallista Maven-kuvaa, jossa on Java 17
FROM maven:3.9.6-eclipse-temurin-17

# Asetetaan työskentelyhakemisto kontin sisällä
WORKDIR /app

# Kopioidaan pom.xml alikansiosta
COPY UI-Localization/pom.xml .

# Ladataan riippuvuudet valmiiksi
RUN mvn dependency:go-offline

# Kopioidaan lähdekoodit alikansiosta
COPY UI-Localization/src ./src

# Rakennetaan sovellus jar-tiedostoksi
RUN mvn clean package -DskipTests

# Määritetään komento sovelluksen ajamiseen
CMD ["mvn", "javafx:run"]
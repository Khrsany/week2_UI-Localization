# Käytetään virallista Maven-kuvaa, jossa on Java 17
FROM maven:3.9.6-eclipse-temurin-17

# Asetetaan työskentelyhakemisto kontin sisällä
WORKDIR /app

# Kopioidaan pom.xml ja asennetaan riippuvuudet (nopeuttaa seuraavia build-kertoja)
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopioidaan lähdekoodit
COPY src ./src

# Rakennetaan sovellus jar-tiedostoksi (skitataan testit nopeuden takia)
RUN mvn clean package -DskipTests

# Määritetään komento, joka kertoo miten sovellus ajettaisiin 
# (JavaFX:n tapauksessa tämä on usein dokumentatiivinen Dockerissa)
CMD ["mvn", "javafx:run"]
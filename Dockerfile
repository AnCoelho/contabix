# Usar uma imagem base do OpenJDK
FROM openjdk:17.0.2-oracle AS build

# Setar o diretório de trabalho
WORKDIR /app

# Copiar o arquivo pom.xml e baixar as dependências
COPY pom.xml ./
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Fase de execução
FROM openjdk:17-slim

# Copiar o jar gerado da fase anterior
COPY --from=build /app/target/*.jar app.jar

# Expor a porta que o aplicativo vai rodar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]
# Aplikacja do Pobierania Statystyk Użytkowników GitHub

Aplikacja ta pobiera i oblicza statystyki dla użytkowników GitHub na podstawie ich informacji profilowych. Wykorzystuje API GitHub do pobierania danych użytkownika oraz przeprowadza obliczenia w celu wygenerowania odpowiednich statystyk.

## Spis treści
1. [Opis ogólny](#opis-ogólny)
2. [Jak uruchomić aplikację](#jak-uruchomić-aplikację)
3. [Dostęp do aplikacji](#dostęp-do-aplikacji)
4. [Struktura projektu](#struktura-projektu)
5. [Klasy i ich funkcje](#klasy-i-ich-funkcje)
6. [Konfiguracja](#konfiguracja)
7. [Autorzy](#autorzy)

## Opis ogólny
Projekt stanowi część aplikacji webowej umożliwiającej pobieranie i obliczanie informacji o użytkownikach platformy GitHub. Wykorzystuje ono API GitHub do pobierania danych użytkownika oraz przeprowadza obliczenia na tych danych, generując odpowiednie statystyki.

## Jak uruchomić aplikację

Aby uruchomić aplikację, wykonaj poniższe kroki:

1. **Uruchomienie aplikacji w środowisku deweloperskim**

   W środowisku deweloperskim, możesz uruchomić aplikację za pomocą narzędzi dostępnych w Twoim IDE.

    - **Korzystając z IntelliJ IDEA:**
        1. Otwórz projekt w IntelliJ IDEA.
        2. Znajdź klasę `ProjectApplication`.
        3. Kliknij prawym przyciskiem myszy na `ProjectApplication`.
        4. Wybierz opcję "Run 'ProjectApplication'".

    - **Korzystając z Eclipse:**
        1. Otwórz projekt w Eclipse.
        2. Znajdź klasę `ProjectApplication`.
        3. Kliknij prawym przyciskiem myszy na `ProjectApplication`.
        4. Wybierz opcję "Run As" > "Java Application".

2. **Uruchomienie aplikacji z linii poleceń**

   Aby uruchomić aplikację z linii poleceń, użyj narzędzia `mvn` (Maven). Upewnij się, że masz Maven zainstalowany na swoim systemie.

    1. Otwórz terminal.
    2. Przejdź do głównego katalogu projektu, gdzie znajduje się plik `pom.xml`.
    3. Wykonaj polecenie:

   ```
    mvn package
    ```

    ```
    mvn spring-boot:run
    ```

   Aplikacja zostanie skompilowana i uruchomiona.

## Dostęp do aplikacji

Po uruchomieniu, aplikacja będzie dostępna pod adresem [http://localhost:8080](http://localhost:8080).
Aby dostać inforamcję o użytkowniku trzeba do tego dodać /users/{login}.
Na stronie wyświetlą nam się informacje o użytkowniku o podanym loginie.

## Struktura projektu
Projekt składa się z kilku klas Java oraz pliku konfiguracyjnego `application.properties`. Główne komponenty projektu to:
- Kontroler (`UserController`): Odpowiedzialny za obsługę żądań HTTP, przetwarzanie danych i generowanie odpowiedzi.
- Klasy modelu (`GitHubUserData`, `User`, `UserInfo`): Reprezentujące dane i logikę aplikacji.
- Serwisy (`GitHubApiService`, `UserService`): Zawierające logikę biznesową aplikacji.
- Konfiguracja (`AppConfig`): Konfiguracja aplikacji.
- Główna klasa aplikacji (`ProjectApplication`): Główna klasa Spring Boot.

## Klasy i ich funkcje

### UserController
- **getUser(String login)**: Obsługuje żądania HTTP typu GET na końcówkę `/users/{login}`. Pobiera dane użytkownika z GitHub i oblicza na ich podstawie punktację. Zwraca odpowiedź JSON z obliczoną punktacją.

### GitHubUserData
- Klasa reprezentująca dane użytkownika pobrane z API GitHub.

### User
- Klasa reprezentująca dane użytkownika w lokalnej bazie danych.

### UserInfo
- Klasa reprezentująca przetworzone dane użytkownika wraz z obliczeniami.

### CustomException
- Wyjątek reprezentujący nietypową sytuację w projekcie.

### GitHubApiService
- Serwis obsługujący komunikację z API GitHub. Pobiera dane o użytkowniku na podstawie loginu.

### UserService
- Serwis zarządzający użytkownikami w lokalnej bazie danych. Inkrementuje liczbę zapytań użytkownika i dostarcza statystyki.

### AppConfig
- Konfiguracja aplikacji Spring.

### ProjectApplication
- Główna klasa aplikacji Spring Boot.

## Konfiguracja

Plik `application.properties` zawiera konfigurację aplikacji, w tym ustawienia bazy danych i endpoint GitHub API.

### Spring Data Source
- **spring.datasource.url**: Adres URL bazy danych H2.
- **spring.datasource.username**: Nazwa użytkownika bazy danych.
- **spring.datasource.password**: Hasło bazy danych.
- **spring.datasource.driver-class-name**: Klasa sterownika bazy danych.

### Spring JPA
- **spring.jpa.database-platform**: Dialekt bazy danych używany przez Hibernate.

### H2 Console
- **spring.h2.console.enabled**: Czy H2 Console jest włączony.

### GitHub API URL
- **github.api.url**: Adres API GitHub.

## Autorzy
- Bartosz Kotwica


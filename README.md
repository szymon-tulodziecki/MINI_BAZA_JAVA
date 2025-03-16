# Mini Baza Java 📊
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green.svg)

MiniBaza to prosty program do zarządzania bazą danych procesorów. Program umożliwia tworzenie, otwieranie, przeglądanie, sortowanie, modyfikowanie i usuwanie bazy danych procesorów.

## Struktura projektu 🗂️
Projekt składa się z trzech plików:
- **Main.java**: Główny plik programu zawierający logikę zarządzania bazą danych.
- **Plik.java**: Klasa reprezentująca plik bazy danych.
- **Procesor.java**: Klasa reprezentująca procesor.

## Funkcjonalności ⚙️

### Menu główne 📋
Po uruchomieniu programu użytkownik zobaczy menu główne z następującymi opcjami:
- Utwórz nową bazę
- Otwórz bazę
- Przegląd bazy
- Sortowanie bazy
- Usuń bazę
- Zakończ program

### Tworzenie nowej bazy 🆕
Użytkownik może utworzyć nową bazę danych, podając nazwę pliku. Program sprawdzi, czy nazwa jest poprawna i czy plik o tej nazwie już istnieje. Jeśli wszystko jest w porządku, baza zostanie utworzona, a przykładowe procesory zostaną dodane do bazy.

### Otwieranie bazy 📂
Użytkownik może otworzyć istniejącą bazę danych, podając nazwę pliku. Program sprawdzi, czy plik istnieje i czy nazwa jest poprawna. Jeśli wszystko jest w porządku, baza zostanie otwarta, a procesory zostaną wczytane do pamięci.

### Przegląd bazy 🔍
Użytkownik może przeglądać rekordy w bazie danych. Program wyświetli szczegóły każdego procesora, a użytkownik będzie mógł przechodzić między rekordami, dodawać nowe, usuwać istniejące oraz modyfikować dane procesorów.

### Sortowanie bazy 🔄
Użytkownik może sortować bazę danych według ID, nazwy producenta lub ceny. Program umożliwia sortowanie rosnące i malejące.

### Usuwanie bazy 🗑️
Użytkownik może usunąć istniejącą bazę danych, podając nazwę pliku. Program sprawdzi, czy plik istnieje i czy nazwa jest poprawna. Jeśli wszystko jest w porządku, plik zostanie usunięty.

## Uruchomienie programu 🚀
Aby uruchomić program, skompiluj wszystkie pliki źródłowe i uruchom klasę Main:

```sh
javac Main.java Plik.java Procesor.java
java Main
```

## Wymagania 📋
- Java 8 lub nowsza

## Autor ✍️
Szymon Tułodziecki

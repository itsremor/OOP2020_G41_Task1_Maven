package ru.vsu.cs.services;

import ru.vsu.cs.models.GameMap;
import ru.vsu.cs.models.GraphHelper;
import ru.vsu.cs.models.Station;
import ru.vsu.cs.models.WayType;
import ru.vsu.cs.models.characters.Character;
import ru.vsu.cs.models.characters.CharacterType;
import ru.vsu.cs.models.characters.Detective;
import ru.vsu.cs.models.characters.MrX;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameService {
    private static WayType[] getPossibleWays(Station station, int newStation) {
        int counter = 0;

        if (isContainStation(station.getWays().get(WayType.BUS), newStation)) counter++;
        if (isContainStation(station.getWays().get(WayType.METRO), newStation)) counter++;
        if (isContainStation(station.getWays().get(WayType.TAXI), newStation)) counter++;

        WayType[] types = new WayType[counter];
        counter = 0;

        if (isContainStation(station.getWays().get(WayType.BUS), newStation)) {
            types[counter] = WayType.BUS;
            counter++;
        }
        if (isContainStation(station.getWays().get(WayType.METRO), newStation)) {
            types[counter] = WayType.METRO;
            counter++;
        }
        if (isContainStation(station.getWays().get(WayType.TAXI), newStation)) {
            types[counter] = WayType.TAXI;
            counter++;
        }

        return types;
    }

    private static boolean isContainStation(Station[] stations, int id) {
        for (int i = 0; i < stations.length; i++) {
            if (stations[i].getId() == id) return true;
        }
        return false;
    }

    private static void spendTicket(Character character, WayType wayType) {
        switch (wayType) {
            case BUS:
                character.setBusTickets(character.getBusTickets() - 1);
            case TAXI:
                character.setBusTickets(character.getTaxiTickets() - 1);
            case METRO:
                character.setBusTickets(character.getMetroTickets() - 1);
            case BLACK:
                if(character.getCharacterType() == CharacterType.MRX)
                ((MrX) character).setBlackTicket(((MrX) character).getBlackTicket() - 1);
        }
    }

    private static void initGameMap(GameMap gameMap) {
        Station[] mapStations = new Station[33];
        Station station;

        for (int i = 0; i < 33; i++) {
            station = new Station();
            station.setId(i);
            mapStations[i] = station;
        }
//(Зелёный=Такси, Оранжевый=метро, Голубой=Автобус)

        /*На данный момент здесь всё инициализируется вручную, но к конечной версии я планирую довести это до автоматизма
        Добавив парсер SVG-шки карты и всё происходило бы автоматически. Но ввиду ограниченного времени к этой
        аттестации реализация сделана вот так паршиво. Очень неприятно что не успел сделать по-хорошему
        но всё равно это лучше, чем ничего

        P.S. Если речь зайдёт о том, что это можно было бы на данном этапе как-то запихнуть в отдельный метод, то
        вряд ли, ввиду необходимости ручного ввода данных о вершинах. Но всё же, постарался
        свести к автоматизму это
        */

        GraphHelper bus = new GraphHelper();
        GraphHelper taxi = new GraphHelper();
        GraphHelper metro = new GraphHelper();

        taxi.addEdge(0, 1);
        taxi.addEdge(0, 2);
        taxi.addEdge(4, 2);
        taxi.addEdge(4, 7);
        taxi.addEdge(3, 1);
        taxi.addEdge(3, 7);
        taxi.addEdge(12, 7);
        taxi.addEdge(18, 7);
        taxi.addEdge(6, 7);
        taxi.addEdge(6, 5);
        taxi.addEdge(6, 8);
        taxi.addEdge(11, 8);
        taxi.addEdge(12, 8);
        taxi.addEdge(12, 18);
        taxi.addEdge(9, 18);
        taxi.addEdge(9, 16);
        taxi.addEdge(9, 15);
        taxi.addEdge(11, 15);
        taxi.addEdge(11, 13);
        taxi.addEdge(15, 13);
        taxi.addEdge(15, 17);
        taxi.addEdge(15, 14);
        taxi.addEdge(31, 14);
        taxi.addEdge(31, 30);
        taxi.addEdge(10, 30);
        taxi.addEdge(10, 15);
        taxi.addEdge(10, 17);
        taxi.addEdge(10, 29);
        taxi.addEdge(19, 13);
        taxi.addEdge(19, 20);
        taxi.addEdge(21, 20);
        taxi.addEdge(21, 32);
        taxi.addEdge(23, 32);
        taxi.addEdge(22, 24);
        taxi.addEdge(25, 26);
        taxi.addEdge(28, 26);

        metro.addEdge(0, 3);
        metro.addEdge(5, 3);
        metro.addEdge(5, 1);
        metro.addEdge(15, 17);
        metro.addEdge(29, 17);
        metro.addEdge(29, 32);
        metro.addEdge(21, 32);
        metro.addEdge(21, 22);
        metro.addEdge(23, 22);
        metro.addEdge(24, 25);
        metro.addEdge(27, 25);
        metro.addEdge(28, 25);

        bus.addEdge(0, 2);
        bus.addEdge(0, 3);
        bus.addEdge(7, 3);
        bus.addEdge(1, 5);
        bus.addEdge(6, 5);
        bus.addEdge(18, 12);
        bus.addEdge(15, 12);
        bus.addEdge(15, 14);
        bus.addEdge(15, 17);
        bus.addEdge(20, 17);
        bus.addEdge(21, 32);
        bus.addEdge(22, 24);
        bus.addEdge(23, 26);
        bus.addEdge(25, 26);
        bus.addEdge(27, 28);

        for (int i = 0; i < 33; i++) {
            addWaysForStation(mapStations, bus.getArrayOfEdges(i),
                    taxi.getArrayOfEdges(i),
                    metro.getArrayOfEdges(i),
                    i);
        }
        //также может задаваться автоматически с помощью парсера. Спросите меня и я расскажу свою идею

        int[] startPoints = {0, 28, 30, 18, 8, 15};

        gameMap.setStartPoints(startPoints);
        gameMap.setStations(mapStations);
    }

    private static Character[] spawnCharacters(int countOfDetectives, GameMap gameMap) {
        Character[] characters = new Character[countOfDetectives + 1];
        Random rnd = new Random();
        int randomValue;

        ArrayList<Integer> spawnZones = new ArrayList<>();
        for (int i = 0; i < gameMap.getStartPoints().length; i++) {
            spawnZones.add(gameMap.getStartPoints()[i]);
        }

        characters[0] = new MrX(countOfDetectives);
        randomValue = rnd.nextInt(spawnZones.size());
        characters[0].setCurrentPosition(gameMap.getStations()[spawnZones.get(randomValue)]);
        gameMap.getStations()[spawnZones.get(randomValue)].setCharacterType(CharacterType.MRX);
        spawnZones.remove(randomValue);

        for (int i = 1; i <= countOfDetectives; i++) {
            characters[i] = new Detective();
            randomValue = rnd.nextInt(spawnZones.size());
            characters[i].setCurrentPosition(gameMap.getStations()[spawnZones.get(randomValue)]);
            gameMap.getStations()[spawnZones.get(randomValue)].setCharacterType(CharacterType.DETECTIVE);
            spawnZones.remove(randomValue);
        }

        return characters;
    }

    private static ArrayList<Integer> getPossibleMovesForCharacter(GameMap gameMap, Character character) {
        ArrayList<Integer> possibleMoves = new ArrayList<>();

        Station[] stations;
        if (character.getCharacterType() == CharacterType.DETECTIVE) {

            if (character.getBusTickets() > 0) {
                stations = character.getCurrentPosition().getWays().get(WayType.BUS);

                for (int i = 0; i < stations.length; i++) {
                    if (stations[i].getCharacter() != CharacterType.DETECTIVE)
                        possibleMoves.add(stations[i].getId());
                }
            }

            if (character.getTaxiTickets() > 0) {
                stations = character.getCurrentPosition().getWays().get(WayType.TAXI);

                for (int i = 0; i < stations.length; i++) {
                    if (!possibleMoves.contains(stations[i].getId()) &&
                            stations[i].getCharacter() != CharacterType.DETECTIVE)
                        possibleMoves.add(stations[i].getId());
                }
            }

            if (character.getMetroTickets() > 0) {
                stations = character.getCurrentPosition().getWays().get(WayType.METRO);

                for (int i = 0; i < stations.length; i++) {
                    if (!possibleMoves.contains(stations[i].getId()) &&
                            stations[i].getCharacter() != CharacterType.DETECTIVE)
                        possibleMoves.add(stations[i].getId());
                }
            }
        } else {
            MrX mrX = (MrX) character;

            if (character.getBusTickets() > 0 || mrX.getBlackTicket() > 0) {
                stations = character.getCurrentPosition().getWays().get(WayType.BUS);

                for (int i = 0; i < stations.length; i++) {
                    if (stations[i].getCharacter() != CharacterType.DETECTIVE)
                        possibleMoves.add(stations[i].getId());
                }
            }

            if (character.getTaxiTickets() > 0 || mrX.getBlackTicket() > 0) {
                stations = character.getCurrentPosition().getWays().get(WayType.TAXI);

                for (int i = 0; i < stations.length; i++) {
                    if (!possibleMoves.contains(stations[i].getId()) &&
                            stations[i].getCharacter() != CharacterType.DETECTIVE)
                        possibleMoves.add(stations[i].getId());
                }
            }

            if (character.getMetroTickets() > 0 || mrX.getBlackTicket() > 0) {
                stations = character.getCurrentPosition().getWays().get(WayType.METRO);

                for (int i = 0; i < stations.length; i++) {
                    if (!possibleMoves.contains(stations[i].getId()) &&
                            stations[i].getCharacter() != CharacterType.DETECTIVE)
                        possibleMoves.add(stations[i].getId());
                }
            }
        }

        return possibleMoves;
    }

    private static void moveCharacter(GameMap gameMap, Character character, int station) {
        gameMap.getStations()[character.getCurrentPosition().getId()].setCharacterType(CharacterType.NOBODY);
        character.setCurrentPosition(gameMap.getStations()[station]);
        gameMap.getStations()[character.getCurrentPosition().getId()].setCharacterType(character.getCharacterType());
    }

    private static void addWaysForStation(Station[] allStations, int[] busIndexes,
                                          int[] taxiIndexes, int[] metroIndexes,
                                          int currentStationIndex) {
        Station[] busStations = new Station[busIndexes.length];
        Station[] taxiStations = new Station[taxiIndexes.length];
        Station[] metroStations = new Station[metroIndexes.length];

        for (int i = 0; i < busStations.length; i++) {
            busStations[i] = allStations[busIndexes[i]];
        }
        allStations[currentStationIndex].getWays().put(WayType.BUS, busStations);

        for (int i = 0; i < taxiStations.length; i++) {
            taxiStations[i] = allStations[taxiIndexes[i]];
        }
        allStations[currentStationIndex].getWays().put(WayType.TAXI, taxiStations);

        for (int i = 0; i < metroStations.length; i++) {
            metroStations[i] = allStations[metroIndexes[i]];
        }
        allStations[currentStationIndex].getWays().put(WayType.METRO, metroStations);
    }

    public static void startGameInConsole(int countOfDetectives) {
        Scanner scn = new Scanner(System.in);
        GameMap gameMap = new GameMap();
        boolean isMrXWinner = false;

        initGameMap(gameMap);
        Character[] characters = spawnCharacters(countOfDetectives, gameMap);

        System.out.print("---В игре находится " + countOfDetectives + " детектива('ов)---\n");

        System.out.print("Текущее положение детективов: ");
        for (int i = 1; i <= countOfDetectives; i++) {
            System.out.print("\n" + i + "'й находится на станции " + (characters[i].getCurrentPosition().getId() + 1));
        }

        int turn = 0;
        int choice;
        int moveChoice;
        int temp;
        ArrayList<Integer> possibleWays;
        WayType[] wayTypes;

        String flag = "";
        String currentStreak = "";

        for (int i = 0; i < countOfDetectives; i++) {
            flag += '-';
        }


        while (true) {
            if(turn % characters.length == 0) currentStreak = "";
            System.out.print("\n\nСейчас ходит " + characters[turn % characters.length].getCharacterType());
            possibleWays = getPossibleMovesForCharacter(gameMap, characters[turn % characters.length]);

            System.out.print("\nВ распоряжении имеется:\n•"
                    + characters[turn % characters.length].getTaxiTickets() + " билетов на такси\n•"
                    + characters[turn % characters.length].getBusTickets() + " билетов на автобус\n•"
                    + characters[turn % characters.length].getMetroTickets() + " билетов на метро");
            if (characters[turn % characters.length].getCharacterType() == CharacterType.MRX) {
                MrX mrx = (MrX) characters[turn % characters.length];
                System.out.print("\n•" + mrx.getBlackTicket() + " чёрных билета\n•" +
                        mrx.getDoubleMove() + " двойных ходов");
            }

            System.out.print("\nЭтот персонаж находится на станции " + (characters[turn % characters.length].getCurrentPosition().getId() + 1));
            if (possibleWays.size() == 0){
                System.out.print("\n\nДля этого персонажа нет доступных ходов");
                if(characters[turn % characters.length].getCharacterType() == CharacterType.DETECTIVE)
                    currentStreak += '-';
            }

            else {
                System.out.print("\nВы можете отправиться на следующие станции: | ");
                for (int i = 0; i < possibleWays.size(); i++) {
                    System.out.print((possibleWays.get(i) + 1) + " | ");
                }
                while (true) {
                    System.out.print("\nВведите номер станции, на которую вы хотите отправиться: ");
                    moveChoice = scn.nextInt() - 1;
                    if (possibleWays.contains(moveChoice)) break;
                    else
                        System.out.print("\n! ! !Вы не можете отправиться на эту станцию. Попробуйте ввод снова! ! !\n");
                }

                wayTypes = getPossibleWays(characters[turn % characters.length].getCurrentPosition(), moveChoice);
                for (int i = 0; i < wayTypes.length; i++) {
                    System.out.print("\nВам доступна поездка на " + (i + 1) + ". " + wayTypes[i]);
                }

                while (true) {
                    System.out.print("\nВыберите тип транспорта, введя его номер: ");
                    choice = scn.nextInt() - 1;
                    if (choice >= 0 && choice <= wayTypes.length) break;

                    System.out.print("\n! ! !Неверный ввод! ! !");
                }

                if (characters[turn % characters.length].getCharacterType() == CharacterType.MRX) {
                    MrX mrx = (MrX) characters[turn % characters.length];
                    System.out.print("\n1. - BLACK");
                    if (mrx.getTicketsByType(wayTypes[choice]) > 0)
                        System.out.print("\n2. - " + wayTypes[choice]);
                    while (true) {
                        System.out.print("\nВыберите тип билета, который хотите потратить: ");
                        temp = scn.nextInt();
                        if (temp == 2 && mrx.getTicketsByType(wayTypes[choice]) > 0) {
                            spendTicket(characters[turn % characters.length], wayTypes[choice]);
                            break;
                        } else if (temp == 1) {
                            mrx.setBlackTicket(mrx.getBlackTicket() - 1);
                            break;
                        }
                        System.out.print("\nНеверный ввод");
                    }
                    if(mrx.getDoubleMove() > 0){
                        System.out.print("\nВы можете сделать двойной ход. Введите 0, если хотите его сделать,\nлюбое другое число - если нет: ");
                        temp = scn.nextInt();
                        if(temp == 0){
                            mrx.setDoubleMove(mrx.getDoubleMove() - 1);
                            continue;
                        }
                    }
                }
                else spendTicket(characters[turn % characters.length], wayTypes[choice]);



                if (characters[turn % characters.length].getCharacterType() == CharacterType.DETECTIVE &&
                        gameMap.getStations()[moveChoice].getCharacter() == CharacterType.MRX) break;

                moveCharacter(gameMap, characters[turn % characters.length], moveChoice);

            }

            turn++;
            if (turn / characters.length >= 22 || currentStreak.equals(flag)) {
                isMrXWinner = true;
                break;
            }

            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }

        if (isMrXWinner) System.out.println("\u001B[34m" + "\nМистер Икс одержал победу" + "\u001B[0m");
        else System.out.println("\u001B[31m" + "\nДетективы одержали победу" + "\u001B[0m");
    }
}
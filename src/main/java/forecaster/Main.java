package forecaster;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

//Интерфейс - команды считанные из консоли:
//Курс валюты на завтра
//		"rate TRY tomorrow" Вт 22.02.2022 - 6,11;
//Курс валюты на 7 дней
//		"rate USD week"
//			Вт 22.02.2022 - 75,45
//			Ср 23.02.2022 - 76,12
//			Чт 24.02.2022 - 77,34
//			Пт 25.02.2022 - 78,23
//			Сб 26.02.2022 - 80,11
//			Вс 27.02.2022 - 82,10
//			Пн 28.02.2022 - 90,45
//
//	Алгоритм прогнозирования: Линейная регрессия

public class Main {
    public static void main(String[] args) {
        String[] command = new Scanner(System.in).nextLine().split(" ");
        List<Integer> rateList = RateRepo.getRates(command[1]);
        List<Integer> forecasts = Forecaster.getForecast(rateList, command[2]);
        printForecast(forecasts);
    }

    private static void printForecast(List<Integer> forecasts ) {
        for (int i = 0; i < forecasts.size(); i++) {
            LocalDate localDate = LocalDate.now().plus(i + 1, ChronoUnit.DAYS);
            System.out.println(localDate + " " + forecasts.get(i));
        }
    }

}

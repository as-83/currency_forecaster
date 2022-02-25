package forecaster.domain;

/**
 * Класс Command представляет команду
 * пользователя
 */
public class Command {
    private int forecastPeriod;
    private Currency currencyCode;
    private boolean correct;


    public int getForecastPeriod() {
        return forecastPeriod;
    }

    public void setForecastPeriod(int forecastPeriod) {
        this.forecastPeriod = forecastPeriod;
    }

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

}

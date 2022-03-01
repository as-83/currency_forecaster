package forecaster.domain;

/**
 * Класс Command представляет код валюты,
 * период прогноза  и корректость команды
 */
public class Command {

    private boolean correct;
    private int forecastPeriod;
    private CurrencyCode currencyCode;

    public int getForecastPeriod() {
        return forecastPeriod;
    }

    public void setForecastPeriod(int forecastPeriod) {
        this.forecastPeriod = forecastPeriod;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

}

package forecaster.sources;

import forecaster.domain.Rate;

import java.util.List;

/**
 *
 */
public interface DataSource {
    List<Rate> getRates(String currencyCode);
}

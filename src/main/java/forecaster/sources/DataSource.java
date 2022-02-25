package forecaster.sources;

import forecaster.domain.Rate;

import java.io.IOException;
import java.util.List;

public interface DataSource {
    public List<Rate> getRates(String currencyCode);
}

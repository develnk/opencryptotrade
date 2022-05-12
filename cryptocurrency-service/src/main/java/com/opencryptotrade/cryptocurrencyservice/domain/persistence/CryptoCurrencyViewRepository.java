package com.opencryptotrade.cryptocurrencyservice.domain.persistence;

import com.opencryptotrade.cryptocurrencyservice.domain.model.entity.CryptoCurrencyView;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface CryptoCurrencyViewRepository extends CrudRepository<CryptoCurrencyView, Integer> {

    Optional<CryptoCurrencyView> findOneCryptoCurrencyViewBySymbolAndType(String symbol, String type);

}

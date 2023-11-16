package com.example.Contact.Repository;

import com.example.Contact.Dto.Symbol;
import com.example.Contact.ExceptionHandler.DuplicateEntryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SymbolRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean existsBySymbol(String symbol) {
        String sql = "SELECT COUNT(*) FROM symbol WHERE symbol = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, symbol);
        return count != null && count > 0;
    }
    public Symbol findSymbolBySymbolName(String symbolName) {
        String sql = "SELECT * FROM Symbol WHERE SYMBOL = ?";
        return jdbcTemplate.query(sql, new Object[]{symbolName}, resultSetExtractor);
    }

    private final ResultSetExtractor<Symbol> resultSetExtractor = resultSet -> {
        if (resultSet.next()) {
            Symbol symbol = new Symbol();
            symbol.setSymbol(resultSet.getString("SYMBOL"));
            symbol.setSymbolName(resultSet.getString("SYMBOL_NAME"));
            symbol.setIndexName(resultSet.getString("INDEX_NAME"));
             symbol.setCompanyName(resultSet.getString("INDEX_NAME"));
             symbol.setIndustry(resultSet.getString("INDUSTRY"));
             symbol.setSeries(resultSet.getString("SERIES"));
             symbol.setIsinCode(resultSet.getString("ISIN_CODE"));
             symbol.setExchange(resultSet.getString("EXCHANGE"));
             symbol.setCreatedAt(resultSet.getString("CREATED_AT"));
             symbol.setUpdatedAt(resultSet.getString("UPDATED_AT"));
             symbol.setScripCode(resultSet.getString("SCRIP_CODE"));
            return symbol;
        } else {
             throw new DuplicateEntryException("Symbol not found","/quote/get-symbol");
        }
    };
    public List<Symbol> findAllSymbols() {
        String sql = "SELECT * FROM Symbol";
        return jdbcTemplate.query(sql, symbolRowMapper);
    }

    private final RowMapper<Symbol> symbolRowMapper = (resultSet, rowNum) -> {
        Symbol symbol = new Symbol();
        symbol.setSymbol(resultSet.getString("SYMBOL"));
        symbol.setSymbolName(resultSet.getString("SYMBOL_NAME"));
        symbol.setIndexName(resultSet.getString("INDEX_NAME"));
        symbol.setCompanyName(resultSet.getString("INDEX_NAME"));
        symbol.setIndustry(resultSet.getString("INDUSTRY"));
        symbol.setSeries(resultSet.getString("SERIES"));
        symbol.setIsinCode(resultSet.getString("ISIN_CODE"));
        symbol.setExchange(resultSet.getString("EXCHANGE"));
        symbol.setCreatedAt(resultSet.getString("CREATED_AT"));
        symbol.setUpdatedAt(resultSet.getString("UPDATED_AT"));
        symbol.setScripCode(resultSet.getString("SCRIP_CODE"));
        return symbol;
    };
}

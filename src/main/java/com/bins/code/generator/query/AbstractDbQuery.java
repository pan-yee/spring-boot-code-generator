package com.bins.code.generator.query;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDbQuery implements IDbQuery {

    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return false;
    }

    @Override
    public String[] fieldCustom() {
        return null;
    }
}

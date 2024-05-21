package nl.indi.returns.service.exceptions;

import java.sql.SQLException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Covered by API tests of ReturnsServiceOverview
@Provider
public class SQLExceptionMapper implements ExceptionMapper<SQLException> {
    private static final Logger LOG = LoggerFactory.getLogger(SQLExceptionMapper.class);

    @Override
    public Response toResponse(SQLException exception) {
        LOG.error("Something went wrong with the service", exception);
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR) //
                .build();
    }
}

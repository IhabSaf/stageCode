package nl.indi.returns;

import java.sql.SQLException;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import nl.indi.order_management.database.OmsDao;
import nl.indi.order_management.database.beans.RMA;

@ApplicationScoped
public class INDIReturnsOverview {
    private static final Logger LOG = LoggerFactory.getLogger(INDIReturnsOverview.class);
    private final AgroalDataSource iom;
    OmsDao dao;

    @Inject
    public INDIReturnsOverview(@DataSource("oms") AgroalDataSource iom) {
        this.iom = iom;
    }

    @PostConstruct
    public void setUp() {
        dao = new OmsDao(iom);
    }

    public List<RMA> getAPIReturnRequestsForCustomer(String customerNumber) throws SQLException {
        LOG.info("Returning request overview for customerNumber: {}", customerNumber);
        return dao.getAllReturnRequestsByCustomerNumber(customerNumber);
    }
}

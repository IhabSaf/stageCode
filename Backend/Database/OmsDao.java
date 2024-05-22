public class OmsDao {
private static final String GET_EMAIL_ADDRESS_BY_RMA_NUMBER_FROM_RMA_PROPERTIES = TextUtil
            .getString(OmsDao.class.getResourceAsStream("get-rma-properties-email-by-rma-number.sql"));
private static final String GET_ALL_RETURN_REQUESTS_BY_CUSTOMER_NUMBER = TextUtil
            .getString(OmsDao.class.getResourceAsStream("get-all-return-request-by-customer-number.sql"));

    public String getEmailFromRmaPropreties(String rmaNumber) throws SQLException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(GET_EMAIL_ADDRESS_BY_RMA_NUMBER_FROM_RMA_PROPERTIES)) {
            statement.setString(1, rmaNumber);

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    return null;
                }
                return result.getString("value");
            }
        }
    }

    public List<RMA> getAllReturnRequestsByCustomerNumber(String customerNumber) throws SQLException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_ALL_RETURN_REQUESTS_BY_CUSTOMER_NUMBER)) {
            statement.setString(1, customerNumber);
            try (ResultSet result = statement.executeQuery()) {
                Map<String, RMA> listOfRMAs = new HashMap<>();
                while (result.next()) {
                    String rmaNumber = result.getString("rmaNumber");
                    RMA rma = listOfRMAs.get(rmaNumber);

                    if (rma == null) {
                        rma = RMA.builder()//
                                .rmaNum(rmaNumber)//
                                .orderNumber(result.getString("shopOrderNumber"))//
                                .emailAddress(result.getString("customerMailAddress"))//
                                .comment(result.getString("comment"))//
                                .returnRequestDate(result.getString("creationDate"))//
                                .items(new ArrayList<>())//
                                .build();
                        listOfRMAs.put(rmaNumber, rma);
                    }
                    RMAItems item = RMAItems.builder()//
                            .position(result.getInt("orderPosNo"))//
                            .sku(result.getString("shopArticleNumber"))//
                            .quantity(BigDecimal.valueOf(result.getInt("quantity")))//
                            .articleName(result.getString("shopArticleName"))//
                            .imageURL(result.getString("imageURL"))//
                            .build();//
                    rma.getItems().add(item);
                }
                return listOfRMAs.values().stream() //
                        .sorted(Comparator.comparing(RMA::getRmaNum).reversed())//
                        .toList();
            }
        }
    }
}

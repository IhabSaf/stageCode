@DataSet("classpath:db/base_data_structure_pgsql")
class OmsDaoTest {
    @RegisterExtension
    static final PostgreSQLTestServer server = new PostgreSQLTestServer("14.5-alpine");

      @Test
    @DataSet("classpath:db/oms_dao")
    void testGetEmailFromRmaPropreties() throws Exception {
        try (HikariDataSource ds = server.createDataSource()) {
            OmsDao dao = new OmsDao(ds);

            String rmaNumber = "RMA-109843";
            String acutalResult = dao.getEmailFromRmaPropreties(rmaNumber);
            String expectedEmailAddress = "Test@indi.nl";
            assertEquals(expectedEmailAddress, acutalResult);
        }
    }


    @Test
    @DataSet("classpath:db/oms_dao")
    void testGetEmailFromRmaPropreties_notFound() throws Exception {
        try (HikariDataSource ds = server.createDataSource()) {
            OmsDao dao = new OmsDao(ds);

            String rmaNumber = "109843333";
            String acutalResult = dao.getEmailFromRmaPropreties(rmaNumber);
            assertNull(acutalResult);
        }
    }

  
     @Test
    @DataSet("classpath:db/oms_dao")
    void testGetAllReturnRequestsByCustomerNumber() throws Exception {
        try (HikariDataSource ds = server.createDataSource()) {
            OmsDao dao = new OmsDao(ds);
            List<RMA> actualResult = dao.getAllReturnRequestsByCustomerNumber("3311208");
            System.out.print("this is the data should be printed: " + actualResult);
            List<RMA> exceptedResult = Arrays.asList(RMA.builder().rmaNum("RMA-1709843").status("initialized")
                    .orderNumber("01179015").emailAddress("info@demto.com").comment(null).returnRequestDate(
                            "2023-01-02 11:18:27.754")
                    .items(Arrays.asList(
                            RMAItems.builder().position(9).sku("20487").quantity(new BigDecimal(7))
                                    .articleName("Stiftfrees 52SKM-N 12x25-S6x70")
                                    .imageURL("https://assets.indi.nl/images/2XS/z901300_01.jpg").build(),
                            RMAItems.builder().position(11).sku("20487").quantity(new BigDecimal(8))
                                    .articleName("Stiftfrees 52SKM-N 12x25-S6x70")
                                    .imageURL("https://assets.indi.nl/images/2XS/v6e.jpg").build(),
                            RMAItems.builder().position(9).sku("20487").quantity(new BigDecimal(9))
                                    .articleName("Stiftfrees 52SKM-N 12x25-S6x70")
                                    .imageURL("https://assets.indi.nl/images/2XS/v6e.jpg").build()))
                    .build(),
                    RMA.builder().rmaNum("RMA-1609843").status("initialized").orderNumber("01179015")
                            .emailAddress("info@demto.com").comment(null).returnRequestDate("2023-01-02 11:18:27.754")
                            .items(Arrays.asList(RMAItems.builder().position(9).sku("20487").quantity(new BigDecimal(6))
                                    .articleName("Stiftfrees 52SKM-N 12x25-S6x70")
                                    .imageURL("https://assets.indi.nl/images/2XS/z901300_01.jpg").build()))
                            .build(),
                    RMA.builder().rmaNum("RMA-1409843").status("initialized").orderNumber("01179015")
                            .emailAddress("info@arpengineering.nl").comment(null)
                            .returnRequestDate("2023-01-02 11:18:27.754")
                            .items(Arrays.asList(
                                    RMAItems.builder().position(6).sku("GWE150259").quantity(new BigDecimal(4))
                                            .articleName("Ladegeleider 1502/1504 voor lade 67mm 2stuks")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/HF35142.jpg").build(),
                                    RMAItems.builder().position(6).sku("GWE150259").quantity(new BigDecimal(4))
                                            .articleName("Ladegeleider 1502/1504 voor lade 67mm 2stuks")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/HF35142.jpg").build()))
                            .build(),
                    RMA.builder().rmaNum("RMA-1309843").status("initialized").orderNumber("01179015")
                            .emailAddress("info@demto.com").comment(null).returnRequestDate("2023-01-02 11:18:27.754")
                            .items(Arrays
                                    .asList(RMAItems.builder().position(11).sku("PP35006").quantity(new BigDecimal(3))
                                            .articleName("Schuurlinnen Handy Roll R222 25mm L=25m K80")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/CA6590001_2.jpg").build()))
                            .build(),
                    RMA.builder().rmaNum("RMA-1109843").status("initialized").orderNumber("01179015")
                            .emailAddress("info@arpengineering.nl").comment(null)
                            .returnRequestDate(
                                    "2023-01-02 11:18:27.754")
                            .items(Arrays.asList(
                                    RMAItems.builder().position(7).sku("1500ED70K").quantity(new BigDecimal(1))
                                            .articleName("Gereedschap opvulmodule leeg")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/TS2025A_2.jpg").build(),
                                    RMAItems.builder().position(7).sku("1500ED70K").quantity(new BigDecimal(1))
                                            .articleName("Gereedschap opvulmodule leeg")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/TS2025A_2.jpg").build(),
                                    RMAItems.builder().position(7).sku("1500ED70K").quantity(new BigDecimal(1))
                                            .articleName("Gereedschap opvulmodule leeg")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/TS2025A_2.jpg").build(),
                                    RMAItems.builder().position(7).sku("1500ED70K").quantity(new BigDecimal(1))
                                            .articleName("Gereedschap opvulmodule leeg")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/TS2025A_2.jpg").build(),
                                    RMAItems.builder().position(12).sku("PP35007").quantity(new BigDecimal(1))
                                            .articleName("Schuurlinnen Handy Roll R222 25mm L=25m K150")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/990B1PS01.jpg").build(),
                                    RMAItems.builder().position(12).sku("PP35007").quantity(new BigDecimal(1))
                                            .articleName("Schuurlinnen Handy Roll R222 25mm L=25m K150")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/990B1PS01.jpg").build(),
                                    RMAItems.builder().position(12).sku("PP35007").quantity(new BigDecimal(1))
                                            .articleName("Schuurlinnen Handy Roll R222 25mm L=25m K150")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/990B1PS01.jpg").build(),
                                    RMAItems.builder().position(12).sku("PP35007").quantity(new BigDecimal(1))
                                            .articleName("Schuurlinnen Handy Roll R222 25mm L=25m K150")
                                            .imageURL("http://prodindi.eperium.nl/images/2XS/990B1PS01.jpg").build()))
                            .build());

            assertThat(actualResult)//
                    .usingRecursiveComparison()//
                    .isEqualTo(exceptedResult);
        }
    }

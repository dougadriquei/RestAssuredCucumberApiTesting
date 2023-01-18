

Feature: UC01 - PostPixPayments
  It must be possible to get the code data from valid QR Codes, get the accounts data and send payment

  #TODO - Deveria retornar 201, mas está retornando status code 200
  Scenario: UC01.01 - The Application must validate if it is a valid payment QR CODE 01 and ACCOUNT 01
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201



  Scenario: UC01.02 - The Application must validate if it is an invalid payment
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send invalid payment for "/pix/payments" with valid QR Code
    And I should see the bad request as "invalid_request" with errors endToEnd as "must not be blank" and with status code 400

    #TODO - Accept end_to_end invalid!
  Scenario: UC01.03 - The Application must validate if it is a not found with end_to_end invalid
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with end_to_end invalid and with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with invalid end_to_end
    And I should see the bad request as "info_not_found" with errors endToEnd as "must not be blank" and with status code 404

    #TODO - Accept conciliation_id invalid!
  Scenario: UC01.04 - The Application must validate if it is a not found with conciliation_id invalid
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with conciliation_id invalid and with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with invalid end_to_end
    And I should see the bad request as "info_not_found" with errors endToEnd as "must not be blank" and with status code 404

    #TODO - Deveria retornar 201, mas está retornando status code 200
  Scenario: UC01.05 - The Application must validate if it is an invalid payment without sender bank
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95" without bank
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

    #TODO - Retornando Erro 500
  Scenario: UC01.06 -  The Application must validate if it is an invalid payment without sender
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95" without sender
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

    #TODO - Erro 500
  Scenario: UC01.07 - The Application must validate if it is an invalid payment without recipient bank
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" without recipient bank and body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

    #TODO Está retornando Erro 500
  Scenario: UC01.08 - The Application must validate if it is an invalid payment without recipient@@@
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" without recipient and body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

    #TODO - Permitiu mandar valores negativos
  Scenario: UC01.09 - The Application must validate if it is an invalid payment with negative amount
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with negative amount and body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with invalid end_to_end
    And I should see the bad request as "invalid_request" with errors recipient bank as "must not be blank" and with status code 400

   #TODO - Sem amount esta retornando erro 500
  Scenario: UC01.10 - The Application must validate if it is an invalid payment without amount@@@@
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" without amount and body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

    #TODO - Deveria retornar 201, porem está restornando 404 qrcode_not_found
  Scenario: UC01.11 - The Application must validate if it is an invalid payment with document sender invalid@@@
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95" with document invalid
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

     #TODO - Deveria retornar 400
  Scenario: UC01.12 - The Application must validate if it is an invalid payment when Sender and Recipient are the same
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" sender and recipient are the same with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the bad request as "invalid_request" with errors document sender as "recipient cannot be the same as the sender" and with status code 400

     #TODO - Deveria retornar 201, mas está retornando status code 200
  Scenario: UC01.13 - The Application must validate if it is an valid payment QR CODE 02
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540520.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

     #TODO - Deveria retornar 201, mas está retornando status code 200
  Scenario: UC01.14 - The Application must validate if it is an invalid payment QR CODE 03 insufficient balance
    Given I want perform GET operation for "/accounts/630932fc327fb162f0d8e72c"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540550.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

     #TODO - Deveria retornar 201, mas está retornando status code 200
  Scenario: UC01.15 - The Application must validate if it is a valid payment QR CODE 01 and ACCOUNT 02
    Given I want perform GET operation for "/accounts/630932fc327fb162f0d8e72c"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

     #TODO - Deveria retornar 201, mas está retornando status code 200
  Scenario: UC01.16 - The Application must validate if it is a valid payment QR CODE 01 and ACCOUNT 03
    Given I want perform GET operation for "/accounts/630933566e9f1d5128cf4c00"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the bad request as "insufficient_balance" with status code 404

     #TODO - Deveria retornar 400, e tratar tamanho dos campos
  Scenario: UC01.17 - The Application must validate if it is a valid payment QR CODE 01 and ACCOUNT 01 limit test
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations with limit test for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the bad request as "invalid_request" with errors document sender as "limit of fields" and with status code 400

     #TODO - Deveria retornar 400, e tratar o tipo dos campos
  Scenario: UC01.18 - The Application must validate if it is a valid payment QR CODE 01 and ACCOUNT 01 equivalence test
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations equivalence test for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the bad request as "invalid_request" with errors document sender as "invalid types" and with status code 400
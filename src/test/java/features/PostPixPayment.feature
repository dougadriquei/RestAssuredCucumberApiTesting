

Feature: PostPixPayments
  It must be possible to get the code data from valid QR Codes, get the accounts data and send payment

  #TODO - Deveria retornar 201, mas está retornando status code 200
  Scenario: The Application must validate if it is a valid payment QR CODE 01 and ACCOUNT 01
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

  Scenario: The Application must validate if it is a invalid payment
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send invalid payment for "/pix/payments" with valid QR Code
    And I should see the bad request as "invalid_request" with errors endToEnd as "must not be blank" and with status code 400


    #TODO - Accept end_to_end invalid!
  Scenario: The Application must validate if it is a not found with end_to_end invalid
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with end_to_end invalid and with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with invalid end_to_end
    And I should see the bad request as "info_not_found" with errors endToEnd as "must not be blank" and with status code 404

        #TODO - Accept conciliation_id invalid!
  Scenario: The Application must validate if it is a not found with conciliation_id invalid
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with conciliation_id invalid and with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with invalid end_to_end
    And I should see the bad request as "info_not_found" with errors endToEnd as "must not be blank" and with status code 404

 #TODO - Permitiu enviar sem o banco do SENDER
  Scenario: The Application must validate if it is a invalid payment without sender bank
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95" without bank
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with invalid end_to_end
    And I should see the bad request as "invalid_request" with errors sender bank as "must not be blank" and with status code 400

    #TODO - Erro 500
  Scenario: The Application must validate if it is a invalid payment without recipient bank
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" without recipient bank and body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with invalid end_to_end
    And I should see the bad request as "invalid_request" with errors recipient bank as "must not be blank" and with status code 400

    #TODO - Permitiu mandar valores negativos
  Scenario: The Application must validate if it is a invalid payment with negative amount
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with negative amount and body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with invalid end_to_end
    And I should see the bad request as "invalid_request" with errors recipient bank as "must not be blank" and with status code 400


        #TODO - Permitiu mandar valores negativos
  Scenario: The Application must validate if it is a invalid payment with negative amount
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with negative amount and body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with invalid end_to_end
    And I should see the bad request as "invalid_request" with errors recipient bank as "must not be blank" and with status code 400

  Scenario: The Application must validate if it is a invalid payment with document sender invalid
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95" with document invalid
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the bad request as "invalid_request" with errors document sender as "must not be invalid" and with status code 400

  Scenario: The Application must validate if it is a invalid payment when Sender and Recipient are the same
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" sender and recipient are the same with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the bad request as "invalid_request" with errors document sender as "recipient cannot be the same as the sender" and with status code 400

 #TODO - Deveria retornar 201, mas está retornando status code 200
  Scenario: The Application must validate if it is a valid payment QR CODE 02
    Given I want perform GET operation for "/accounts/62f87af90ba501353df77b95"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540520.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

  Scenario: The Application must validate if it is a invalid payment QR CODE 03 insufficient balance
    Given I want perform GET operation for "/accounts/630932fc327fb162f0d8e72c"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540550.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

     #TODO - Deveria retornar 201, mas está retornando status code 200
  Scenario: The Application must validate if it is a valid payment QR CODE 01 and ACCOUNT 02
    Given I want perform GET operation for "/accounts/630932fc327fb162f0d8e72c"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the body has authentication code and status code as 201

  Scenario: The Application must validate if it is a valid payment QR CODE 01 and ACCOUNT 03
    Given I want perform GET operation for "/accounts/630933566e9f1d5128cf4c00"
    And I perform POST operations for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I want send payment for "/pix/payments" with valid QR Code
    And I should see the bad request as "insufficient_balance" with status code 404
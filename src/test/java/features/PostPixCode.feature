

Feature: UC02 -PostPixCodes
  It must be possible to get the code data from valid QR Codes

  Scenario: UC02.01 - The Application must validate if it is a valid QR Code 01
    Given I perform POST operation for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540510.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I should see the body has key as "70326165690"
    And I also want to see the body has end_to_end as "c74205c0-5f7b-4ee2-8fab-ef646c408fff"
    And I want to see the conciliation identifier is equal to "95225008-6c6b-4310-821a-72dfe634c24e"
    And Finally I want to see the total value is equal to "10"

  Scenario: UC02.02 -The Application must validate if it is a valid QR Code 02
    Given I perform POST operation for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540520.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I should see the body has key as "70326165690"
    And I also want to see the body has end_to_end as "48f1b8b6-1459-4a4e-baa4-3276fce8349e"
    And I want to see the conciliation identifier is equal to "6e2102a1-f62c-4724-956e-e25d38e78bd5"
    And Finally I want to see the total value is equal to "20"

  Scenario: UC02.03 -The Application must validate if it is a valid QR Code 03
    Given I perform POST operation for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540550.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I should see the body has key as "70326165690"
    And I also want to see the body has end_to_end as "e3decb6a-a9af-4d9e-9652-0905002e002e"
    And I want to see the conciliation identifier is equal to "aa5f86f4-b6f6-4951-9145-3b0631cc0bd0"
    And Finally I want to see the total value is equal to "50"

  Scenario: UC02.04 -The Application must validate if it is an invalid QR Code 04
    Given I perform POST operation for "/pix/codes" with body as
      | encoded_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540550.005802BR5911Builders 016002SP62150512PAGAMENTO0163044312 |
    Then I should see the body has message error as "qrcode_not_found" with status code as 404

  Scenario: UC02.05 -The Application must validate if it is an invalid body with QR Code 03
    Given I perform POST operation for "/pix/codes", returning bad request and with body as with body as
      | encod9ed_value |
      | 00020126330014BR.GOV.BCB.PIX011170326165690520400005303986540550.005802BR5911Builders 016002SP62150511PAGAMENTO0163044312 |
    Then I should see the body has message error as "invalid_request" with status code as 400



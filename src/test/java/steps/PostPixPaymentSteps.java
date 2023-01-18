package steps;

import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import model.*;
import org.json.JSONException;
import org.json.JSONObject;
import utilities.RestAssuredExtension;

import java.util.Base64;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class PostPixPaymentSteps {

    private static ResponseOptions<Response> response;
    private static Recipient recipient = new Recipient();
    private static RecipientFake recipientFake = new RecipientFake();
    private static Bank recipientBank = new Bank();
    private static Sender sender = new Sender();
    private static Bank senderBank = new Bank();
    private static Payment payment = new Payment();



    @Given("I want perform GET operation for {string}")
    public void iWantPerformGETOperationForWithIdParameter(String url) {
        response = RestAssuredExtension.GetOps(url);
        sender.setName(response.getBody().jsonPath().getJsonObject("name").toString());
        sender.setDocument(response.getBody().jsonPath().getJsonObject("document").toString());
        senderBank.setName(response.getBody().jsonPath().getJsonObject("bank.name").toString());
        senderBank.setIspb(response.getBody().jsonPath().getJsonObject("bank.ispb").toString());
        sender.setBank(senderBank);
        payment.setSender(sender);
    }

    @Given("I want perform GET operation for {string} without bank")
    public void iWantPerformGETOperationForWithIdParameterWithoutBank(String url) {
        response = RestAssuredExtension.GetOps(url);
        sender.setName(response.getBody().jsonPath().getJsonObject("name").toString());
        sender.setDocument(response.getBody().jsonPath().getJsonObject("document").toString());
        payment.setSender(sender);
    }

    @Given("I want perform GET operation for {string} without sender")
    public void iWantPerformGETOperationForWithIdParameterWithoutSender(String url) {
        response = RestAssuredExtension.GetOps(url);
        sender.setName(response.getBody().jsonPath().getJsonObject("name").toString());
        sender.setDocument(response.getBody().jsonPath().getJsonObject("document").toString());
    }
    @And("I perform POST operations for {string} with body as")
    public void iPerformPOSTOperationsForWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipient(recipient);
    }

    @And("I perform POST operations equivalence test for {string} with body as")
    public void iPerformPOSTOperationsEquivalenceTestForWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        System.out.println(response.getBody().prettyPrint());
        System.out.println(response.statusCode());
        recipientFake = new RecipientFake();
        recipientFake.setName(9999999.00);
        recipientFake.setDocument(9999999.00);
        recipientFake.setKey(9999999.00);
        recipientFake.setKey_type(9999999.00);
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipientFake.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipientFake(recipientFake);
    }

    @And("I perform POST operations with limit test for {string} with body as")
    public void iPerformPOSTOperationsLimitTestForWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        System.out.println(response.getBody().prettyPrint());
        System.out.println(response.statusCode());

        recipient = new Recipient();
        recipient.setName("\"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...\"\n" +
                "\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
                "What is Lorem Ipsum?\n" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\n" +
                "Why do we use it?\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                "\n" +
                "Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n" +
                "\n" +
                "Where can I get some?\n" +
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                "\n" +
                "5\n" +
                "\tparagraphs\n" +
                "\twords\n" +
                "\tbytes\n" +
                "\tlists\n" +
                "\tStart with 'Lorem\n" +
                "ipsum dolor sit amet...'\n" +
                "\n" +
                "Translations: Can you help translate this site into a foreign language ? Please email us with details if you can help.\n" +
                "There is a set of mock banners available here in three colours and in a range of standard banner sizes:\n" +
                "BannersBannersBanners\n" +
                "Donate: If you use this site regularly and would like to help keep the site on the Internet, please consider donating a small sum to help pay for the hosting and bandwidth bill. There is no minimum donation, any sum is appreciated - click here to donate using PayPal. Thank you for your support.\n" +
                "Donate Bitcoin: 16UQLq1HZ3CNwhvgrarV6pMoA2CDjb4tyF\n" +
                "NodeJS Python Interface GTK Lipsum Rails .NET Groovy\n" +
                "The standard Lorem Ipsum passage, used since the 1500s\n" +
                "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"\n" +
                "\n" +
                "Section 1.10.32 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?\"\n" +
                "\n" +
                "Section 1.10.33 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"\n" +
                "\n");
        recipient.setDocument("\"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...\"\n" +
                "\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
                "What is Lorem Ipsum?\n" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\n" +
                "Why do we use it?\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                "\n" +
                "Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n" +
                "\n" +
                "Where can I get some?\n" +
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                "\n" +
                "5\n" +
                "\tparagraphs\n" +
                "\twords\n" +
                "\tbytes\n" +
                "\tlists\n" +
                "\tStart with 'Lorem\n" +
                "ipsum dolor sit amet...'\n" +
                "\n" +
                "Translations: Can you help translate this site into a foreign language ? Please email us with details if you can help.\n" +
                "There is a set of mock banners available here in three colours and in a range of standard banner sizes:\n" +
                "BannersBannersBanners\n" +
                "Donate: If you use this site regularly and would like to help keep the site on the Internet, please consider donating a small sum to help pay for the hosting and bandwidth bill. There is no minimum donation, any sum is appreciated - click here to donate using PayPal. Thank you for your support.\n" +
                "Donate Bitcoin: 16UQLq1HZ3CNwhvgrarV6pMoA2CDjb4tyF\n" +
                "NodeJS Python Interface GTK Lipsum Rails .NET Groovy\n" +
                "The standard Lorem Ipsum passage, used since the 1500s\n" +
                "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"\n" +
                "\n" +
                "Section 1.10.32 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?\"\n" +
                "\n" +
                "Section 1.10.33 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"\n" +
                "\n");
        recipient.setKey("\"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...\"\n" +
                "\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
                "What is Lorem Ipsum?\n" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\n" +
                "Why do we use it?\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                "\n" +
                "Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n" +
                "\n" +
                "Where can I get some?\n" +
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                "\n" +
                "5\n" +
                "\tparagraphs\n" +
                "\twords\n" +
                "\tbytes\n" +
                "\tlists\n" +
                "\tStart with 'Lorem\n" +
                "ipsum dolor sit amet...'\n" +
                "\n" +
                "Translations: Can you help translate this site into a foreign language ? Please email us with details if you can help.\n" +
                "There is a set of mock banners available here in three colours and in a range of standard banner sizes:\n" +
                "BannersBannersBanners\n" +
                "Donate: If you use this site regularly and would like to help keep the site on the Internet, please consider donating a small sum to help pay for the hosting and bandwidth bill. There is no minimum donation, any sum is appreciated - click here to donate using PayPal. Thank you for your support.\n" +
                "Donate Bitcoin: 16UQLq1HZ3CNwhvgrarV6pMoA2CDjb4tyF\n" +
                "NodeJS Python Interface GTK Lipsum Rails .NET Groovy\n" +
                "The standard Lorem Ipsum passage, used since the 1500s\n" +
                "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"\n" +
                "\n" +
                "Section 1.10.32 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?\"\n" +
                "\n" +
                "Section 1.10.33 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"\n" +
                "\n");
        recipient.setKey_type("\"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...\"\n" +
                "\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
                "What is Lorem Ipsum?\n" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\n" +
                "Why do we use it?\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                "\n" +
                "Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n" +
                "\n" +
                "Where can I get some?\n" +
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                "\n" +
                "5\n" +
                "\tparagraphs\n" +
                "\twords\n" +
                "\tbytes\n" +
                "\tlists\n" +
                "\tStart with 'Lorem\n" +
                "ipsum dolor sit amet...'\n" +
                "\n" +
                "Translations: Can you help translate this site into a foreign language ? Please email us with details if you can help.\n" +
                "There is a set of mock banners available here in three colours and in a range of standard banner sizes:\n" +
                "BannersBannersBanners\n" +
                "Donate: If you use this site regularly and would like to help keep the site on the Internet, please consider donating a small sum to help pay for the hosting and bandwidth bill. There is no minimum donation, any sum is appreciated - click here to donate using PayPal. Thank you for your support.\n" +
                "Donate Bitcoin: 16UQLq1HZ3CNwhvgrarV6pMoA2CDjb4tyF\n" +
                "NodeJS Python Interface GTK Lipsum Rails .NET Groovy\n" +
                "The standard Lorem Ipsum passage, used since the 1500s\n" +
                "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"\n" +
                "\n" +
                "Section 1.10.32 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?\"\n" +
                "\n" +
                "Section 1.10.33 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"\n" +
                "\n");
        recipientBank = new Bank();
        recipientBank.setName("\"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...\"\n" +
                "\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
                "What is Lorem Ipsum?\n" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\n" +
                "Why do we use it?\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                "\n" +
                "Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n" +
                "\n" +
                "Where can I get some?\n" +
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                "\n" +
                "5\n" +
                "\tparagraphs\n" +
                "\twords\n" +
                "\tbytes\n" +
                "\tlists\n" +
                "\tStart with 'Lorem\n" +
                "ipsum dolor sit amet...'\n" +
                "\n" +
                "Translations: Can you help translate this site into a foreign language ? Please email us with details if you can help.\n" +
                "There is a set of mock banners available here in three colours and in a range of standard banner sizes:\n" +
                "BannersBannersBanners\n" +
                "Donate: If you use this site regularly and would like to help keep the site on the Internet, please consider donating a small sum to help pay for the hosting and bandwidth bill. There is no minimum donation, any sum is appreciated - click here to donate using PayPal. Thank you for your support.\n" +
                "Donate Bitcoin: 16UQLq1HZ3CNwhvgrarV6pMoA2CDjb4tyF\n" +
                "NodeJS Python Interface GTK Lipsum Rails .NET Groovy\n" +
                "The standard Lorem Ipsum passage, used since the 1500s\n" +
                "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"\n" +
                "\n" +
                "Section 1.10.32 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?\"\n" +
                "\n" +
                "Section 1.10.33 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"\n" +
                "\n");
        recipientBank.setIspb("\"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...\"\n" +
                "\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
                "What is Lorem Ipsum?\n" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\n" +
                "Why do we use it?\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                "\n" +
                "Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n" +
                "\n" +
                "Where can I get some?\n" +
                "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.\n" +
                "\n" +
                "5\n" +
                "\tparagraphs\n" +
                "\twords\n" +
                "\tbytes\n" +
                "\tlists\n" +
                "\tStart with 'Lorem\n" +
                "ipsum dolor sit amet...'\n" +
                "\n" +
                "Translations: Can you help translate this site into a foreign language ? Please email us with details if you can help.\n" +
                "There is a set of mock banners available here in three colours and in a range of standard banner sizes:\n" +
                "BannersBannersBanners\n" +
                "Donate: If you use this site regularly and would like to help keep the site on the Internet, please consider donating a small sum to help pay for the hosting and bandwidth bill. There is no minimum donation, any sum is appreciated - click here to donate using PayPal. Thank you for your support.\n" +
                "Donate Bitcoin: 16UQLq1HZ3CNwhvgrarV6pMoA2CDjb4tyF\n" +
                "NodeJS Python Interface GTK Lipsum Rails .NET Groovy\n" +
                "The standard Lorem Ipsum passage, used since the 1500s\n" +
                "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"\n" +
                "\n" +
                "Section 1.10.32 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?\"\n" +
                "\n" +
                "Section 1.10.33 of \"de Finibus Bonorum et Malorum\", written by Cicero in 45 BC\n" +
                "\"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.\"\n" +
                "\n" +
                "1914 translation by H. Rackham\n" +
                "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"\n" +
                "\n");
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999900000000009999999.00);
        payment.setRecipient(recipient);
    }

    @And("I perform POST operations for {string} with negative amount and body as")
    public void iPerformPOSTOperationsForWithNegativeAmountAndBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(-3000.00);
        payment.setRecipient(recipient);
    }


    @And("I perform POST operations for {string} without amount and body as")
    public void iPerformPOSTOperationsForWithoutAmountAndBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setRecipient(recipient);
    }

    //TODO - parâmetro errado na api, endToEnd
    @Then("I want send payment for {string} with valid QR Code")
    public void iWantSendPaymentQRCodeToQRCode(String url) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(payment);
        jsonString = jsonString.replace("\"end_to_end\"", "\"endToEnd\"");
        response = RestAssuredExtension.PostOpsWithBody(url, jsonString);
        assertThat(Long.parseLong("3000"), greaterThanOrEqualTo(response.getTime()));

    }

    //TODO - Não está retornando status code 201 conforme documentação
    @And("I should see the body has authentication code and status code as {int}")
    public void iShouldSeeTheBodyHasAuthenticationCodeAsWithStatusCode(Integer status_code) {
        String authenticationCodeResponse = response.getBody().jsonPath().getJsonObject("authentication_code").toString();
        assertThat(authenticationCodeResponse,hasLength(authenticationCodeResponse.length()));
        assertThat(response.statusCode(), equalTo(status_code));
    }

    @Then("I want send invalid payment for {string} with valid QR Code")
    public void iWantSendInvalidPaymentForWithValidQRCode(String url) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(payment);
        response = RestAssuredExtension.PostOpsWithBody(url, jsonString);
    }



    @And("I should see the bad request as {string} with errors endToEnd as {string} and with status code {int}")
    public void iShouldSeeTheBadRequestAsWithErrorsEndToEndAsAndWithStatusCode(String error, String errors, int status_code) {
        String errorResponse = response.getBody().jsonPath().getJsonObject("error").toString();
        String errorsResponse =  response.getBody().jsonPath().getJsonObject("errors.endToEnd").toString();
        assertThat(errorResponse, equalTo(error));
        assertThat(errorsResponse, equalTo(errors));
        assertThat(response.statusCode(), equalTo(status_code));
    }

    @And("I should see the bad request as {string} with errors sender bank as {string} and with status code {int}")
    public void iShouldSeeTheBadRequestAsWithErrorsSenderBankAsAndWithStatusCode(String error, String errors, int status_code) {
        String errorResponse = response.getBody().jsonPath().getJsonObject("error").toString();
        String errorsResponse =  response.getBody().jsonPath().getJsonObject("errors.sender.bank").toString();
        assertThat(errorResponse, equalTo(error));
        assertThat(errorsResponse, equalTo(errors));
        assertThat(response.statusCode(), equalTo(status_code));
    }

    @And("I should see the bad request as {string} with errors recipient bank as {string} and with status code {int}")
    public void iShouldSeeTheBadRequestAsWithErrorsRecipientBankAsAndWithStatusCode(String error, String errors, int status_code) {
        String errorResponse = response.getBody().jsonPath().getJsonObject("error").toString();
        String errorsResponse =  response.getBody().jsonPath().getJsonObject("errors.recipient.bank").toString();
        assertThat(errorResponse, equalTo(error));
        assertThat(errorsResponse, equalTo(errors));
        assertThat(response.statusCode(), equalTo(status_code));
    }

    @Then("I want send payment for {string} with invalid end_to_end")
    public void iWantSendPaymentForWithInvalidEnd_to_end(String url) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(payment);
        jsonString = jsonString.replace("\"end_to_end\"", "\"endToEnd\"");
        response = RestAssuredExtension.PostOpsWithBody(url, jsonString);
    }

    @And("I perform POST operations for {string} with conciliation_id invalid and with body as")
    public void iPerformPOSTOperationsForWithConciliation_idInvalidAndWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id("conciliationIdInvalid");
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipient(recipient);
    }

    @And("I perform POST operations for {string} with end_to_end invalid and with body as")
    public void iPerformPOSTOperationsForWithEnd_to_endInvalidAndWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end("e2eInvalid");
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipient(recipient);
    }

    @And("I perform POST operations for {string} without recipient bank and body as")
    public void iPerformPOSTOperationsForWithoutRecipientBankAndBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipient(recipient);
    }

    @And("I perform POST operations for {string} without recipient and body as")
    public void iPerformPOSTOperationsForWithoutRecipientAndBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        recipient.setName(response.getBody().jsonPath().getJsonObject("holder.name").toString());
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
    }

    @Given("I want perform GET operation for {string} with document invalid")
    public void iWantPerformGETOperationForWithDocumentInvalid(String url) {
        response = RestAssuredExtension.GetOps(url);
        sender.setName(response.getBody().jsonPath().getJsonObject("name").toString());
        sender.setDocument("9999");
        senderBank.setName(response.getBody().jsonPath().getJsonObject("bank.name").toString());
        senderBank.setIspb(response.getBody().jsonPath().getJsonObject("bank.ispb").toString());
        sender.setBank(senderBank);
        payment.setSender(sender);
    }

    @And("I should see the bad request as {string} with errors document sender as {string} and with status code {int}")
    public void iShouldSeeTheBadRequestAsWithErrorsDocumentSenderAsAndWithStatusCode(String error, String errors, int status_code) {
        String errorResponse = response.getBody().jsonPath().getJsonObject("error").toString();
        String errorsResponse =  response.getBody().jsonPath().getJsonObject("errors.sender.document").toString();
        assertThat(errorResponse, equalTo(error));
        assertThat(errorsResponse, equalTo(errors));
        assertThat(response.statusCode(), equalTo(status_code));
    }

    @And("I perform POST operations for {string} sender and recipient are the same with body as")
    public void iPerformPOSTOperationsForSenderAndRecipientAreTheSameWithBodyAs(String url, DataTable table) {
        String base64Encode = Base64.getEncoder().encodeToString(table.cell(1, 0).getBytes());
        JSONObject body = new JSONObject();
        try {
            body.put("encoded_value", base64Encode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        response = RestAssuredExtension.PostOpsWithBody(url, body.toString());
        recipient = new Recipient();
        String name = response.getBody().jsonPath().getJsonObject("holder.name").toString();
        String document = response.getBody().jsonPath().getJsonObject("holder.document").toString();
        recipient.setName(name);
        recipient.setDocument(response.getBody().jsonPath().getJsonObject("holder.document").toString());
        recipient.setKey(response.getBody().jsonPath().getJsonObject("holder.key").toString());
        recipient.setKey_type(response.getBody().jsonPath().getJsonObject("holder.key_type").toString());
        recipientBank = new Bank();
        recipientBank.setName(response.getBody().jsonPath().getJsonObject("holder.bank.name").toString());
        recipientBank.setIspb(response.getBody().jsonPath().getJsonObject("holder.bank.ispb").toString());
        recipient.setBank(recipientBank);
        payment.setEnd_to_end(response.getBody().jsonPath().getJsonObject("end_to_end").toString());
        payment.setConciliation_id(response.getBody().jsonPath().getJsonObject("conciliation_id").toString());
        payment.setAmount(Double.parseDouble(response.getBody().jsonPath().getJsonObject("total_value").toString()));
        payment.setRecipient(recipient);
        Sender sender = new Sender();
        sender.setName(name);
        sender.setDocument(document);
        sender.setBank(recipientBank);
        payment.setSender(sender);

    }

    @And("I should see the bad request as {string} with status code {int}")
    public void iShouldSeeTheBadRequestAsWithStatusCode(String error, int status_code) {
        String errorResponse = response.getBody().jsonPath().getJsonObject("error").toString();
        assertThat(errorResponse, equalTo(error));
        assertThat(response.statusCode(), equalTo(status_code));
    }
}

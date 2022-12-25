package lambda.statusProcessor;

import DAO.IDAO.bean.FeedBean;
import DAO.IDAO.bean.FollowBean;
import DAOFactory.AbstractDAOFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageBatchResult;
import com.google.gson.Gson;
import request.authenticated_request.PostStatusRequest;

import java.util.*;

public class PostRequestQueueProcessor implements RequestHandler<SQSEvent, Void> {
    final String feedBatchInsertionQueueURL = "https://sqs.us-east-1.amazonaws.com/652355629952/FeedBatchInsertions";
    final AmazonSQS client = AmazonSQSClientBuilder.defaultClient();
    private int uploads = 0;

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        List<String> messages = new ArrayList<>();

        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            Gson gson = new Gson();
            PostStatusRequest postStatusRequest = gson.fromJson(msg.getBody(), PostStatusRequest.class);
            long timestamp = new Date(postStatusRequest.getStatus().datetime).getTime();

            List<FeedBean> feedBeans = new ArrayList<>();

            List<FollowBean> followBeans = AbstractDAOFactory.factory()
                    .followDAO()
                    .findFollowersPagedList(postStatusRequest.getStatus().user.getAlias(), -1, null)
                    .getFirst();



            for(FollowBean followBean: followBeans){
                FeedBean feedBean = new FeedBean(
                        followBean.getFollower_handle(),
                        postStatusRequest.getStatus().user.getAlias(),
                        postStatusRequest.getStatus().user.getFirstName(),
                        postStatusRequest.getStatus().user.getLastName(),
                        postStatusRequest.getStatus().user.getImageUrl(),
                        postStatusRequest.getStatus().post,
                        timestamp
                );

                feedBeans.add(feedBean);

                if(feedBeans.size() == 25){
                    String requestJson = gson.toJson(feedBeans);
                    messages.add(requestJson);
                    feedBeans.clear();
                }
                if(messages.size() == 10){
                    sendMessageBatch(messages);
                    ++uploads;
                }

            }

            if(feedBeans.size() != 0) {
                String requestJson = gson.toJson(feedBeans);
                messages.add(requestJson);
                feedBeans.clear();
            }

            if (messages.size() != 0) {
                sendMessageBatch(messages);
                ++uploads;
            }
        }

        System.out.println("num uploads: " + uploads);

        return null;
    }

    private void sendMessageBatch(List<String> messages){
        SendMessageBatchRequest sendMessageBatchRequest = new SendMessageBatchRequest().withQueueUrl(feedBatchInsertionQueueURL);
        Collection<SendMessageBatchRequestEntry> entries = new ArrayList<>();
        for(String message: messages){
            SendMessageBatchRequestEntry entry = new SendMessageBatchRequestEntry()
                    .withMessageBody(message)
                    .withId(UUID.randomUUID().toString());
            entries.add(entry);
        }

        messages.clear();

        sendMessageBatchRequest.withEntries(entries);
        SendMessageBatchResult result = client.sendMessageBatch(sendMessageBatchRequest);


    }
}

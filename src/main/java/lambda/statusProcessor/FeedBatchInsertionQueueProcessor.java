package lambda.statusProcessor;

import DAO.IDAO.bean.FeedBean;
import DAO.IDAO.bean.FollowBean;
import DAOFactory.AbstractDAOFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FeedBatchInsertionQueueProcessor implements RequestHandler<SQSEvent, Void> {
    int numItems = 0;
    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {

            Type listType = new TypeToken<List<FeedBean>>(){}.getType();
            List<FeedBean> feedBeans = new Gson().fromJson(msg.getBody(), listType);
            AbstractDAOFactory.factory().feedDAO().insert(feedBeans);
            numItems += feedBeans.size();

            feedBeans.forEach(bean -> System.out.println(bean.getFollower_handle())); //todo: debug. delete later

        }
        System.out.println("TOTAL INSERTED: " + numItems);
        return null;
    }
}
